package net.virushd.core.main;

import net.virushd.core.api.PlaceHolder;
import net.virushd.core.api.SaveUtils;
import net.virushd.core.api.Utils;
import net.virushd.core.events.ItemClickEvent;
import net.virushd.core.scoreboards.Lobby;
import net.virushd.core.scoreboards.NoScoreboard;
import net.virushd.title.title.Title;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerManager {

	private static ArrayList<Player> players = new ArrayList<>();
	private static HashMap<Player, Mode> mode = new HashMap<>();

	// get a copy of the players
	public static ArrayList<Player> getPlayers() { return new ArrayList<>(players); }

	// join
	public static void join(Player p) {
		if (!players.contains(p)) {

			String TabTitleHeader = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Header"), p);
			String TabTitleFooter = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Footer"), p);

			// teleport
			Utils.smoothTeleport(p, SaveUtils.getLocationFromFile(FileManager.config, "Spawns.Lobby"), GameMode.ADVENTURE);
			p.getInventory().clear();

			// items
			p.getInventory().setItem(1, Utils.getHideItem(p));
			p.getInventory().setItem(4, Utils.getTeleporterItem());
			p.getInventory().setItem(7, Utils.getCosmeticsItem());

			// Tab
			Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

			// Scoreboard
			Lobby.setScoreboard(p);

			// add to players
			players.add(p);
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
			p.getInventory().clear();
		}
	}

	// leave without message
	public static void leaveQuiet(Player p) {
		if (players.contains(p)) {
			players.remove(p);
			if (ItemClickEvent.hideMode.contains(p)) {
				ItemClickEvent.hideMode.remove(p);
				ItemClickEvent.hideOff(p);
			}
			p.getInventory().clear();
		}
	}

	// set to normal mode
	public static void setNormal(Player p) {
		mode.remove(p);
		PlayerManager.join(p);
	}

	// set to admin mode
	public static void setAdmin(Player p) {
		mode.remove(p);
		mode.put(p, Mode.ADMIN);
		leaveQuiet(p);
		p.getInventory().clear();
		p.setGameMode(GameMode.CREATIVE);
		Title.sendTabTitle(p, null, null);
		Title.sendActionBar(p, "");
		NoScoreboard.SetScoreboard(p);
	}

	// set to troll mode
	public static void setTroll(Player p) {
		mode.remove(p);
		mode.put(p, Mode.TROLL);
		leaveQuiet(p);
		p.getInventory().clear();
		p.setGameMode(GameMode.CREATIVE);
		Title.sendTabTitle(p, null, null);
		Title.sendActionBar(p, "");
		NoScoreboard.SetScoreboard(p);
	}

	// check if normal mode
	public static boolean isNormal(Player p) {
		return !mode.containsKey(p);
	}

	// check if admin mode
	public static boolean isAdmin(Player p) {
		if (mode.containsKey(p)) {
			return mode.get(p).equals(Mode.ADMIN);
		}
		return false;
	}

	// check if troll mode
	public static boolean isTroll(Player p) {
		if (mode.containsKey(p)) {
			return mode.get(p).equals(Mode.TROLL);
		}
		return false;
	}
}
