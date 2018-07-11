package net.virushd.core.main;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import net.virushd.core.scoreboards.Lobby;
import net.virushd.title.title.Title;

public class SetLobby {

	public static void setLobby(Player p) {

		String TabTitleHeader = PlaceHolder.WithPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.WithPlayer(FileManager.messages.getString("TabTitle.Footer"), p);

		Utils.SmoothTeleport(p, SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.Lobby"));
		p.getInventory().clear();
		p.setGameMode(GameMode.ADVENTURE);

		// items
		p.getInventory().setItem(1, Utils.HideItem(p));
		p.getInventory().setItem(4, Utils.TeleporterItem());
		p.getInventory().setItem(7, Utils.CosmeticsItem());

		// Tab
		Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

		// Scoreboard
		Lobby.SetScoreboard(p);

		if (!CoreMain.players.contains(p)) {
			CoreMain.players.add(p);
		}
	}
}
