package net.virushd.pets.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.PetUtils;

public class QuitEvent implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (PetUtils.hasPet(p)) {
			boolean isHide = FileManager.pets.getBoolean(p.getUniqueId().toString() + ".Hide");
			if (isHide == false) {
				PetUtils.despawnPet(p, p.getWorld());
			}
		}
	}
}
