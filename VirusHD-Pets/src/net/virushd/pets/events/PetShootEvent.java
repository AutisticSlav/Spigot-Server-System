package net.virushd.pets.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.PetUtils;

public class PetShootEvent implements Listener {

	@EventHandler
	public void onEntityShoot(ProjectileLaunchEvent e) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (PetUtils.hasPet(players)) {
				if (e.getEntity().getUniqueId().toString().equals(FileManager.pets.getString(players.getUniqueId().toString() + ".PetUUID"))) {
					e.setCancelled(true);
				}
			}
		}
//		
//		if (entity instanceof Wither) {
//			event.setCancelled(true);
//			return;
//		}
//		if (entity instanceof Blaze) {
//			event.setCancelled(true);
//			return;
//		}
	}
}
