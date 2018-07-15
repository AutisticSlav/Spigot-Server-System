package net.virushd.pets.main;

import net.virushd.core.main.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
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
						Location loc = PetUtils.getPet(players).getLocation().add(0, 1, 0);
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
}
