package data;

import java.util.ArrayList;

import gui.AmobaPanel;

public class Game {
	private int[][] map;
	
	public Game(){
		map = new int[AmobaPanel.K][AmobaPanel.K];
		for(int i = 0;i<AmobaPanel.K;i++)
			for(int j = 0;j<AmobaPanel.K;j++)
				map[i][j] = -1;
	}
	
	public Game(Game g){
		map = new int[AmobaPanel.K][AmobaPanel.K];
		for(int i = 0;i<AmobaPanel.K;i++)
			for(int j = 0;j<AmobaPanel.K;j++)
				map[i][j] = g.get(i,j);
	}
	
	public void initGame(int[][] g){
		for(int i = 0;i<AmobaPanel.K;i++)
			for(int j = 0;j<AmobaPanel.K;j++)
				map[i][j] = g[i][j];
	}
	
	public void putInMap(Point p, int type){
		map[p.getX()][p.getY()] = type;
	}
	
	public Game adddMove(Point p, int type){
		Game g = new Game(this);
		g.putInMap(p, type);
		return g;
	}
	
	public int isOver(){
		int lenght = 1;
		for(int i = 0;i<AmobaPanel.K;i++){
			for(int j = 0;j<AmobaPanel.K-1;j++){
				if(map[i][j]!=-1 && map[i][j] == map[i][j+1]){
					lenght++;
				}else lenght = 1;
				if(lenght == AmobaPanel.J){
					return map[i][j];
				}
			}
			lenght = 1;
		}
		
		for(int i = 0;i<AmobaPanel.K;i++){
			for(int j = 0;j<AmobaPanel.K-1;j++){
				if(map[j][i]!=-1 && map[j][i] == map[j+1][i]){
					lenght++;
				}else lenght = 1;
				if(lenght == AmobaPanel.J){
					return map[j][i];
				}
			}
			lenght = 1;
		}		
		
		for(int j = 0;j<=AmobaPanel.K-AmobaPanel.J;j++){
			for(int i = 0;i<AmobaPanel.K-j-1;i++){
				if(map[i+j][i]!=-1 && map[i+j][i] == map[i+j+1][i+1]){
					lenght++;
				}else lenght = 1;
				if(lenght == AmobaPanel.J){
					return map[i+j][i];
				}
			}
			lenght = 1;
			for(int i = 0;i<AmobaPanel.K-j-1;i++){
				if(map[i][j+i]!=-1 && map[i][i+j] == map[i+1][i+j+1]){
					lenght++;
				}else lenght = 1;
				if(lenght == AmobaPanel.J){
					return map[i][i+j];
				}
			}
			lenght = 1;
		}
		
		for(int j = 0;j<=AmobaPanel.K-AmobaPanel.J;j++){
			for(int i = 0;i<AmobaPanel.K-j-1;i++){
				if(map[i+j][AmobaPanel.K-1-i]!=-1 && map[i+j][AmobaPanel.K-1-i] == map[i+j+1][AmobaPanel.K-1-i-1]){
					lenght++;
				}else lenght = 1;
				if(lenght == AmobaPanel.J){
					return map[i+j][AmobaPanel.K-1-i];
				}
			}
			lenght = 1;
			for(int i = 0;i<AmobaPanel.K-j-1;i++){
				if(map[i][AmobaPanel.K-1-i-j]!=-1 && map[i][AmobaPanel.K-1-i-j] == map[i+1][AmobaPanel.K-1-i-1-j]){
					lenght++;
				}else lenght = 1;
				if(lenght == AmobaPanel.J){
					return map[i][AmobaPanel.K-1-i-j];
				}
			}
			lenght = 1;
		}
		for(int i = 0;i<AmobaPanel.K;i++)
			for(int j = 0;j<AmobaPanel.K;j++)
				if(map[i][j] == -1) return -2;
		return -1;
	}
	
	public ArrayList<Point> getFreePositions(){
		ArrayList<Point> pos = new ArrayList<Point>();
		for(int i = 0;i<AmobaPanel.K;i++){
			for(int j = 0;j<AmobaPanel.K;j++){
				if(map[i][j] == -1){
					pos.add(new Point(i,j));
				}
			}
		}
		return pos;
	}
	
