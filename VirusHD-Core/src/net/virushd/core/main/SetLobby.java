package net.virushd.core.main;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import net.virushd.core.scoreboards.Lobby;
import net.virushd.title.title.Title;

public class SetLobby {

	public static void setLobby(Player p) {

		String TabTitleHeader = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Footer"), p);

		// teleport
		Utils.smoothTeleport(p, SaveUtils.getLocationFromFile(FileManager.config, "Spawns.Lobby"));
		p.getInventory().clear();
		p.setGameMode(GameMode.ADVENTURE);

		// items
		p.getInventory().setItem(1, Utils.getHideItem(p));
		p.getInventory().setItem(4, Utils.getTeleporterItem());
		p.getInventory().setItem(7, Utils.getCosmeticsItem());

		// Tab
		Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

		// Scoreboard
		Lobby.setScoreboard(p);

		// add to players
		if (!CoreMain.getPlayers().contains(p)) {
			CoreMain.getPlayers().add(p);
		}
	}
}
