package net.virushd.pets.events;

import net.virushd.pets.pet.PetUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import net.virushd.pets.main.FileManager;

public class TargetEvent implements Listener {

	@EventHandler
	public void onPetTarget(EntityTargetEvent e) {

		for (Player players : Bukkit.getOnlinePlayers()) {
			if (PetUtils.hasPet(players)) {

				// cancel pet targetting
				if (e.getEntity().getUniqueId().toString().equals(FileManager.pets.getString(players.getUniqueId().toString() + ".PetUUID"))) {
					e.setCancelled(true);
				}
			}
		}
	}
}
