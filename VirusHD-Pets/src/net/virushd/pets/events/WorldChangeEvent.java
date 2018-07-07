package net.virushd.pets.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.main.PetsMain;
import net.virushd.pets.pet.PetUtils;

@Deprecated
public class WorldChangeEvent implements Listener {

	PetsMain pl;

	public WorldChangeEvent(PetsMain main) {
		this.pl = main;
	}

	@EventHandler
	public void WorldEvent(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		if (PetUtils.hasPet(p)) {
			PetUtils.despawnPet(p, e.getFrom());
			boolean isHide = FileManager.pets.getBoolean(p.getUniqueId().toString() + ".Hide");

			Bukkit.getServer().getScheduler().runTaskLater(pl, new Runnable() {

				@Override
				public void run() {
					if (p.getWorld().equals(Bukkit.getWorld(net.virushd.core.main.FileManager.config.getString("Worlds.Lobby")))) {
						if (isHide == false) {
							PetUtils.spawnPet(p, p.getWorld());
						}
					}
				}
			}, 20L);
		}
	}
}