	public ArrayList<Point> getFreeNeighbourPositions(){
		ArrayList<Point> pos = new ArrayList<Point>();
		for(int i = 0;i<AmobaPanel.K;i++){
			for(int j = 0;j<AmobaPanel.K;j++){
				if(map[i][j] != -1){
					if(i-1>=0){
						add(i-1,j,pos);
						if(j-1>=0){
							add(i-1,j-1,pos);
						}
						if(j+1<AmobaPanel.K){
							add(i-1,j+1,pos);
						}
					}
					if(i+1<AmobaPanel.K){
						add(i+1,j,pos);
						if(j-1>=0){
							add(i+1,j-1,pos);
						}
						if(j+1<AmobaPanel.K){
							add(i+1,j+1,pos);
						}
					}
					if(j-1>=0){
						add(i,j-1,pos);
					}
					if(j+1<AmobaPanel.K){
						add(i,j+1,pos);
					}
				}
			}
		}
		return pos;
	}
	
	private void add(int i, int j, ArrayList<Point> list){
		if(map[i][j]!=-1) return;
		else{
			if(list.isEmpty()) list.add(new Point(i,j));
			else{
				Point tmp = new Point(i,j);
				for(Point p :list){
					if(p.equals(tmp)){
						return;
					}
				}
				if(tmp!=null) list.add(tmp);
			}
		}
	}
	
	public int get(int i, int j){
		return map[i][j];
	}

	public boolean isEmpty() {
		for(int i = 0;i<AmobaPanel.K;i++){
			for(int j = 0;j<AmobaPanel.K;j++){
				if(map[i][j] != -1) return false; 
			}
		}
		return true;
	}

	public int goodness(int type) {
		int lenght = 1;
		int maxlength = 0;
		for(int i = 0;i<AmobaPanel.K;i++){
			for(int j = 0;j<AmobaPanel.K-1;j++){
				if(map[i][j]!=-1 && map[i][j] == map[i][j+1] && map[i][j] == type){
					lenght++;
				}else lenght = 1;
				if(lenght > maxlength){
					maxlength = lenght;
				}
			}
			lenght = 1;
		}
		
		for(int i = 0;i<AmobaPanel.K;i++){
			for(int j = 0;j<AmobaPanel.K-1;j++){
				if(map[j][i]!=-1 && map[j][i] == map[j+1][i] && map[j][j] == type){
					lenght++;
				}else lenght = 1;
				if(lenght > maxlength){
					maxlength = lenght;
				}
			}
			lenght = 1;
		}		
		
		for(int j = 0;j<=AmobaPanel.K-AmobaPanel.J;j++){
			for(int i = 0;i<AmobaPanel.K-j-1;i++){
				if(map[i+j][i]!=-1 && map[i+j][i] == map[i+j+1][i+1] && map[i+j][i] == type){
					lenght++;
				}else lenght = 1;
				if(lenght > maxlength){
					maxlength = lenght;
				}
			}
			lenght = 1;
			for(int i = 0;i<AmobaPanel.K-j-1;i++){
				if(map[i][j+i]!=-1 && map[i][i+j] == map[i+1][i+j+1] && map[i][i+j] == type){
					lenght++;
				}else lenght = 1;
				if(lenght > maxlength){
					maxlength = lenght;
				}
			}
			lenght = 1;
		}
		
		for(int j = 0;j<=AmobaPanel.K-AmobaPanel.J;j++){
			for(int i = 0;i<AmobaPanel.K-j-1;i++){
				if(map[i+j][AmobaPanel.K-1-i]!=-1 && map[i+j][AmobaPanel.K-1-i] == map[i+j+1][AmobaPanel.K-1-i-1] && map[i+j][AmobaPanel.K-1-i] == type){
					lenght++;
				}else lenght = 1;
				if(lenght > maxlength){
					maxlength = lenght;
				}
			}
			lenght = 1;
			for(int i = 0;i<AmobaPanel.K-j-1;i++){
				if(map[i][AmobaPanel.K-1-i-j]!=-1 && map[i][AmobaPanel.K-1-i-j] == map[i+1][AmobaPanel.K-1-i-1-j] && map[i][AmobaPanel.K-1-i-j] == type){
					lenght++;
				}else lenght = 1;
				if(lenght > maxlength){
					maxlength = lenght;
				}
			}
			lenght = 1;
		}
		return (int) Math.pow(10, maxlength);
	}
}
