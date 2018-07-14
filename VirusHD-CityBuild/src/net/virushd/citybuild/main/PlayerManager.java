package net.virushd.citybuild.main;

import net.virushd.citybuild.scoreboards.CityBuild;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.SaveUtils;
import net.virushd.core.main.Utils;
import net.virushd.title.title.Title;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerManager {

	private static ArrayList<Player> players = new ArrayList<>();

	// get a copy of the players
	public static ArrayList<Player> getPlayers() { return new ArrayList<>(players); }

	// join
	public static void join(Player p) {

		if (!players.contains(p)) {

			String CityBuildMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CityBuild"), p);
			String JoinTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Title"), p);
			String JoinSubTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.SubTitle"), p);
			String TabTitleHeader = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Header"), p);
			String TabTitleFooter = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Footer"), p);
			String JoinMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Message"), p);

			// add the player
			players.add(p);

			// remove the player from the lobby
			net.virushd.core.main.PlayerManager.leaveQuiet(p);

			// teleport
			Utils.smoothTeleport(p, SaveUtils.getLocationFromFile(FileManager.config, "Spawns.CityBuild"), GameMode.SURVIVAL);

			// Title
			Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

			// TabTitle
			Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

			// Scoreboard
			CityBuild.setScoreboard(p);

			// send the join message
			p.sendMessage(CityBuildMessage);
			for (Player player : players) {
				if (!player.equals(p)) {
					player.sendMessage(JoinMessage);
				}
			}

			// debug
			if (CoreMain.debug()) {
				CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " joined CityBuild.");
			}
		}
	}

	// leave
	public static void leave(Player p) {

		if (players.contains(p)) {

			String QuitMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Quit.Message"), p);

			// send the quit message & remove the player
			players.remove(p);
			for (Player player : players) {
				player.sendMessage(QuitMessage);
			}

			// debug
			if (CoreMain.debug()) {
				CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
			}
		}
	}
}
