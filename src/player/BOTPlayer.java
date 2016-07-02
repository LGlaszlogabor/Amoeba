package player;

import java.util.ArrayList;

import data.Game;
import data.Point;
import events.PlayerMoveEvent;
import events.PlayerMoveListener;
import gui.AmobaPanel;

public class BOTPlayer implements PlayerMoveListener{
	private int type;
	private Point move;
	
	public BOTPlayer(int type){
		this.type = type;
	}
	
	public void putNext(){
		Game g = new Game(AmobaPanel.mainGame);
		minMaxGame(g, type);
		AmobaPanel.set(move.getX(), move.getY(), type);
	}
	
	public int minMaxGame(Game g, int type){
		int who = g.isOver();
		if(this.type == 1){
			if(who == 1){
				return 10;
			}
			else if(who == 0){
				return -10;
			}
			else if(who == -1)
				return 0;
		}else{
			if(who == 1){
				return -10;
			}
			else if(who == 0){
				return 10;
			}
			else if(who == -1)
				return 0;
		}
		ArrayList<Integer> scores = new ArrayList<Integer>();
		ArrayList<Point> moves = new ArrayList<Point>();
		for(Point p:g.getFreePositions()){
			Game possibleGame = g.adddMove(p,type);
			if(type == 1)
				scores.add(minMaxGame(possibleGame,0));
			else
				scores.add(minMaxGame(possibleGame,1));
			moves.add(p);
		}
		
		if(type == this.type){
			move = moves.get(maxIndex(scores));
			return scores.get(maxIndex(scores));
		}else{
			move = moves.get(minIndex(scores));
			return scores.get(minIndex(scores));
		}
	}
	
	private int maxIndex(ArrayList<Integer> sc){
		int maxIndex = 0;
		for(int i = 1;i<sc.size();i++){
			if(sc.get(i) > sc.get(maxIndex)){
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	private int minIndex(ArrayList<Integer> sc){
		int minIndex = 0;
		for(int i = 1;i<sc.size();i++){
			if(sc.get(i) < sc.get(minIndex)){
				minIndex = i;
			}
		}
		return minIndex;
	}

	@Override
	public void playerMoved(PlayerMoveEvent pme) {
		putNext();
	}
}
