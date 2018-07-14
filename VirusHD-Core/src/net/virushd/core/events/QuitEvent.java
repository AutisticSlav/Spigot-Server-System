package net.virushd.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;

public class QuitEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		// debug
		if (CoreMain.debug()) {
			CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " left the game.");
		}
		
		String QuitMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Quit.Message"), p);

		// send the quit message
		if (CoreMain.getPlayers().contains(p)) {
			CoreMain.getPlayers().remove(p);
			for (Player players : CoreMain.getPlayers()) {
				players.sendMessage(QuitMessage);
			}
		}

		// diable the default message
		e.setQuitMessage(null);
	}
}
