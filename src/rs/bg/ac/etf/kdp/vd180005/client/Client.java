package rs.bg.ac.etf.kdp.vd180005.client;

import rs.bg.ac.etf.kdp.vd180005.common.StreamOperations;

/**
 *
 * @author Dobrosav Vlaskovic
 */
public class Client {
	
	public Client() {
	}
	
	public void sendGetRequest(String host, int port, String filename) {		
		StreamOperations.downloadFile(host, port, 0, "/" + filename);
	}
	
	public void sendPutRequest(String host, int port, String filename) {
		StreamOperations.sendFile("localhost", 8080, "/" + filename);
	}
	
	public void sendDeleteRequest(String host, int port, String filename) {
		StreamOperations.deleteFile(host, port, "/" + filename);
	}
}
