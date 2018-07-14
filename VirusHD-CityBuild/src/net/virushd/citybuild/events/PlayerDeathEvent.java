package net.virushd.citybuild.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.core.main.CoreMain;

public class PlayerDeathEvent implements Listener {

	@EventHandler
	public void onDeath(org.bukkit.event.entity.PlayerDeathEvent e) {

		Player p = e.getEntity();

		// debug
		if (CityBuildMain.getPlayers().contains(p)) {
			if (CoreMain.debug()) {
				CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " died.");
			}
		}
	}
}
