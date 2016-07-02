package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import client.Client;
import client.Server;

public class Element extends JButton{
	private static final long serialVersionUID = 1L;
	Element(int type,int x, int y){
		super();
		int size = AmobaPanel.W / AmobaPanel.K;
		setBounds(x*size,y*size,size,size);
		setIcon(new ImageIcon(AmobaPanel.empty.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(AmobaPanel.networkMode){
					if(AmobaPanel.isServer ){
						if(Server.canPut){
							if(Server.type == 0){
								setIcon(new ImageIcon(AmobaPanel.x.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
								setEnabled(false);
								AmobaPanel.setAsPlayer(x, y, 0);
							}else{
								setIcon(new ImageIcon(AmobaPanel.o.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
								setEnabled(false);
								AmobaPanel.setAsPlayer(x, y, 1);
							}
							Server.canPut = false;
						}else return;
					}else{
						if(Client.canPut){
							if(Client.type == 0){
								setIcon(new ImageIcon(AmobaPanel.x.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
								setEnabled(false);
								AmobaPanel.setAsPlayer(x, y, 0);
							}else{
								setIcon(new ImageIcon(AmobaPanel.o.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
								setEnabled(false);
								AmobaPanel.setAsPlayer(x, y, 1);
							}
							Client.canPut = false;
						}else return;
					}
				}
				else{
					if(AmobaPanel.who == 0){
						setIcon(new ImageIcon(AmobaPanel.x.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
						setEnabled(false);
						AmobaPanel.who = 1;
						AmobaPanel.setAsPlayer(x, y, 0);
					}else{
						setIcon(new ImageIcon(AmobaPanel.o.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
						setEnabled(false);
						AmobaPanel.who = 0;
						AmobaPanel.setAsPlayer(x, y, 1);
					}
				}
			}		
		});
		setSelected(true);
	}
	
	public void click(int type){
		int size = AmobaPanel.W / AmobaPanel.K;
		if(AmobaPanel.networkMode){
			if(type == 0){
				setIcon(new ImageIcon(AmobaPanel.x.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
			}else{
				setIcon(new ImageIcon(AmobaPanel.o.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
			}
			setEnabled(false);
		}else{
			if(AmobaPanel.who == 0){
				setIcon(new ImageIcon(AmobaPanel.x.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
				AmobaPanel.who = 1;
			}else{
				setIcon(new ImageIcon(AmobaPanel.o.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH)));
				AmobaPanel.who = 0;
			}
			setEnabled(false);
		}
	}
}
