package rs.bg.ac.etf.kdp.vd180005.centralserver;

import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import rs.bg.ac.etf.kdp.vd180005.common.AbstractServerThread;
import rs.bg.ac.etf.kdp.vd180005.common.ServerInfo;
import rs.bg.ac.etf.kdp.vd180005.common.StreamOperations;
import rs.bg.ac.etf.kdp.vd180005.purgers.PurgeRequestHandler;

/**
 *
 * @author Dobrosav Vlaskovic
 */
public class CentralServerThread extends AbstractServerThread {

	// TODO static mutex for filename lock
	
	private static LinkedList<ServerInfo> serverList = new LinkedList<>();

	/**
	 *
	 * @param socket
	 * @param myPort
	 * @throws Exception
	 */
	public CentralServerThread(Socket socket, int myPort) {
		super(socket, myPort);
	}

	/**
	 * Adds filename to serverInfo in serverList. Creates new serverInfo if
	 * needed.
	 *
	 * @param filename
	 */
	protected synchronized void addFileToServerInfo(String filename) {
		// Get server information (IP and port)
		String ip = socket.getInetAddress().toString().substring(1); // substring(1) for removing the slash
		int port = clientPort;
		ServerInfo newServerInfo = null;

		// Check if serverInfo exists for the server
		if (serverList != null) {
			for (ServerInfo serverInfo : serverList) {
				if (serverInfo.getIp().equals(ip) && serverInfo.getPort() == port) {
					newServerInfo = serverInfo;
					break;
				}
			}
		}

		// Add file record to serverInfo
		if (newServerInfo != null) {
			newServerInfo.addFile(filename);
		}
		else {
			// Create new serverInfo if needed
			newServerInfo = new ServerInfo(ip, port);
			newServerInfo.addFile(filename);
			serverList.add(newServerInfo);
		}

	}
	
	private synchronized static int getServerIndexInList(ServerInfo serverInfoToFind) {
		int index = -1;
		
		String ip = serverInfoToFind.getIp();
		int port = serverInfoToFind.getPort();
		
		if (serverList != null) {
			index++;
			for (ServerInfo serverInfo : serverList) {
				if (serverInfo.getIp().equals(ip) && serverInfo.getPort() == port) {
					break;
				}
			}
		}		
		
		return index;
	}

	public synchronized static void deleteServerInfo(ServerInfo serverInfo) {
		int index = getServerIndexInList(serverInfo);
		serverList.remove(index);
	}
	
	public synchronized static void unsuccessfulDeleteRequest(ServerInfo serverInfo) {
		int index = getServerIndexInList(serverInfo);
		serverList.get(index).incNumOfFailedRequests();
	}
	
	
	/**
	 *
	 * @param filename
	 */
	@Override
	protected void fileDoesNotExist(String filename) {
		StreamOperations.send404(filename, output);
	}


	/**
	 *
	 * @param filename
	 * @return
	 */
	@Override
	protected boolean httpGet(String filename) {
		boolean result;

		result = super.httpGet(filename); // call super method
		if (result) {
			addFileToServerInfo(filename);
			return true;
		}

		return false;
	}

	/**
	 *
	 * @param filename
	 */
	@Override
	protected void httpPut(String filename) {
		super.httpPut(filename);
		deleteFileOnServers(filename);
	}
	
	
	@Override
	protected void httpDelete(String filename) {
		super.httpDelete(filename);
	}
	
	/**
	 *
	 * @param filename
	 */
	protected synchronized void deleteFileOnServers(String filename) {
		if (serverList != null) {
			ConcurrentLinkedQueue<ServerInfo> serverInfoQueue = new ConcurrentLinkedQueue<>();

			// For all servers in record
			for (ServerInfo serverInfo : serverList) {
				// If file exists on server
				if (serverInfo.fileExists(filename)) {
					// Add to serverInfoQueue
					serverInfoQueue.add(serverInfo);
				}
			}

			// put serverInfoQueue into purgeQueue
			if (!serverInfoQueue.isEmpty()) {
				PurgeRequestHandler purgeRequestHandler = new PurgeRequestHandler(filename, serverInfoQueue);
				PurgeRequestHandler.purgeQueue.add(purgeRequestHandler);
			}
		}
	}
}
