package net.virushd.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;

public class ChatEvent implements Listener{

	@EventHandler()
	public void onChat(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		
		if (CoreMain.players.contains(p)) {
			
			// debug
			if (CoreMain.debug()) {
				CoreMain.main.getLogger().info("DEBUG: (Chat) " + p.getName() + ": " + e.getMessage());
			}
			
			String ChatFormat = PlaceHolder.WithPlayer(FileManager.messages.getString("ChatFormat"), p);

			for (Player players : CoreMain.players) {
				players.sendMessage(ChatFormat.replace("{Message}", e.getMessage()));
			}
		}
		
		e.setCancelled(true);
	}
}
