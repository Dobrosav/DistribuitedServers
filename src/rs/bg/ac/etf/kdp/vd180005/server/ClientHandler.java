package rs.bg.ac.etf.kdp.vd180005.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	public static ArrayList<ClientHandler> clientHandlers= new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String clientUsername;
	
	public ClientHandler(Socket socket) {
		// TODO Auto-generated constructor stub
		try {
			this.socket=socket;
			this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.clientUsername=bufferedReader.readLine();
			clientHandlers.add(this);
			broadCastMessage("SERVER: "+clientUsername+" joined the room");
		}
		catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void broadCastMessage(String message) {
		// TODO Auto-generated method stub
		for(ClientHandler c:clientHandlers) {
			try {
				if(!c.clientUsername.equals(clientUsername)) {
					c.bufferedWriter.write(message);
					c.bufferedWriter.newLine();
					c.bufferedWriter.flush();
				}
					
			}
			catch(IOException e) {
				close();
			}
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String message;
		while(socket.isConnected()) {
			try {
				message=bufferedReader.readLine();
				broadCastMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				close();
				break;
			}
		}
	}
	private void close() {
		// TODO Auto-generated method stub
		removeClientHandler();
		try {
			if(socket!=null)
				socket.close();
			if(bufferedReader!=null)
				bufferedReader.close();
			if(bufferedWriter!=null)
				bufferedWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void removeClientHandler() {
		clientHandlers.remove(this);
		broadCastMessage("SERVER: "+clientUsername+" left the room");
	}
}
