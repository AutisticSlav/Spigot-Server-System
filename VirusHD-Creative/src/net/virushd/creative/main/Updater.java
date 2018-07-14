package net.virushd.creative.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import net.virushd.creative.scoreboards.Creative;
import net.virushd.core.main.PlaceHolder;

public class Updater {

	// update all the creative scoreboards
	public static void scoreboardUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CreativeMain.main, () -> {
			for (Player players : CreativeMain.getPlayers()) {
				Creative.setScoreboard(players);
			}
		}, 60L, 60L);
	}

	public static ArrayList<Sign> updateSigns = new ArrayList<>();

	// update all the creative signs
	public static void signUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CreativeMain.main, () -> {
			for (Sign signs : updateSigns) {
				for (int i = 0; i < 4; i++) {
					signs.setLine(i, PlaceHolder.creativeSign(FileManager.config.getString("Sign.Lines." + (i))));
					signs.update();
				}
			}
		}, 5L, 5L);
	}

	// update the visibility of the players
	public static void playerVisibility() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CreativeMain.main, () -> {
			for (Player players : CreativeMain.getPlayers()) {
				for (Player AllPlayers : Bukkit.getOnlinePlayers()) {
					if (CreativeMain.getPlayers().contains(AllPlayers)) {
						if (!players.canSee(AllPlayers)) players.showPlayer(AllPlayers);
					} else {
						if (players.canSee(AllPlayers)) players.hidePlayer(AllPlayers);
					}
				}
			}
		}, 5L, 5L);
	}
}
