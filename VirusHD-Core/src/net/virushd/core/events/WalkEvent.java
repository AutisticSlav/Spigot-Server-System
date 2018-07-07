package net.virushd.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.SaveUtils;

@Deprecated
public class WalkEvent implements Listener{

	@EventHandler
	public void onWalk (PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if (CoreMain.players.contains(p)) {
			if (p.getWorld().equals(SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.Lobby").getWorld())) {
				
			} else {
				String ErrorKick = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.ErrorKick"), p);
				p.getInventory().clear();
				p.kickPlayer(ErrorKick);
			}
		}
	}
}
