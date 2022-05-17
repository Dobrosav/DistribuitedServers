package rs.bg.ac.etf.kdp.vd180005.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract server class. Initiates the server socket and delivers new tasks to
 * threads.
 
 * @author Dobrosav Vlaskovic
 */
public abstract class AbstractServer extends Thread {	
	protected int port;
	
	public ServerSocket serverSocket;
	private Semaphore allowedToRun;
	
	public AbstractServer() {
		serverSocket = null;
		allowedToRun = new Semaphore(0);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				allowedToRun.acquire();
				startServer();
			} catch (InterruptedException ex) {
				Logger.getLogger(AbstractServer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public void signalStart() {
		allowedToRun.release();
	}
	
	protected void startServer() {		
		try {
			initializeServer();
			serverSocket = new ServerSocket(port);
			MyLogger.logger.log(Level.INFO, "WebServer started");

			while (true) {
				Socket socket = serverSocket.accept();
				MyLogger.logger.log(Level.INFO, "New connection accepted {0}:{1}", new Object[]{socket.getInetAddress(), socket.getPort()});

				startNewThread(socket, port);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected abstract void startNewThread(Socket socket, int port);
	
	protected abstract void initializeServer();
	
	public void stopServer() {
		// TODO good enough?
		try {
			serverSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(AbstractServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
		
	public String getAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
		
		return "unknown";
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
