package rs.bg.ac.etf.kdp.vd180005.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.bg.ac.etf.kdp.vd180005.server.ServerThread;

public abstract class AbstractServerThread extends Thread {

	protected Socket socket;
	protected InputStream input;
	protected OutputStream output;
	protected int myPort;
	protected int clientPort;

	public AbstractServerThread(Socket socket, int myPort) {
		try {
			this.socket = socket;
			this.myPort = myPort;
			this.input = socket.getInputStream();
			this.output = socket.getOutputStream();
		} catch (IOException ex) {
			Logger.getLogger(AbstractServerThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	@Override
	public void run() {
		while (true) {
			try {
				String line = StreamOperations.getLine(input);
				System.out.println("==>" + line);

				// Break if empty line
				if (line.equals(HTTP.CRLF) || line.equals("")) {
					break;
				}

				// Get HTTP header and its value
				StringTokenizer st = new StringTokenizer(line);
				String method = st.nextToken();
				String requestUri = st.nextToken();

				switch (method) {
					case "GET":
						requestUri = StreamOperations.getFilePath(requestUri);
						httpGet(requestUri);
						break;
					case "PUT":
						requestUri = StreamOperations.getFilePath(requestUri);
						httpPut(requestUri);
						break;
					case "DELETE":
						requestUri = StreamOperations.getFilePath(requestUri);
						httpDelete(requestUri);
						break;
					case "Client-Port:":
						clientPort = Integer.parseInt(requestUri);
						break;
					default:
						break;
				}

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		} // end while

		// close all
		try {
			output.close();
			input.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean httpGet(String filename) {
		File f = new File(filename);
		boolean fileExists = f.exists() && f.isFile();
		
		// display file
		if (fileExists) {
			StreamOperations.uploadFile(filename, output);
			return true;
		} else {
			fileDoesNotExist(filename);
			return false;
		}
	}

	protected synchronized void httpPut(String filename) {
		try {
			StreamOperations.createSubdirectories(filename);
			File newFile = new File(filename);
			StreamOperations.copy(input, new FileOutputStream(newFile));
		} catch (IOException ex) {
			Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	protected void httpDelete(String filename) {
		try {
			File file = new File(filename);

			if (file.delete()) {
				String msg = "File " 
						+ filename 
						+ " deleted.";
				MyLogger.logger.log(Level.INFO, msg);
			} else {
				String msg = "Delete operation failed " 
						+ "(" + filename + ")";
				MyLogger.logger.log(Level.INFO, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void fileDoesNotExist(String filename);
}
