package net.virushd.multiarena.events;

import net.virushd.core.main.CoreMain;
import net.virushd.multiarena.arena.Arena;
import net.virushd.multiarena.arena.ArenaManager;
import net.virushd.multiarena.main.MultiArenaMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

	@EventHandler
	public void onCommand (PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();

		// debug
		for (Arena a : ArenaManager.getCompletedArenas()) {
			if (a.getPlayers().contains(p) || a.getSpectators().contains(p)) {
				if (CoreMain.debug()) {
					MultiArenaMain.main.getLogger().info("DEBUG: " + p.getName() + " executed the command " + e.getMessage());
				}
			}
		}
	}
}
