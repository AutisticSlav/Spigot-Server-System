package net.virushd.citybuild.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.citybuild.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.CoreMain;

public class ChatEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat (AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();

		if (CityBuildMain.players.contains(p)) {
			
			// debug
			if (CoreMain.debug()) {
				CityBuildMain.main.getLogger().info("DEBUG: (Chat) " + p.getName() + ": " + e.getMessage());
			}
			
			String ChatFormat = PlaceHolder.WithPlayer(FileManager.messages.getString("ChatFormat"), p);

			for (Player players : CityBuildMain.players) {
				players.sendMessage(ChatFormat.replace("{Message}", e.getMessage()));
			}
		}
	}
}
