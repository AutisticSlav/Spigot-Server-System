package net.virushd.creative.main;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.title.title.Title;

public class SetCreative {

public static void setCreative (Player p) {
		
		String CreativeMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Creative"), p);
		String JoinTitle = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String TabTitleHeader = PlaceHolder.WithPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.WithPlayer(FileManager.messages.getString("TabTitle.Footer"), p);
		String JoinMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.Message"), p);

		if (!CreativeMain.players.contains(p)) {
			CreativeMain.players.add(p);
		}
		
		// debug
		if (CoreMain.debug()) {
			CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " joined Creative.");
		}
		
		p.teleport(SaveUtils.GetSpawnLocationFromFile(FileManager.config, "Spawns.Creative"));
		p.setGameMode(GameMode.CREATIVE);
		
		// Title
		Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

		// Tab
		Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);
				
		// Scoreboard
		net.virushd.creative.scoreboards.Creative.SetScoreboard(p);
		
		p.sendMessage(CreativeMessage);
		
		for (Player players : CreativeMain.players) {
			if (players.equals(p)) {
				
			} else {
				players.sendMessage(JoinMessage);
			}
		}
	}
}
