package net.virushd.multiarena.events;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.PlaceHolder;
import net.virushd.multiarena.arena.Arena;
import net.virushd.multiarena.arena.ArenaManager;
import net.virushd.multiarena.main.FileManager;
import net.virushd.multiarena.main.MultiArenaMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit (PlayerQuitEvent e) {

		Player p = e.getPlayer();

		String QuitMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Quit.Message"), p);

		for (Arena a : ArenaManager.getCompletedArenas()) {
			if (a.getPlayers().contains(p)) {

				// debug
				if (CoreMain.debug()) {
					MultiArenaMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
				}

				a.getPlayers().remove(p);
				for (Player players : a.getPlayers()) {
					players.sendMessage(QuitMessage);
				}
			}
		}
	}
}
