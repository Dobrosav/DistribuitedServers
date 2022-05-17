package rs.bg.ac.etf.kdp.vd180005.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.bg.ac.etf.kdp.vd180005.server.ServerThread;

/**
 *
 * @author Dobrosav Vlaskovic
 */
public class StreamOperations {

	/**
	 *
	 * @param in
	 * @param out
	 */
	public static void copy(InputStream in, OutputStream out) {
		try {
			byte[] b = new byte[1024];

			int len;
			while ((len = in.read(b)) >= 0) {
				out.write(b, 0, len);
			}
		} catch (IOException ex) {
			MyLogger.logger.log(Level.SEVERE, null, ex);
		} finally {
			try {
				in.close();
				out.close(); // Interrupts client browsers?
			} catch (IOException ex) {
				Logger.getLogger(StreamOperations.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 *
	 * @param filename
	 * @return
	 */
	public static String getFilePath(String filename) {
		while (filename.startsWith(".") || filename.startsWith("/")) { // Prevents "../" security hole
			filename = filename.substring(1);
		}
		filename = "./" + filename; // Adds leading dot for file search in local directory

		return filename;
	}

	/**
	 *
	 * @param filename
	 * @return
	 */
	public static FileInputStream getFileInputStream(String filename) {
		FileInputStream file = null;

		filename = getFilePath(filename); // TODEL

		// check if file exists
		try {
			file = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return file;
	}

	/**
	 *
	 * @param host
	 * @param port
	 * @return
	 */
	public static Socket getSocket(String host, int port) {
		Socket socket = null;

		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException ex) {
			Logger.getLogger(StreamOperations.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(StreamOperations.class.getName()).log(Level.SEVERE, null, ex);
		}

		return socket;
	}

	/**
	 *
	 * @param socket
	 * @param request
	 */
	public static boolean sendHttpRequest(Socket socket, String request) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(request.getBytes());
			os.flush();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(StreamOperations.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
	
	
	public static String getLine(InputStream input) {
		String line = "";

		try {
			char c;
			int data = input.read();

			while (data != -1) {
				c = (char) data;
				if (c == '\n') {
					break;
				}
				if (c != '\r') {
					line += c;
				}
				data = input.read();
			}
		} catch (IOException ex) {
			Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
		}

		return line;
	}

	/**
	 *
	 * @param host
	 * @param port
	 * @param filename
	 */
	public static boolean downloadFile(String host, int port, int clientPort, String filename) {		
		try {
			Socket socket = getSocket(host, port);

			createSubdirectories(filename);
			
			// request = client info + request string
			String request =
					HTTP.getHeaderString("Client-Port:", Integer.toString(clientPort))
					+ HTTP.getRequestString("GET", filename);

			sendHttpRequest(socket, request);
			
			InputStream input = socket.getInputStream();
			
			String responseHeader = getLine(input);
			getLine(input);
			
			if (responseHeader.contains("200")) {
				filename = getFilePath(filename);
				File newCopy = new File(filename);
				StreamOperations.copy(input, new FileOutputStream(newCopy));
				return true;
			}
		} catch (IOException ex) {
			Logger.getLogger(StreamOperations.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return false;
	}
	
	public static boolean uploadFile(String filename, OutputStream output) {
		try {
			// Send HTTP headers
			String header = HTTP.STATUS.OK;

			output.write(header.getBytes());
			output.write(HTTP.CRLF.getBytes());
			
			FileInputStream file = new FileInputStream(filename);
			copy(file, output);
			return true;
		} catch (IOException ex) {
			Logger.getLogger(StreamOperations.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return false;
	}
	
	public static void send404(String filename, OutputStream output) {
		try {
			String header = HTTP.STATUS.NOT_FOUND;

			output.write(header.getBytes());
			output.write(HTTP.CRLF.getBytes());

			output.write(("404" + HTTP.CRLF).getBytes());
			output.write(("file " + filename + " not found!").getBytes());
		} catch (IOException ex) {
			Logger.getLogger(StreamOperations.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void sendFile(String host, int port, String filename) {
		try {
			// Get socket
			Socket socket = getSocket(host, port);

			// Send PUT request
			String request = HTTP.getRequestString("PUT", filename);
			sendHttpRequest(socket, request);

			// Get file input stream
			FileInputStream fileInputStream = getFileInputStream(filename);

			// Start copying 
			StreamOperations.copy(fileInputStream, socket.getOutputStream());
		} catch (IOException ex) {
			Logger.getLogger(StreamOperations.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void createSubdirectories(String filename) {
		while (filename.startsWith(".") || filename.startsWith("/")) { // prevent "../" security hole
			filename = filename.substring(1);
		}
		
		if (filename.lastIndexOf("/") >= 0) {
			String dirs = filename.substring(0, filename.lastIndexOf("/"));
			new File(dirs).mkdirs();
		}
	}

	/**
	 *
	 * @param host
	 * @param port
	 * @param filename
	 */
	public static boolean deleteFile(String host, int port, String filename) {
		Socket socket = getSocket(host, port);
		if (null == socket) {
			return false;
		}
		
		String request = HTTP.getRequestString("DELETE", filename);

		return sendHttpRequest(socket, request);
	}
}
