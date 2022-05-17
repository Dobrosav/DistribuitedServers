package rs.bg.ac.etf.kdp.vd180005.common;

import java.util.LinkedList;
import rs.bg.ac.etf.kdp.vd180005.centralserver.CentralServerThread;
import rs.bg.ac.etf.kdp.vd180005.purgers.PurgeRequestHandler;

/**
 *
 * @author Dobrosav Vlaskovic
 */
public class ServerInfo {

	private String ip;
	private int port;
	private int numOfFailedRequests;
	private boolean active; // if server is online
	private LinkedList<String> files; // files cached on server

	public ServerInfo(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.active = true;
		this.numOfFailedRequests = 0;

		files = new LinkedList<>();
	}

	public synchronized void addFile(String filename) {
		if (!files.contains(filename)) {
			getFiles().add(filename);
		}
	}

	public synchronized void removeFile(String filename) {
		getFiles().remove(filename);
	}
	
	public synchronized boolean fileExists(String filename) {
		return getFiles().contains(filename);
	}
	
	
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * 
	 */
	public void incNumOfFailedRequests() {
		numOfFailedRequests++;
		if (numOfFailedRequests >= PurgeRequestHandler.K) {
			active = false;
			CentralServerThread.deleteServerInfo(this);
		}
	}
	
	public String toString() {
		return ip + ":" + port;
	}

	/**
	 * @return the files
	 */
	public LinkedList<String> getFiles() {
		return files;
	}
}
