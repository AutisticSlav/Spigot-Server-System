package net.virushd.creative.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.virushd.core.main.CoreMain;
import net.virushd.creative.main.CreativeMain;
import net.virushd.creative.main.FileManager;
import net.virushd.core.main.PlaceHolder;

public class ChatEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat (AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();

		if (CreativeMain.players.contains(p)) {
			
			// debug
			if (CoreMain.debug()) {
				CreativeMain.main.getLogger().info("DEBUG: (Chat) " + p.getName() + ": " + e.getMessage());
			}
			
			String ChatFormat = PlaceHolder.WithPlayer(FileManager.messages.getString("ChatFormat"), p);

			for (Player players : CreativeMain.players) {
				players.sendMessage(ChatFormat.replace("{Message}", e.getMessage()));
			}
		}
	}
}
