package net.virushd.pets.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import net.virushd.pets.main.FileManager;

public class InteractEvent implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent e) {

		String PetUUID;

		for (Player players : Bukkit.getOnlinePlayers()) {
			if (FileManager.pets.contains(players.getUniqueId().toString())) {

				PetUUID = FileManager.pets.getString(players.getUniqueId().toString() + ".PetUUID");

				// cancel right click on pet
				if (e.getRightClicked().getUniqueId().toString().equals(PetUUID)) {
					e.setCancelled(true);
				}
			}
		}
	}
}
