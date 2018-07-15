package net.virushd.ttt.main;

import java.util.HashMap;

import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.arena.GameState;
import net.virushd.ttt.scoreboards.Ingame;
import net.virushd.ttt.scoreboards.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;

import net.virushd.core.api.PlaceHolder;
import org.bukkit.entity.Player;

public class Updater {

	// update all the ttt scoreboards
	public static void scoreboardUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TTTMain.main, () -> {
			for (Arena a : ArenaManager.getCompletedArenas()) {
				for (Player p : a.getPlayers()) {
					if (a.getGameState().equals(GameState.LOBBY)) {
						Lobby.setScoreboard(p);
					} else {
						Ingame.setScoreboard(p);
					}
				}
			}
		}, 60L, 60L);
	}

	public static HashMap<Sign, Integer> updateSigns = new HashMap<>();

	// update the ttt signs
	public static void signUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TTTMain.main, () -> {
			for (Sign sign : updateSigns.keySet()) {
				for (int i = 0; i < 4; i++) {
					try {
						sign.setLine(i, PlaceHolder.sign(FileManager.config.getString("Sign.Lines." + (i)), TTTMain.main, updateSigns.get(sign)));
						sign.update();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}, 5L, 5L);
	}

	// update the player visibility
	public static void playerVisibility() {

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
