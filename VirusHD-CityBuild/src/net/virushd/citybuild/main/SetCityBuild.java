package net.virushd.citybuild.main;

import net.virushd.core.main.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.core.main.PlaceHolder;
import net.virushd.title.title.Title;

public class SetCityBuild {

	public static void setCityBuild(Player p) {

		String CityBuildMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CityBuild"), p);
		String JoinTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String TabTitleHeader = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Footer"), p);
		String JoinMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Message"), p);

		if (!CityBuildMain.getPlayers().contains(p)) {
			CityBuildMain.getPlayers().add(p);
		}

		// debug
		if (CoreMain.debug()) {
			CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " joined CityBuild.");
		}

		Utils.smoothTeleport(p, SaveUtils.getLocationFromFile(FileManager.config, "Spawns.CityBuild"));
		p.setGameMode(GameMode.SURVIVAL);

		// Title
		Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

		// TabTitle
		Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

		// Scoreboard
		net.virushd.citybuild.scoreboards.CityBuild.setScoreboard(p);

		p.sendMessage(CityBuildMessage);

		for (Player players : CityBuildMain.getPlayers()) {
			if (!players.equals(p)) {
				players.sendMessage(JoinMessage);
			}
		}
	}
}
