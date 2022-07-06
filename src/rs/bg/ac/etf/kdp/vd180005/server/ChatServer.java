package rs.bg.ac.etf.kdp.vd180005.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	private ServerSocket serverSocket;

	public ChatServer(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		System.out.println("SERVER STARTED");
	}

	public void startServer() {
		try {
			while (!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				System.out.println("New client connected");
				ClientHandler clientHandler = new ClientHandler(socket);
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void closeServer() {
		try {
			if(serverSocket!=null)
				serverSocket.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket=new ServerSocket(1234);
		ChatServer chatServer=new ChatServer(serverSocket);
		chatServer.startServer();
	}
}
