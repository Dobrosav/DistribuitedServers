package rs.bg.ac.etf.kdp.vd180005.purgers;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import rs.bg.ac.etf.kdp.vd180005.centralserver.CentralServerThread;
import rs.bg.ac.etf.kdp.vd180005.common.MyLogger;
import rs.bg.ac.etf.kdp.vd180005.common.ServerInfo;
import rs.bg.ac.etf.kdp.vd180005.common.StreamOperations;

/**
 *
 * @author Dobrosav Vlaskovic
 */
public class PurgeRequestHandler {
	public static final int M = 9; // Num of seconds for server to respond
	public static final int K = 10; // Max num of retries to claim server failure
	public static final int NUM_OF_RETRIES = 3; // Num of times purge request can fail
	
	public static LinkedBlockingQueue<PurgeRequestHandler> purgeQueue = new LinkedBlockingQueue<>(); // thread safe
	
	private String filename;
	
	private ConcurrentLinkedQueue<ServerInfo> serverInfoQueue;
	private ConcurrentLinkedQueue<ServerInfo> failedServersQueue;

	private int numOfRequests;
	private boolean finished;

	public PurgeRequestHandler(String filename, ConcurrentLinkedQueue<ServerInfo> serverInfoQueue) {
		this.filename = filename;
		
		this.serverInfoQueue = serverInfoQueue;
		this.failedServersQueue = new ConcurrentLinkedQueue<>();
		this.numOfRequests = serverInfoQueue.size();
		this.finished = false;
	}
	
	public void addToQueue(ServerInfo serverInfo) {
		serverInfoQueue.add(serverInfo);
		incNumOfRequests();
	}
	
	
	/**
	 *
	 * @return	True if tried
	 *			False if no requests left
	 */
	public boolean sendNextDeleteRequest() throws InterruptedException {		
		ServerInfo serverInfo = serverInfoQueue.poll();
		if (null == serverInfo) {
			return false;
		}
		String ip = serverInfo.getIp();
		int port = serverInfo.getPort();
		
		// Send request NUM_OF_RETRIES times over M seconds
		for (int i = 0; i <= NUM_OF_RETRIES; i++) {
			if (StreamOperations.deleteFile(ip, port, filename)) { // Success
				break;
			} else { // Server not responding
				CentralServerThread.unsuccessfulDeleteRequest(serverInfo);
				
				if (i < NUM_OF_RETRIES) {
					// Sleep and try again
					Thread.sleep(M * 1000 / NUM_OF_RETRIES);
				}
				else { // Last request failed
					failedServersQueue.add(serverInfo);
				}
			}
		}
		
		lastRequestWritesToLog();
		
		return true;
	}
	
	private synchronized void lastRequestWritesToLog() {
		if (decNumOfRequests() == 0) {
			if (failedServersQueue.isEmpty()) {
				// Write success
				String msg = "File "
						+ filename 
						+ " successfuly deleted on all servers.";
				MyLogger.logger.log(Level.INFO, msg);
			} else {
				// Write failed
				String msg = "File "
						+ filename 
						+ " failed to be deleted on the following servers:";
				
				while (!failedServersQueue.isEmpty()) {					
					ServerInfo failedServer = failedServersQueue.poll();
					msg += "\n" + failedServer.toString();
				}
				MyLogger.logger.log(Level.WARNING, msg);
			}
			finished = true;
		}
	}

	/**
	 * @return the numOfRequests
	 */
	public synchronized int incNumOfRequests() {
		return ++numOfRequests;
	}

	/**
	 * @return the numOfRequests
	 */
	public synchronized int decNumOfRequests() {
		return --numOfRequests;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}
}
