package net.virushd.pets.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.PetUtils;

public class WalkEvent implements Listener {

	@EventHandler
	public void onWalk(PlayerMoveEvent e) {

		// p.playEffect(PetUtils.getPet(p).getLocation(), Effect.HEART, null);

		Player p = e.getPlayer();

		if (p.getWorld().equals(SaveUtils.GetLocationFromFile(net.virushd.core.main.FileManager.config, "Spawns.Lobby").getWorld())) {
			if (PetUtils.hasPet(p) && CoreMain.players.contains(p)) {
				boolean isHide = FileManager.pets.getBoolean(p.getUniqueId().toString() + ".Hide");
				if (!isHide) {
					try {
						PetUtils.walkToLoc(PetUtils.getPet(p), p.getLocation(), 1.4);
					} catch (Exception ex) {
						PetUtils.spawnPet(p, p.getWorld());
						FileManager.pets.set(p.getUniqueId().toString() + ".Hide", false);
						FileManager.savePetsFile();
					}
				}
			}
		}
	}
}
