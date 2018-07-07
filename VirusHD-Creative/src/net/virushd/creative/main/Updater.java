package net.virushd.creative.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import net.virushd.creative.scoreboards.Creative;
import net.virushd.core.main.PlaceHolder;

public class Updater {
	
	/*
	 * ScoreboardUpdater
	 */
	public static void ScoreboardUpdater () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CreativeMain.main, new Runnable() {
			
			@Override
			public void run() {
				for (Player players : CreativeMain.players) {
					Creative.SetScoreboard(players);
				}
			}
		}, 60L, 60L);
	}
	
	/*
	 * SignUpdater
	 */
	public static ArrayList<Sign> UpdateSigns = new ArrayList<>();
	public static void SignUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CreativeMain.main, new Runnable() {

			@Override
			public void run() {
				for (Sign signs : UpdateSigns) {
					for (int i = 0; i < 4; i++) {
						signs.setLine(i, PlaceHolder.CreativeSign(FileManager.config.getString("Sign.Lines." + (i + 1))));
						signs.update();
					}
				}
			}
		}, 5L, 5L);
	}
	
	/*
	 * PlayerVisibility
	 */
	public static void PlayerVisibility () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CreativeMain.main, new Runnable() {
			@Override
			public void run() {
				for (Player players : CreativeMain.players) {
					for (Player AllPlayers : Bukkit.getOnlinePlayers()) {
						if (CreativeMain.players.contains(AllPlayers)) {
							if (!players.canSee(AllPlayers)) players.showPlayer(AllPlayers);
						} else {
							if (players.canSee(AllPlayers)) players.hidePlayer(AllPlayers);
						}
					}
				}
			}
		}, 5L, 5L);
	}
}
