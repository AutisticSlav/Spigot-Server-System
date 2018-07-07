package net.virushd.pets.main;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.pets.pet.PetUtils;

public class Updater {
	
	public static void PetHearts () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PetsMain.main, new Runnable() {

			@Override
			public void run() {
				for (Player players : CoreMain.players) {
					if (PetUtils.hasPet(players)) {
						try {
							Location loc = PetUtils.getPet(players).getLocation().add(0, 1, 0);
							players.playEffect(loc, Effect.HEART, null);
						} catch (Exception e) {
							
						}
					}
				}
			}
		}, 5L, 5L);
	}
	
	public static void PetVisibility () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PetsMain.main, new Runnable() {

			@Override
			public void run() {
				for (Player players : Bukkit.getOnlinePlayers()) {
					if (PetUtils.hasPet(players)) {
						boolean isHide = FileManager.pets.getBoolean(players.getUniqueId().toString() + ".Hide");
						if (!CoreMain.players.contains(players)) {
							try {
								if (!isHide) {
									PetUtils.despawnPet(players, SaveUtils.GetLocationFromFile(net.virushd.core.main.FileManager.config, "Spawns.Lobby").getWorld());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}, 5L, 5L);
	}
}
