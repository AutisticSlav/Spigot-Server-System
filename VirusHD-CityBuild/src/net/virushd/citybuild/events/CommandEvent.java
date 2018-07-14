package net.virushd.citybuild.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.core.main.CoreMain;

public class CommandEvent implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();

		// debug
		if (CityBuildMain.getPlayers().contains(p)) {
			if (CoreMain.debug()) {
				CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " executed the command " + e.getMessage());
			}
		}
	}
}
