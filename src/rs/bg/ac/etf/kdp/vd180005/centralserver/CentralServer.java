package rs.bg.ac.etf.kdp.vd180005.centralserver;

import java.net.Socket;
import rs.bg.ac.etf.kdp.vd180005.common.AbstractServer;
import rs.bg.ac.etf.kdp.vd180005.purgers.Purger;

/**
 *
 * @author Dobrosav Vlaskovic
 */
public class CentralServer extends AbstractServer {
	public static final int PORT = 8080;
	
	private static Purger[] purgerPool;
	private static int numOfPurgers; // Number of purge threads
	
	public CentralServer() {
		// Default values
		numOfPurgers = 5;
		port = PORT;
	}

	/**
	 *
	 */
	@Override
	public void startServer() {
		// Create new purgers
		purgerPool = new Purger[numOfPurgers];
		for (int i = 0; i < numOfPurgers; i++) {
			purgerPool[i] = new Purger();
			purgerPool[i].start();
		}

		// Call super
		super.startServer();
	}

	/**
	 *
	 */
	@Override
	public void stopServer() {
		// Stop purgers
		for (int i = 0; i < numOfPurgers; i++) {
			purgerPool[i].interrupt();
			purgerPool[i] = null;
		}

		// Call super
		super.stopServer();
	}

	@Override
	public void startNewThread(Socket socket, int port) {
		CentralServerThread serverThread;
		serverThread = new CentralServerThread(socket, port);
		serverThread.start();
	}

	/**
	 * @param numOfPurgers the numOfPurgers to set
	 */
	public void setNumOfPurgers(int numOfPurgers) {
		this.numOfPurgers = numOfPurgers;
	}

	@Override
	protected void initializeServer() {
		// Nothing to see here, move along
	}
}
