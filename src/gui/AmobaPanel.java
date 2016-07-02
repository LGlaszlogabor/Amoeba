package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Client;
import client.Server;
import data.Game;
import data.Point;
import events.PlayerMoveEvent;
import events.PlayerMoveListener;

public class AmobaPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public static ImageIcon x = new ImageIcon("src//x.png");
	public static ImageIcon o = new ImageIcon("src//o.png");
	public static ImageIcon empty= new ImageIcon("src//empty.png");
	public static int K = 0, J = 0, W = 524;
	public static int who = 0;
	public static Game mainGame;
	private static Element[][] elements;
	private static LinkedList<PlayerMoveListener> moveListeners = new LinkedList<PlayerMoveListener>();
	public static boolean networkMode = false, isServer = false; 
	
	AmobaPanel(){
		super();
		setLayout(null);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		for(Component comp:getComponents()){
			comp.paint(g);
		}
	}	
	
	public void generate(int k, int j){
		removeAll();
		AmobaPanel.K = k;
		AmobaPanel.J = j;
		mainGame = new Game();
		elements = new Element[k][k];
		who = 0;
		for(int i = 0;i<k;i++){
			for(int s = 0;s<k;s++){
				elements[i][s] = new Element(0,i,s);
				add(elements[i][s]);
			}
		}	
		revalidate();
		repaint();
	}	
	
	public static void set(int x, int y, int type){
		mainGame.putInMap(new Point(x, y), type);
		elements[x][y].click(type);
		checkForWin();
	}
	
	public static void setAsPlayer(int x, int y, int type){
		if(networkMode){
			mainGame.putInMap(new Point(x, y), type);
			if(isServer) Server.notifyOponent(x,y);
			else Client.notifyOponent(x,y);
			checkForWin();
		}else{
			mainGame.putInMap(new Point(x, y), type);
			checkForWin();
			notifyPlayerMove(type);
		}
	}
	
	private static void checkForWin(){
		int end = mainGame.isOver();
		if(networkMode){
			if(-2 != end){
				if(isServer) Server.disconnect();
				else Client.disconnect();
			}
		}
		switch(end){
		case 0:
			JOptionPane.showMessageDialog(null, "X Player wins!");
			break;
		case 1:
			JOptionPane.showMessageDialog(null, "O Player wins!");
			break;
		case -1:
			JOptionPane.showMessageDialog(null, "It's a draw!");
			break;
		}
	}
	
	public static boolean hasNotEnded(){
		return -2 == mainGame.isOver();
	}
	
	public static boolean isEmpty(){
		return mainGame.isEmpty();
	}
	
	public static synchronized void notifyPlayerMove(int type) {
		PlayerMoveEvent pme = new PlayerMoveEvent(new Object(), type); 
		for(PlayerMoveListener pml:moveListeners) { 
			pml.playerMoved(pme); 
		} 
	}
	
	public static synchronized void addPlayerMoveListener(PlayerMoveListener dcl){ 
		moveListeners.add(dcl);
	}
	
	public static synchronized void removePlayerMoveListener(PlayerMoveListener dcl){ 
		moveListeners.remove(dcl);
	}
	
}
