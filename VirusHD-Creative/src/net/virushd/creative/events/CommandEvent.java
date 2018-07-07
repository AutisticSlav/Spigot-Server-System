package net.virushd.creative.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.creative.main.CreativeMain;

public class CommandEvent implements Listener {
	
	@EventHandler
	public void onCommand (PlayerCommandPreprocessEvent e) {
		
		Player p = e.getPlayer();
		
		// debug
		if (CreativeMain.players.contains(p)) {
			if (CoreMain.debug()) {
				CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " executed the command " + e.getMessage());
			}
		}
	}
}
