package net.virushd.core.main;

import java.util.List;

import net.virushd.core.api.ConfigFile;
import net.virushd.core.api.PlaceHolder;
import net.virushd.core.api.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.virushd.core.events.ItemClickEvent;
import net.virushd.core.scoreboards.Lobby;
import net.virushd.title.title.Title;

@SuppressWarnings("ConstantConditions")
public class Updater {

	// update the scoreboards
	public static void scoreboardUpdater() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, () -> {
			for (Player players : PlayerManager.getPlayers()) {
				Lobby.setScoreboard(players);
			}
		}, 60L, 60L);
	}

	// random action bar messages
	public static void randomMessages() {
		List<String> ActionBarMessages = FileManager.getFile("messages",ConfigFile.FileType.NORMAL).getConfig().getStringList("Messages.ActionBar");
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

					for (Player players : PlayerManager.getPlayers()) {
						Title.sendActionBar(players, PlaceHolder.withPlayer(ActionBarMessages.get(n), players));
					}

				}, 0L, 1L);

			}
		}, 0L, (long) 20 * Laenge);
	}

	// update the player visibility
	public static void playerVisibility() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, () -> {
			for (Player players : PlayerManager.getPlayers()) {
				for (Player AllPlayers : Bukkit.getOnlinePlayers()) {
					if (PlayerManager.getPlayers().contains(AllPlayers)) {
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
			for (Player players : PlayerManager.getPlayers()) {
				if (!(players.getWorld().equals(Serializer.deserializeLocation(Serializer.getMap(FileManager.getFile("config", ConfigFile.FileType.NORMAL), "Spawns.Lobby")).getWorld()))) {
					players.getInventory().clear();
					PlayerManager.join(players);
				}
			}
		}, 5L, 5L);
	}
}
