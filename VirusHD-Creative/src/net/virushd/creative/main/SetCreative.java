package net.virushd.creative.main;

import net.virushd.core.main.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.title.title.Title;

public class SetCreative {

	public static void setCreative(Player p) {

		String CreativeMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Creative"), p);
		String JoinTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String TabTitleHeader = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Footer"), p);
		String JoinMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Message"), p);

		if (!CreativeMain.getPlayers().contains(p)) {
			CreativeMain.getPlayers().add(p);
		}

		// debug
		if (CoreMain.debug()) {
			CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " joined Creative.");
		}

		Utils.smoothTeleport(p, SaveUtils.getLocationFromFile(FileManager.config, "Spawns.Creative"));
		p.setGameMode(GameMode.CREATIVE);

		// Title
		Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

		// Tab
		Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

		// Scoreboard
		net.virushd.creative.scoreboards.Creative.setScoreboard(p);

		p.sendMessage(CreativeMessage);

		for (Player players : CreativeMain.getPlayers()) {
			if (!players.equals(p)) {
				players.sendMessage(JoinMessage);
			}
		}
	}
}
