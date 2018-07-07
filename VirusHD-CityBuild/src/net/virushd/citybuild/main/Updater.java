package net.virushd.citybuild.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import net.virushd.citybuild.scoreboards.CityBuild;
import net.virushd.core.main.PlaceHolder;

public class Updater {
	
	/*
	 * ScoreboardUpdater
	 */
	public static void ScoreboardUpdater () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CityBuildMain.main, new Runnable() {
			
			@Override
			public void run() {
				for (Player players : CityBuildMain.players) {
					CityBuild.SetScoreboard(players);
				}
			}
		}, 60L, 60L);
	}
	
	/*
	 * SignUpdater
	 */
	public static ArrayList<Sign> UpdateSigns = new ArrayList<>();
	public static void SignUpdater () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CityBuildMain.main, new Runnable() {
			
			@Override
			public void run() {
				for (Sign signs:UpdateSigns) {
					for (int i = 0; i < 4; i++) {
						signs.setLine(i, PlaceHolder.CityBuildSign(FileManager.config.getString("Sign.Lines." + (i + 1))));
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
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CityBuildMain.main, new Runnable() {
			
			@Override
			public void run() {
				for (Player players : CityBuildMain.players) {
					for (Player AllPlayers : Bukkit.getOnlinePlayers()) {
						if (CityBuildMain.players.contains(AllPlayers)) {
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
