package rs.bg.ac.etf.kdp.vd180005.server;

import java.io.File;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import rs.bg.ac.etf.kdp.vd180005.common.AbstractServer;
import rs.bg.ac.etf.kdp.vd180005.common.MyLogger;
import rs.bg.ac.etf.kdp.vd180005.common.ServerInfo;

/**
 * @author Dobrosav Vlaskovic
 */
public class Server extends AbstractServer {
	private static int defaultPort = 9000;
	private static String centralServerAddress = "localhost";
	public static ServerInfo myServerInfo = null;

	/**
	 * @param aCentralServerAddress the centralServerAddress to set
	 */
	public static void setCentralServerAddress(String aCentralServerAddress) {
		centralServerAddress = aCentralServerAddress;
	}

	/**
	 * @return the centralServerAddress
	 */
	public static String getCentralServerAddress() {
		return centralServerAddress;
	}
	
	
	public Server() {
		synchronized(this) {
			port = defaultPort;
			defaultPort++;
		}
	}

	@Override
	public void startNewThread(Socket socket, int port) {
		ServerThread serverThread;
		serverThread = new ServerThread(socket, port);
		serverThread.start();
	}

	@Override
	protected void initializeServer() {
		// Delete files on initialization
		if (myServerInfo != null) {
			LinkedList<String> files = myServerInfo.getFiles();


			for (String filename : files) {
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
		}

		myServerInfo = new ServerInfo("", port);
	}

}
