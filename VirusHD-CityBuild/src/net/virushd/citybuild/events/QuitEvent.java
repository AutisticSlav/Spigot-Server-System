package net.virushd.citybuild.events;

import net.virushd.citybuild.main.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.citybuild.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.CoreMain;

public class QuitEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		Player p = e.getPlayer();

		if (PlayerManager.getPlayers().contains(p)) {
			PlayerManager.leave(p);
		}
	}
}
