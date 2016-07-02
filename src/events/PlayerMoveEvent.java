package events;

import java.util.EventObject;

public class PlayerMoveEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private int type;

	public PlayerMoveEvent(Object source, int type){
		super(source);
		this.type = type;
	}

	public int getUser(){
		return type;
	}
}