package net.virushd.multiarena.events;

import net.virushd.core.main.CoreMain;
import net.virushd.multiarena.arena.Arena;
import net.virushd.multiarena.arena.ArenaManager;
import net.virushd.multiarena.main.MultiArenaMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

	@EventHandler
	public void onDeath(org.bukkit.event.entity.PlayerDeathEvent  e) {

		Player p = e.getEntity();

		// debug
		for (Arena a : ArenaManager.getCompletedArenas()) {
			if (a.getPlayers().contains(p)) {
				p.spigot().respawn();
				if (CoreMain.debug()) {
					MultiArenaMain.main.getLogger().info("DEBUG: " + p.getName() + " died.");
				}

				//a.addSpectator(p);
				// TODO spectator mode and die message
			}
		}
	}
}
