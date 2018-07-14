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

	@EventHandler
	public void onQuit (PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		String QuitMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Quit.Message"), p);
		
		if (CreativeMain.getPlayers().contains(p)) {
			
			// debug
			if (CoreMain.debug()) {
				CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " left Creative.");
			}

			// send the quit message to the creative players and remove the player
			CreativeMain.getPlayers().remove(p);
			for (Player players : CreativeMain.getPlayers()) {
				players.sendMessage(QuitMessage);
			}
		}
	}
}
