package net.virushd.creative.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.creative.main.CreativeMain;
import net.virushd.creative.main.FileManager;
import net.virushd.creative.main.PlaceHolder;

@Deprecated
public class WorldChangeEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWorldChange (PlayerChangedWorldEvent e) {
		
		Player p = e.getPlayer();
		String QuitMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Quit.Message"), p);
		
		//from Creative
		if (CreativeMain.players.contains(p)) {
			if (p.getWorld().equals(SaveUtils.GetLocationFromFile(net.virushd.core.main.FileManager.config, "Spawns.Lobby").getWorld())) {
				
				// debug
				if (CoreMain.debug()) {
					CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " left Creative.");
				}
				
				CreativeMain.players.remove(p);
				for (Player players : CreativeMain.players) {
					players.sendMessage(QuitMessage);
				}
			}
		}
	}
}
