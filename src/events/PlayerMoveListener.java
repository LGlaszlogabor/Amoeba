package events;

import java.util.EventListener;

public interface PlayerMoveListener extends EventListener {
	public void playerMoved(PlayerMoveEvent pme);
}
