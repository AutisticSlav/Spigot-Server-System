package net.virushd.pets.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.PetUtils;

public class PetCombustEvent implements Listener {

	@EventHandler
	public void onCombust(EntityCombustEvent e) {

		for (Player players : Bukkit.getOnlinePlayers()) {
			if (PetUtils.hasPet(players)) {

				// cancel if the pet combusts
				if (e.getEntity().getUniqueId().toString().equals(FileManager.pets.getString(players.getUniqueId().toString() + ".PetUUID"))) {
					e.setCancelled(true);
				}
			}
		}
	}
}
