package rs.bg.ac.etf.kdp.vd180005.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class App extends JFrame {
	private static final String TITLE = "My First Media Player";
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private JButton playButton, pauseButton;
	private String path;
	private boolean admin;
	public static String operationPlay=":PLAY";
	public static String operationPause=":PAUSE";
	class ClientChat {
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
		public void sendMessage(String messsageString) {
			try {
				bufferedWriter.write(username);
				bufferedWriter.newLine();
				bufferedWriter.flush();
		//		Scanner s=new Scanner(System.in);
				if(socket.isConnected()) {
					
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
							//String[] ar=messageFromChat.split(":");
							if(messageFromChat.contains("PLAY") && messageFromChat.contains(path)) {
								mediaPlayerComponent.mediaPlayer().controls().play();
							}
							else if(messageFromChat.contains("PAUSE") && messageFromChat.contains(path)) {
								mediaPlayerComponent.mediaPlayer().controls().pause();
							}
						}catch (IOException e) {
							// TODO: handle exception
							close();
						}
					}
				}
			}).start();
		}
	}
	public static Random rnd=new Random();
	private ClientChat clientChat;
	public App(String title, String moviename, boolean admin) {
		super(title);
		this.admin=admin;
		Socket socket;
		try {
			socket = new Socket("192.168.112.129",1234);
			clientChat=new ClientChat(socket, moviename+App.rnd.nextInt());
			clientChat.listenMessage();
			clientChat.sendMessage("Zdravo!");
			this.path = moviename;
		} 
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	}

	public void initialize() {
		this.setBounds(100, 100, 600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

		JPanel controlsPane = new JPanel();
		playButton = new JButton("Play");
		controlsPane.add(playButton);
		pauseButton = new JButton("Pause");
		controlsPane.add(pauseButton);
		contentPane.add(controlsPane, BorderLayout.SOUTH);
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb=new StringBuilder(path);
				sb.append(App.operationPlay);
				System.out.println(sb.toString()+"console");
				clientChat.sendMessage(sb.toString());
				mediaPlayerComponent.mediaPlayer().controls().play();
			}
		});
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb=new StringBuilder(path);
				sb.append(App.operationPause);
				clientChat.sendMessage(sb.toString());
				mediaPlayerComponent.mediaPlayer().controls().pause();
			}
		});
		playButton.setEnabled(admin);
		pauseButton.setEnabled(admin);
		this.setContentPane(contentPane);
		this.setVisible(true);
	}

	public void loadVideo() {
		mediaPlayerComponent.mediaPlayer().media().startPaused(path);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e);
		}
		App application = new App(TITLE, "m1.mp4",true);
		application.initialize();
		application.setVisible(true);
		application.loadVideo();
	}
}
