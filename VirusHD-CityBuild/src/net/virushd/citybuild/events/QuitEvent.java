package net.virushd.citybuild.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.citybuild.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.CoreMain;

public class QuitEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit (PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		String QuitMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Quit.Message"), p);
		
		if (CityBuildMain.players.contains(p)) {
			
			// debug
			if (CoreMain.debug()) {
				CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
			}
			
			CityBuildMain.players.remove(p);
			for (Player players : CityBuildMain.players) {
				players.sendMessage(QuitMessage);
			}
		}
	}
}
