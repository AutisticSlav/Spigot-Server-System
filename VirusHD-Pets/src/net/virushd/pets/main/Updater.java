package net.virushd.pets.main;

import net.virushd.core.main.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.virushd.core.api.SaveUtils;
import net.virushd.pets.pet.PetUtils;

public class Updater {

	// make cute hearts on top of the pet
	public static void petHearts() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PetsMain.main, () -> {
			for (Player players : PlayerManager.getPlayers()) {
				if (PetUtils.hasPet(players)) {
					try {
						Location loc = ((LivingEntity) PetUtils.getPet(players)).getEyeLocation().add(0, 0.3, 0);
						players.playEffect(loc, Effect.HEART, null);
					} catch (Exception ignored) {

					}
				}
			}
		}, 5L, 5L);
	}

	// update the pet's visibility
	public static void petVisibility() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PetsMain.main, () -> {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (PetUtils.hasPet(players)) {
					boolean isHide = FileManager.pets.getBoolean(players.getUniqueId().toString() + ".Hide");
					if (!PlayerManager.getPlayers().contains(players)) {
						try {
							if (!isHide) {
								PetUtils.despawnPet(players, SaveUtils.getLocationFromFile(net.virushd.core.main.FileManager.config, "Spawns.Lobby").getWorld());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, 5L, 5L);
	}

	// pet following mechanics
	public static void petFollow() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PetsMain.main, () -> {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (PetUtils.hasPet(p) && PlayerManager.getPlayers().contains(p)) {
					boolean isHide = FileManager.pets.getBoolean(p.getUniqueId().toString() + ".Hide");

					// if pet is not hidden
					if (!isHide) {

						// let the pet follow the player
						try {
							PetUtils.walkToLoc(PetUtils.getPet(p), p.getLocation());
						} catch (Exception ex) {
							PetUtils.spawnPet(p, p.getWorld());
							FileManager.pets.set(p.getUniqueId().toString() + ".Hide", false);
							SaveUtils.saveFile(FileManager.petsF, FileManager.pets);
						}
					}
				}
			}
		}, 0L, 20L);
	}
}
