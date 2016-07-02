package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import client.Client;
import client.Server;
import player.AutomataPlayer;
import player.BOTPlayer;
import player.ExExtendedPlayer;
import player.ExtendedAutomataPlayer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class AmobaDrawer extends JFrame{
	private static final long serialVersionUID = 1L;
	public static JTextField kField;
	public static JTextField jField;
	public static JButton btnGenerate;
	private AmobaPanel panel;
	private JCheckBox botFirstCheckBox;
	private BOTPlayer botPlayer;
	private AutomataPlayer autoPlayer;
	private ExtendedAutomataPlayer exAutoPlayer;
	private ExExtendedPlayer exExPlayer;
	private JTextField portField;
	private JTextField ipField;
	private JCheckBox isServerCheckBox;

	public AmobaDrawer(){
		getContentPane().setLayout(null);
		
		kField = new JTextField();
		kField.setBounds(35, 11, 86, 20);
		getContentPane().add(kField);
		kField.setColumns(10);
		
		JLabel lblK = new JLabel("K:");
		lblK.setBounds(25, 14, 20, 14);
		getContentPane().add(lblK);
		
		JLabel lblJ = new JLabel("J:");
		lblJ.setBounds(131, 14, 20, 14);
		getContentPane().add(lblJ);
		
		jField = new JTextField();
		jField.setBounds(141, 11, 86, 20);
		getContentPane().add(jField);
		jField.setColumns(10);
		
		JButton btnStartPvc = new JButton("Start PvC");
		btnStartPvc.setBounds(336, 10, 89, 23);
		btnStartPvc.setVisible(false);
		btnStartPvc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int k = Integer.parseInt(kField.getText());
				int j = Integer.parseInt(jField.getText());
				if(k==j && k==3){
					if(botFirstCheckBox.isSelected()){
						botPlayer = new BOTPlayer(0);
						botPlayer.putNext();
						AmobaPanel.addPlayerMoveListener(botPlayer);
					}else{
						botPlayer = new BOTPlayer(1);
						AmobaPanel.addPlayerMoveListener(botPlayer);
					}
				}
				else if(k==j && k==4){
					if(botFirstCheckBox.isSelected()){
						autoPlayer = new AutomataPlayer(0);
						autoPlayer.putNext();
						AmobaPanel.addPlayerMoveListener(autoPlayer);
					}else{
						autoPlayer = new AutomataPlayer(1);
						AmobaPanel.addPlayerMoveListener(autoPlayer);
					}
				}
				else if(k==5 && j==4){
					if(botFirstCheckBox.isSelected()){
						exAutoPlayer = new ExtendedAutomataPlayer(0);
						exAutoPlayer.putNext();
						AmobaPanel.addPlayerMoveListener(exAutoPlayer);
					}else{
						exAutoPlayer = new ExtendedAutomataPlayer(1);
						AmobaPanel.addPlayerMoveListener(exAutoPlayer);
					}
				}else if(k<5 && j<5){
					if(botFirstCheckBox.isSelected()){
						autoPlayer = new AutomataPlayer(0);
						autoPlayer.putNext();
						AmobaPanel.addPlayerMoveListener(autoPlayer);
					}else{
						autoPlayer = new AutomataPlayer(1);
						AmobaPanel.addPlayerMoveListener(autoPlayer);
					}
				}else if(j==5 && k>6){
					if(botFirstCheckBox.isSelected()){
						exExPlayer = new ExExtendedPlayer(0);
						exExPlayer.putNext();
						AmobaPanel.addPlayerMoveListener(exExPlayer);
					}else{
						exExPlayer = new ExExtendedPlayer(1);
						AmobaPanel.addPlayerMoveListener(exExPlayer);
					}
				}
			}
		});
		getContentPane().add(btnStartPvc);
		
		panel = new AmobaPanel();
		panel.setBounds(10, 45, 524, 524);
		getContentPane().add(panel);
		
		btnGenerate = new JButton("Generate");
		btnGenerate.setBounds(237, 10, 89, 23);
		btnGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(AmobaPanel.networkMode){
					int k = Integer.parseInt(kField.getText());
					int j = Integer.parseInt(jField.getText());
					panel.generate(k,j);
				}
				else if(kField.getText().isEmpty() || jField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please fill all the fields!");
				}else{
					AmobaPanel.removePlayerMoveListener(botPlayer);
					AmobaPanel.removePlayerMoveListener(autoPlayer);
					AmobaPanel.removePlayerMoveListener(exAutoPlayer);
					AmobaPanel.removePlayerMoveListener(exExPlayer);
					int k = Integer.parseInt(kField.getText());
					int j = Integer.parseInt(jField.getText());
					if(j>k) JOptionPane.showMessageDialog(null, "J must be smaller or equal to K");
					else{
						if((k == 3 && j==3) || (k == 4 && j==4) || (k == 5 && j==4) ||(k<5&& j<5) || (j==5 && k>6)){
							btnStartPvc.setVisible(true);
							botFirstCheckBox.setVisible(true);
						}
						else {	
							btnStartPvc.setVisible(false);
							botFirstCheckBox.setVisible(false);
						}
						panel.generate(k,j);
		
						revalidate();
						repaint();
					}
				}
			}
		});
		getContentPane().add(btnGenerate);
		
		botFirstCheckBox = new JCheckBox("BOT first");
		botFirstCheckBox.setBounds(431, 10, 97, 23);
		getContentPane().add(botFirstCheckBox);
		
		isServerCheckBox = new JCheckBox("Server");
		isServerCheckBox.setBounds(565, 73, 97, 23);
		getContentPane().add(isServerCheckBox);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(544, 117, 34, 14);
		getContentPane().add(lblPort);
		
		portField = new JTextField();
		portField.setText("2222");
		portField.setBounds(588, 114, 86, 20);
		getContentPane().add(portField);
		portField.setColumns(10);
		
		JLabel lblIp = new JLabel("Ip:");
		lblIp.setBounds(558, 157, 20, 14);
		getContentPane().add(lblIp);
		
		ipField = new JTextField();
		ipField.setText("127.0.0.1");
		ipField.setBounds(588, 154, 86, 20);
		getContentPane().add(ipField);
		ipField.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(565, 199, 89, 23);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ipField.getText().isEmpty() || portField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please fill all the fields!");
				}else if(AmobaPanel.K == 0 && isServerCheckBox.isSelected()){
					JOptionPane.showMessageDialog(null, "Please generate a table first!");
				}else{ 
					AmobaPanel.networkMode = true;
					if(isServerCheckBox.isSelected()){
						new Server(ipField.getText(),Integer.parseInt(portField.getText()));
						AmobaPanel.isServer = true;
					}else{
						new Client(ipField.getText(),Integer.parseInt(portField.getText()));
					}
					btnGenerate.setVisible(false);
					btnStartPvc.setVisible(false);
					isServerCheckBox.setVisible(false);
				}
			}
		});
		getContentPane().add(btnStart);
		botFirstCheckBox.setVisible(false);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100,100, 692,650);
		setVisible(true);
	}
}
