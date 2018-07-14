package net.virushd.creative.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.virushd.core.main.CoreMain;
import net.virushd.creative.main.CreativeMain;

public class PlayerDeathEvent implements Listener {

	@EventHandler
	public void onDeath(org.bukkit.event.entity.PlayerDeathEvent e) {

		Player p = e.getEntity();

		// debug
		if (CreativeMain.getPlayers().contains(p)) {
			if (CoreMain.debug()) {
				CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " died.");
			}
		}
	}
}
