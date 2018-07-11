package net.virushd.ttt.events;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.main.TTTMain;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

	public void onChat (AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();

		for (Arena a : ArenaManager.getCompletedArenas()) {
			if (a.getPlayers().contains(p)) {

				// debug
				if (CoreMain.debug()) {
					TTTMain.main.getLogger().info("DEBUG: (Chat) (Arena " + a.getID() + ") " + p.getName() + ": " + e.getMessage());
				}

				String ChatFormat = PlaceHolder.WithPlayer(FileManager.messages.getString("ChatFormat"), p);

				for (Player players : a.getPlayers()) {
					players.sendMessage(ChatFormat.replace("{Message}", e.getMessage()));
				}
			}
		}
	}
}
