package net.virushd.ttt.events;

import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.arena.GameState;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMoveEvent implements Listener {

	@EventHandler
	public void onMove(org.bukkit.event.player.PlayerMoveEvent e) {

		Player p = e.getPlayer();
		Location from = e.getFrom();
		Location to = e.getTo();

		// cancel player movement smoothly
		for (Arena a : ArenaManager.getCompletedArenas()) {
			if (a.getPlayers().contains(p) && a.getGameState().equals(GameState.STARTING)) {
				if (from.getX() != to.getX() || from.getZ() != to.getZ()) {
					Location l = new Location(
							to.getWorld(),
							from.getX(),
							to.getY(),
							from.getZ(),
							to.getYaw(),
							to.getPitch()
					);
					p.teleport(l);
				}
			}
		}
	}
}
