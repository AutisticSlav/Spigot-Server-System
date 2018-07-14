package net.virushd.ttt.events;

import net.virushd.core.main.CoreMain;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.main.TTTMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

	@EventHandler
	public void onDeath(org.bukkit.event.entity.PlayerDeathEvent e) {

		Player p = e.getEntity();

		for (Arena a : ArenaManager.getCompletedArenas()) {
			if (a.getPlayers().contains(p)) {
				p.spigot().respawn();

				// debug
				if (CoreMain.debug()) {
					TTTMain.main.getLogger().info("DEBUG: " + p.getName() + " died.");
				}

				//a.addSpectator(p);
				// TODO spectator mode and die message
			}
		}
	}
}
