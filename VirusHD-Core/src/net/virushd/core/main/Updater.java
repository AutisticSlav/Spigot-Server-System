package net.virushd.core.main;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.virushd.core.events.ItemClickEvent;
import net.virushd.core.scoreboards.Lobby;
import net.virushd.title.title.Title;

public class Updater {
	
	public static void ScoreboardUpdater () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, new Runnable() {
			
			@Override
			public void run() {
				for (Player players : CoreMain.players) {
					Lobby.SetScoreboard(players);
				}
			}
		}, 60L, 60L);
	}
	
	public static void RandomMessages () {
		
		List<String> ActionBarMessages = FileManager.messages.getStringList("Messages.ActionBar");
		int Laenge = 10;
		
		// schlaufe mit wechsel der Message
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, new Runnable() {
			
			public int n = 0;
			
			@Override
			public void run() {
				
				if (n == ActionBarMessages.size() - 1) {
					n = 0;
				} else {
					n++;
				}
				
				//schlaufe die es wiederholt sendet
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, new Runnable() {
					
					@Override
					public void run() {
						
						for (Player players : CoreMain.players) {
							Title.sendActionBar(players, PlaceHolder.WithPlayer(ActionBarMessages.get(n), players));
						}
						
					}
				}, 0L, (long) 1L);
				
			}
		}, 0L, (long) 20*Laenge);
	}
	
	public static void PlayerVisibility () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, new Runnable() {
			@Override
			public void run() {
				for (Player players : CoreMain.players) {
					for (Player AllPlayers : Bukkit.getOnlinePlayers()) {
						if (CoreMain.players.contains(AllPlayers)) {
							if (!ItemClickEvent.HideMode.contains(players)){
								if (!players.canSee(AllPlayers)) players.showPlayer(AllPlayers);
							}
						} else {
							if (players.canSee(AllPlayers)) players.hidePlayer(AllPlayers);
						}
					}
				}
			}
		}, 5L, 5L);
	}
	
	public static void AntiRespawnBug() {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CoreMain.main, new Runnable() {
			
			@Override
			public void run() {
				for (Player players : CoreMain.players) {
					if (!(players.getWorld().equals(SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.Lobby").getWorld()))) {
						players.getInventory().clear();
						SetLobby.setLobby(players);
					}
				}
			}
		}, 5L, 5L);
	}
}
