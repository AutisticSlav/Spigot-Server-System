package net.virushd.citybuild.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.citybuild.main.FileManager;
import net.virushd.citybuild.main.PlaceHolder;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;

@Deprecated
public class WorldChangeEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWorldChange (PlayerChangedWorldEvent e) {
		
		Player p = e.getPlayer();
		String QuitMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Quit.Message"), p);
		
		//from CityBuild
		if (CityBuildMain.players.contains(p)) {
			if (p.getWorld().equals(SaveUtils.GetLocationFromFile(net.virushd.core.main.FileManager.config, "Spawns.Lobby").getWorld())) {
				
				// debug
				if (CoreMain.debug()) {
					CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
				}
				
				CityBuildMain.players.remove(p);
				for (Player players : CityBuildMain.players) {
					players.sendMessage(QuitMessage);
				}
			}
		}
	}
}
