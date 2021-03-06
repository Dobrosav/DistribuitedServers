package rs.bg.ac.etf.kdp.vd180005.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Dobrosav  Vlaskovic
 */
public class ClientGUI extends javax.swing.JFrame {
	private Client client;
	private ArrayList<String> movies=new ArrayList<String>();
	private String serverAddress;
	private String centralServerAddress;
	private int serverPort;
	private int centralServerPort;
	private String filename;

	/**
	 * Creates new form ClientGUI
	 */
	public ClientGUI() {
		initComponents();
		
		client = new Client();
		movies.add("movie1.mp4");
		movies.add("movie2.mp4");
		movies.add("m1.mp4");
		movies.add("m4.mp4");
		movies.add("m3.mp4");
		movies.add("m2.mp4");
		movies.add("m5.mp4");
		movies.add("m6.mp4");
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfServerAddress = new javax.swing.JTextField();
        spinPort = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        tfFilename = new javax.swing.JTextField();
        btnGet = new javax.swing.JButton();
        btnPut = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfCentralServerAddress = new javax.swing.JTextField();
        spinCentralServerPort = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client");
        setResizable(false);

        jLabel1.setText("Server Address");

        jLabel2.setText("Server Port");

        tfServerAddress.setText("localhost");

        spinPort.setModel(new javax.swing.SpinnerNumberModel(9000, 1, 65535, 1));
        spinPort.setEditor(new javax.swing.JSpinner.NumberEditor(spinPort, "0000"));

        jLabel3.setText("Filename");

        tfFilename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFilenameActionPerformed(evt);
            }
        });

        btnGet.setText("Get");
        btnGet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetActionPerformed(evt);
            }
        });

        btnPut.setText("Put");
        btnPut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPutActionPerformed(evt);
            }
        });

        jLabel4.setText("Central Server Address");

        jLabel5.setText("Central Server Port");

        tfCentralServerAddress.setText("localhost");

        spinCentralServerPort.setModel(new javax.swing.SpinnerNumberModel(8080, 1, 65535, 1));
        spinCentralServerPort.setEditor(new javax.swing.JSpinner.NumberEditor(spinCentralServerPort, "0000"));
        spinCentralServerPort.setEnabled(false);

        jButton1.setText("Join");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfCentralServerAddress)
                            .addComponent(spinCentralServerPort, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfFilename)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfServerAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(spinPort, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGet, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPut, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spinPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfFilename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfCentralServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spinCentralServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGet)
                    .addComponent(btnPut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

	private void takeUserInput() {
		serverAddress = tfServerAddress.getText();
		centralServerAddress = tfCentralServerAddress.getText();
		serverPort = (int)spinPort.getValue();
		centralServerPort = (int)spinCentralServerPort.getValue();
		filename = tfFilename.getText();
		
	}
	
    private void btnPutActionPerformed(java.awt.event.ActionEvent evt) {                                       
		takeUserInput();
		String s=tfFilename.getText();
		if(s.contains(".mp4")) {
		System.out.println(tfFilename.getText());
		movies.add(tfFilename.getText());
		client.sendPutRequest(centralServerAddress, centralServerPort, filename);
		}
		else 
			tfFilename.setText("nije mp4");
		//client.sendPutRequest(centralServerAddress, centralServerPort, filename);
    }                                      

    private void btnGetActionPerformed(java.awt.event.ActionEvent evt) {                                       
    	takeUserInput();
        if(movies.contains(tfFilename.getText())) {
        	App application = new App("My video player", tfFilename.getText(),true);
    		application.initialize();
    		application.setVisible(true);
    		application.loadVideo();
    		this.setVisible(false);
        }
        else
        	tfFilename.setText("nema filma");
	///	client.sendGetRequest(serverAddress, serverPort, filename);
    }                                      

    private void tfFilenameActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    	if(movies.contains(tfFilename.getText())) {
        	App application = new App("My video player", tfFilename.getText(),false);
    		application.initialize();
    		application.setVisible(true);
    		application.loadVideo();
    		this.setVisible(false);
        }
        else
        	tfFilename.setText("nema filma");
	}
                                            

	/**
	 * @param args the command line arguments
	 */
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ClientGUI().setVisible(true);
			}
		});
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnGet;
    private javax.swing.JButton btnPut;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSpinner spinCentralServerPort;
    private javax.swing.JSpinner spinPort;
    private javax.swing.JTextField tfCentralServerAddress;
    private javax.swing.JTextField tfFilename;
    private javax.swing.JTextField tfServerAddress;
    // End of variables declaration                   
}
