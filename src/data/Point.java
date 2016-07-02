package data;

public class Point {
	private int x, y;
	
	public Point(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	@Override
	public boolean equals(Object obj) {
		int x = ((Point) obj).getX();
		int y = ((Point) obj).getY();
		return this.x == x && this.y == y;
	}
}
