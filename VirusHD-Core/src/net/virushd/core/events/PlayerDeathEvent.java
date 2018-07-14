package net.virushd.core.events;

import net.virushd.core.main.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.virushd.core.main.CoreMain;

public class PlayerDeathEvent implements Listener {

	@EventHandler
	public void onDeath(org.bukkit.event.entity.PlayerDeathEvent e) {

		// diable the default message
		e.setDeathMessage(null);

		Player p = e.getEntity();

		if (PlayerManager.getPlayers().contains(p)) {

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
		PlayerManager.join(e.getPlayer());
	}
}
