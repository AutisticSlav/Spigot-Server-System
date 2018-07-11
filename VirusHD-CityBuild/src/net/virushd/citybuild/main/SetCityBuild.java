package net.virushd.citybuild.main;

import net.virushd.core.main.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.core.main.PlaceHolder;
import net.virushd.title.title.Title;

public class SetCityBuild {
	
	public static void setCityBuild (Player p) {
		
		String CityBuildMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.CityBuild"), p);
		String JoinTitle = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String TabTitleHeader = PlaceHolder.WithPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.WithPlayer(FileManager.messages.getString("TabTitle.Footer"), p);
		String JoinMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.Message"), p);
		
		if (!CityBuildMain.players.contains(p)) {
			CityBuildMain.players.add(p);
		}
		
		// debug
		if (CoreMain.debug()) {
			CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " joined CityBuild.");
		}

		Utils.SmoothTeleport(p, SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.CityBuild"));
		p.setGameMode(GameMode.SURVIVAL);
		
		// Title
		Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

		// TabTitle
		Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);
				
		// Scoreboard
		net.virushd.citybuild.scoreboards.CityBuild.SetScoreboard(p);
		
		p.sendMessage(CityBuildMessage);
		
		for (Player players : CityBuildMain.players) {
			if (!players.equals(p)) {
				players.sendMessage(JoinMessage);
			}
		}
	}
}
