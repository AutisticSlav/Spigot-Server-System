package net.virushd.pets.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.main.PetsMain;
import net.virushd.pets.pet.PetUtils;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		Bukkit.getServer().getScheduler().runTaskLater(PetsMain.main, () -> {
			if (PetUtils.hasPet(p)) {
				boolean isHide = FileManager.pets.getBoolean(p.getUniqueId().toString() + ".Hide");

				// when the pet is not hidden
				if (!isHide) {
					if (p.getWorld().getName().equalsIgnoreCase(net.virushd.core.main.FileManager.locations.getString("Spawn.World"))) {

						String PetUUID = FileManager.pets.getString(p.getUniqueId().toString() + ".PetUUID");

						// check if the pet already exists
						for (Entity entities : p.getWorld().getEntities()) {
							if (entities instanceof Animals || entities instanceof Monster) {
								if (entities.getUniqueId().toString().equals(PetUUID)) {
									return;
								}
							}
						}

						// if not spawn the pet
						PetUtils.spawnPet(p, p.getWorld());
					} else {
						onJoin(e);
					}
				}
			}
		}, 40L);
	}
}
