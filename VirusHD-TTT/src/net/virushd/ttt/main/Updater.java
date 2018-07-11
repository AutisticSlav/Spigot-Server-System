package net.virushd.ttt.main;

import java.util.HashMap;

import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.arena.GameState;
import net.virushd.ttt.scoreboards.Ingame;
import net.virushd.ttt.scoreboards.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;

import net.virushd.core.main.PlaceHolder;
import org.bukkit.entity.Player;

public class Updater {

	/*
	 * ScoreboardUpdater
	 */
	public static void ScoreboardUpdater () {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TTTMain.main, () -> {
			for (Arena a : ArenaManager.getCompletedArenas()) {
				for (Player p : a.getPlayers()) {
					if (a.getGameState().equals(GameState.LOBBY)) {
						Lobby.SetScoreboard(p);
					} else {
						Ingame.SetScoreboard(p);
					}
				}
			}
		}, 60L, 60L);
	}

	/*
	 * SignUpdater
	 */
	public static HashMap<Sign, Integer> UpdateSigns = new HashMap<>();
	public static void SignUpdater () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TTTMain.main, () -> {
			for (Sign sign : UpdateSigns.keySet()) {
				for (int i = 0; i < 4; i++) {
					try {
						sign.setLine(i, PlaceHolder.TTTSign(FileManager.config.getString("Sign.Lines." + (i + 1)), UpdateSigns.get(sign)));
						sign.update();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}, 5L, 5L);
	}

	/*
	 * PlayerVisibility
	 */
	public static void PlayerVisibility () {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TTTMain.main, () -> {
			for (Arena arena : ArenaManager.getCompletedArenas()) {
				for (Player players : arena.getPlayers()) {
					for (Player allPlayers : Bukkit.getOnlinePlayers()) {
						if (arena.getPlayers().contains(allPlayers)) {
							if (!players.canSee(allPlayers)) players.showPlayer(allPlayers);
						} else {
							if (players.canSee(allPlayers)) players.hidePlayer(allPlayers);
						}
					}
				}
			}
		}, 5L, 5L);
	}
}
