package net.virushd.citybuild.events;

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

		String QuitMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Quit.Message"), p);

		if (CityBuildMain.getPlayers().contains(p)) {

			// debug
			if (CoreMain.debug()) {
				CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
			}

			// send all players in citybuild the quit message and remove the player
			CityBuildMain.getPlayers().remove(p);
			for (Player players : CityBuildMain.getPlayers()) {
				players.sendMessage(QuitMessage);
			}
		}
	}
}
