package net.virushd.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SetLobby;

public class PlayerDeathEvent implements Listener {

	@EventHandler
	public void onDeath(org.bukkit.event.entity.PlayerDeathEvent e) {

		// diable the default message
		e.setDeathMessage(null);

		Player p = e.getEntity();

		if (CoreMain.getPlayers().contains(p)) {

			// clear the drops
			e.getDrops().clear();

			// debug
			if (CoreMain.debug()) {
				CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " died.");
			}
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {

		Player p = e.getPlayer();

		// set to lobby
		if (CoreMain.getPlayers().contains(p)) {
			SetLobby.setLobby(p);
		}
	}
}
