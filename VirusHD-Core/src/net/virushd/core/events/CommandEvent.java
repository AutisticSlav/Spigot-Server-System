package net.virushd.core.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;

public class CommandEvent implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();

		String cmd = e.getMessage().split(" ")[0];

		// custom unknown command message
		if (Bukkit.getHelpMap().getHelpTopic(cmd) == null) {
			p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.UnknownCommand"), p).replace("{Command}", cmd));
			e.setCancelled(true);
		}

		// debug
		if (CoreMain.getPlayers().contains(p)) {
			if (CoreMain.debug()) {
				CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " executed the command " + e.getMessage());
			}
		}
	}
}
