package net.virushd.core.events;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		Player p = e.getPlayer();

		// leave
		PlayerManager.leave(p);

		// diable the default message
		e.setQuitMessage(null);

		// debug
		if (CoreMain.debug()) {
			CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " left the game.");
		}
	}
}
