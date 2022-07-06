package rs.bg.ac.etf.kdp.vd180005.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class ClientChat {
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String username;
	
	public ClientChat(Socket socket, String username) {
		try {
			this.socket=socket;
			this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.username=username;
		}catch (IOException e) {
			// TODO: handle exception
			close();
		}
	}
	private void close() {
		// TODO Auto-generated method stub
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
	public void sendMessage() {
		try {
			bufferedWriter.write(username);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			Scanner s=new Scanner(System.in);
			while(socket.isConnected()) {
				String messsageString=s.nextLine();
				bufferedWriter.write(username+":"+messsageString);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
		} catch (IOException e) {
			// TODO: handle exception
			close();
		}
	}
	public void listenMessage() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String messageFromChat;
				while(socket.isConnected()) {
					try {
						messageFromChat=bufferedReader.readLine();
						System.out.println(messageFromChat);
					}catch (IOException e) {
						// TODO: handle exception
						close();
					}
				}
			}
		}).start();
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket=new Socket("localhost",1234);
		Random rnd=new Random();
		ClientChat chat= new ClientChat(socket, "m2.mp4"+rnd.nextInt());
		chat.listenMessage();
		chat.sendMessage();
	}
}