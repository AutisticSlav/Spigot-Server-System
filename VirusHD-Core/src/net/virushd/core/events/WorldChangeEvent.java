package net.virushd.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.SaveUtils;

public class WorldChangeEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void WorldEvent(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();

		if (CoreMain.isNormal(p)) {
			
			// from Lobby
			if (e.getFrom().equals(SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.Lobby").getWorld())) {
				if (CoreMain.players.contains(p)) {
					CoreMain.players.remove(p);
					
					if (ItemClickEvent.HideMode.contains(p)) {
						ItemClickEvent.HideMode.remove(p);
						ItemClickEvent.hideOff(p);
					}
				}
			}

			// to Lobby
			if (p.getWorld().equals(SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.Lobby").getWorld())) {
				if (CoreMain.players.contains(p)) {

				} else {
					CoreMain.players.add(p);
				}
			}
		}
	}
}
