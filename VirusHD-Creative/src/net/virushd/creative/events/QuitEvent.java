package net.virushd.creative.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.creative.main.CreativeMain;
import net.virushd.creative.main.FileManager;
import net.virushd.core.main.PlaceHolder;

public class QuitEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit (PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		String QuitMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Quit.Message"), p);
		
		if (CreativeMain.players.contains(p)) {
			
			// debug
			if (CoreMain.debug()) {
				CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " left Creative.");
			}
			
			CreativeMain.players.remove(p);
			for (Player players : CreativeMain.players) {
				players.sendMessage(QuitMessage);
			}
		}
	}
}
