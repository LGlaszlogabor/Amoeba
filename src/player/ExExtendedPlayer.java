package player;

import java.util.ArrayList;
import java.util.Random;

import data.Game;
import data.Point;
import events.PlayerMoveEvent;
import events.PlayerMoveListener;
import gui.AmobaPanel;

public class ExExtendedPlayer implements PlayerMoveListener{
	private int type;
	private Point move;
	private boolean firstMove;
	
	public ExExtendedPlayer(int type){
		this.type = type;
		firstMove = true;
	}
	
	public void putNext(){
		if(firstMove && AmobaPanel.isEmpty()){
			Random r = new Random();
			move = new Point(r.nextInt(AmobaPanel.K),r.nextInt(AmobaPanel.K));
			AmobaPanel.set(move.getX(), move.getY(), type);
			firstMove = false;
		}
		else{
			Game g = new Game(AmobaPanel.mainGame);
			minMaxGameAB(g,-9999999,9999999, type,0);
			AmobaPanel.set(move.getX(), move.getY(), type);
		}
	}
	
	public int minMaxGameAB(Game g,int alfa,int beta, int type, int depth){
		if(depth > 4) return g.goodness(this.type);
		int who = g.isOver();
		if(this.type == 1){
			if(who == 1){
				return 10-depth;
			}
			else if(who == 0){
				return depth-10;
			}
			else if(who == -1)
				return 0;
		}else{
			if(who == 1){
				return depth-10;
			}
			else if(who == 0){
				return 10-depth;
			}
			else if(who == -1)
				return 0;
		}
		ArrayList<Integer> scores = new ArrayList<Integer>();
		ArrayList<Point> moves = new ArrayList<Point>();
		int v;
		
		if(type == this.type){
			v = -9999999;
			for(Point p:g.getFreeNeighbourPositions()){
				Game possibleGame = g.adddMove(p,type);
				if(this.type == 1)
					v = Math.max(v,minMaxGameAB(possibleGame,alfa,beta,0,depth+1));
				else 
					v = Math.max(v,minMaxGameAB(possibleGame,alfa,beta,1,depth+1));
				alfa = Math.max(alfa, v);
				scores.add(v);
				moves.add(p);
				if(beta<=alfa){
					break;
				}				
			}
			move = moves.get(scores.indexOf(v));
			return scores.get(scores.indexOf(v));
		}else{
			v = 9999999;
			for(Point p:g.getFreeNeighbourPositions()){
				Game possibleGame = g.adddMove(p,type);
				if(this.type == 1)
					v = Math.min(v,minMaxGameAB(possibleGame,alfa,beta,1,depth+1));
				else 
					v = Math.min(v,minMaxGameAB(possibleGame,alfa,beta,0,depth+1));
				beta = Math.min(beta, v);
				scores.add(v);
				moves.add(p);
				if(beta<=alfa){
					break;
				}
			}
			move = moves.get(scores.indexOf(v));
			return scores.get(scores.indexOf(v));
		}
	}
	
	@Override
	public void playerMoved(PlayerMoveEvent pme) {
		putNext();
	}
}
