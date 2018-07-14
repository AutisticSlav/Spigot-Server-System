package net.virushd.core.main;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.virushd.core.events.ItemClickEvent;
import net.virushd.core.scoreboards.Lobby;
import net.virushd.title.title.Title;

public class Updater {

	// update the scoreboards
	public static void scoreboardUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, () -> {
			for (Player players : CoreMain.getPlayers()) {
				Lobby.setScoreboard(players);
			}
		}, 60L, 60L);
	}

	// random action bar messages
	public static void randomMessages() {

		List<String> ActionBarMessages = FileManager.messages.getStringList("Messages.ActionBar");
		int Laenge = 10;

		// loop that changes the message
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, new Runnable() {

			public int n = 0;

			@Override
			public void run() {

				if (n == ActionBarMessages.size() - 1) {
					n = 0;
				} else {
					n++;
				}

				// loop that sends the message
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, () -> {

					for (Player players : CoreMain.getPlayers()) {
						Title.sendActionBar(players, PlaceHolder.withPlayer(ActionBarMessages.get(n), players));
					}

				}, 0L, 1L);

			}
		}, 0L, (long) 20 * Laenge);
	}

	// update the player visibility
	public static void playerVisibility() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, () -> {
			for (Player players : CoreMain.getPlayers()) {
				for (Player AllPlayers : Bukkit.getOnlinePlayers()) {
					if (CoreMain.getPlayers().contains(AllPlayers)) {
						if (!ItemClickEvent.hideMode.contains(players)) {
							if (!players.canSee(AllPlayers)) players.showPlayer(AllPlayers);
						}
					} else {
						if (players.canSee(AllPlayers)) players.hidePlayer(AllPlayers);
					}
				}
			}
		}, 5L, 5L);
	}

	// fix a respawn bug
	public static void antiRespawnBug() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, () -> {
			for (Player players : CoreMain.getPlayers()) {
				if (!(players.getWorld().equals(SaveUtils.getLocationFromFile(FileManager.config, "Spawns.Lobby").getWorld()))) {
					players.getInventory().clear();
					SetLobby.setLobby(players);
				}
			}
		}, 5L, 5L);
	}
}
