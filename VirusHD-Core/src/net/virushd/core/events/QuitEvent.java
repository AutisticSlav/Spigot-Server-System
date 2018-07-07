package net.virushd.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;

public class QuitEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		// debug
		if (CoreMain.debug()) {
			CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " left the game.");
		}
		
		String QuitMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Quit.Message"), p);
		
		if (CoreMain.players.contains(p)) {
			CoreMain.players.remove(p);
			for (Player players : CoreMain.players) {
				players.sendMessage(QuitMessage);
			}
		}
		
		e.setQuitMessage(null);
	}
}
