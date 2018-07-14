package net.virushd.ttt.events;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.PlaceHolder;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.main.FileManager;
import net.virushd.ttt.main.TTTMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		Player p = e.getPlayer();

		String QuitMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Quit.Message"), p);

		for (Arena a : ArenaManager.getCompletedArenas()) {
			if (a.getPlayers().contains(p)) {

				// debug
				if (CoreMain.debug()) {
					TTTMain.main.getLogger().info("DEBUG: " + p.getName() + " left TTT.");
				}

				// remove the player
				a.getPlayers().remove(p);
				for (Player players : a.getPlayers()) {
					players.sendMessage(QuitMessage);
				}
			}
		}
	}
}
