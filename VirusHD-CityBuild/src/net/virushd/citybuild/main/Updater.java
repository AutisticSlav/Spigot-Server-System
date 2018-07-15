package net.virushd.citybuild.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import net.virushd.citybuild.scoreboards.CityBuild;
import net.virushd.core.api.PlaceHolder;

public class Updater {

	// update all citybuild scoreboards
	public static void scoreboardUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CityBuildMain.main, () -> {
			for (Player players : PlayerManager.getPlayers()) {
				CityBuild.setScoreboard(players);
			}
		}, 60L, 60L);
	}

	public static ArrayList<Sign> updateSigns = new ArrayList<>();

	// update all citybuild signs
	public static void signUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CityBuildMain.main, () -> {
			for (Sign signs : updateSigns) {
				for (int i = 0; i < 4; i++) {
					signs.setLine(i, PlaceHolder.sign(FileManager.config.getString("Sign.Lines." + (i)), CityBuildMain.main));
					signs.update();
				}
			}
		}, 5L, 5L);
	}

	// update the visibility of the players
	public static void playerVisibility() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CityBuildMain.main, () -> {
			for (Player players : PlayerManager.getPlayers()) {
				for (Player AllPlayers : Bukkit.getOnlinePlayers()) {
					if (PlayerManager.getPlayers().contains(AllPlayers)) {
						if (!players.canSee(AllPlayers)) players.showPlayer(AllPlayers);
					} else {
						if (players.canSee(AllPlayers)) players.hidePlayer(AllPlayers);
					}
				}
			}
		}, 5L, 5L);
	}
}
