package net.virushd.core.api;

import java.util.List;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import CoinsAPI.Coins;

public class PlaceHolder {

	// normal placeholder replacement
	public static String normal(String s) {
		s = s.replace("{Prefix}", FileManager.messages.getString("Prefix"));
		for (Minigame minigame : CoreMain.getMinigames()) {
			s = minigame.normalPlaceholder(s);
		}
		if (CoreMain.pluginAvailable("VirusHD-Coins")) s = coins(s);
		if (CoreMain.pluginAvailable("VirusHD-Pets")) s = pets(s);
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}

	// the same but with a list
	public static List<String> normal(List<String> s) {
		for (int i = 0; i < s.size(); i++) {
			s.set(i, normal(s.get(i)));
		}
		return s;
	}

	// with some extra stuff that can only work with a given player
	public static String withPlayer(String s, Player p) {
		s = normal(s);
		s = s.replace("{Coins}", "" + Coins.get(p.getUniqueId()));
		s = s.replace("{World}", p.getWorld().getName());
		s = s.replace("{PlayerName}", p.getName());
		s = s.replace("{DisplayName}", p.getDisplayName());
		s = s.replace("{Rank}", Utils.getRank(p));
		s = s.replace("{OnlinePlayers}", "" + Bukkit.getOnlinePlayers().size());
		s = s.replace("{MaxOnlinePlayers}", "" + Bukkit.getMaxPlayers());
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}

	// the same but with a list
	public static List<String> withPlayer(List<String> s, Player p) {
		for (int i = 0; i < s.size(); i++) {
			s.set(i, withPlayer(s.get(i), p));
		}
		return s;
	}

	// placeholder for signs (normal)
	public static String sign(String s, Minigame.NormalMinigame minigame) {
		s = minigame.signPlaceholder(s);
		return s;
	}

	// placeholder for signs (arena)
	public static String sign(String s, Minigame.ArenaMinigame minigame, int id) {
		s = minigame.signPlaceholder(s, id);
		return s;
	}

	// normal coins placeholders
	private static String coins(String s) {
		s = s.replace("{CoinsPrefix}", net.virushd.coins.main.FileManager.messages.getString("CoinsPrefix"));
		s = s.replace("{LotteryPrefix}", net.virushd.coins.main.FileManager.messages.getString("LotteryPrefix"));
		s = s.replace("{NormalPrice}", "" + net.virushd.coins.main.FileManager.inv_lottery.getInt("NormalPrice"));
		s = s.replace("{HighChancePrice}", "" + net.virushd.coins.main.FileManager.inv_lottery.getInt("HighChancePrice"));
		s = s.replace("{WaitTime}", "" + net.virushd.coins.main.FileManager.inv_lottery.getInt("WaitTime"));
		return s;
	}

	// normal pets placeholders
	private static String pets(String s) {
		s = s.replace("{PetsPrefix}", net.virushd.pets.main.FileManager.messages.getString("PetsPrefix"));
		s = s.replace("{PetPrice}", net.virushd.pets.main.FileManager.messages.getString("PetPrice"));
		return s;
	}

	/* //normal ttt placeholders
	private static String ttt(String s) {
		s = s.replace("{TTTPrefix}", net.virushd.ttt.main.FileManager.messages.getString("TTTPrefix"));
		return s;
	}

	// ttt sign placeholders
	public static String tttSign(String s, int id) {
		if (CoreMain.pluginAvailable("VirusHD-TTT")) {
			Arena arena = ArenaManager.getArenaByID(id);
			if (arena != null && arena.isComplete()) {
				int MaxPlayers = net.virushd.ttt.main.FileManager.config.getInt("MaxPlayers");
				String Lobby = net.virushd.ttt.main.FileManager.config.getString("GameStates.Lobby");
				String LobbyFull = net.virushd.ttt.main.FileManager.config.getString("GameStates.LobbyFull");
				String Ingame = net.virushd.ttt.main.FileManager.config.getString("GameStates.Ingame");
				s = s.replace("{Name}", arena.getName());
				if (arena.getGameState() == GameState.LOBBY && arena.getPlayers().size() < MaxPlayers)
					s = s.replace("{GameState}", Lobby);
				if (arena.getGameState() == GameState.LOBBY && arena.getPlayers().size() == MaxPlayers)
					s = s.replace("{GameState}", LobbyFull);
				if (arena.getGameState() == GameState.STARTING || arena.getGameState() == GameState.GAME || arena.getGameState() == GameState.RESTARTING)
					s = s.replace("{GameState}", Ingame);
				s = s.replace("{MaxPlayers}", "" + MaxPlayers);
				s = s.replace("{Players}", "" + arena.getPlayers().size());
				s = normal(s);
			}
		}
		return s;
	}*/

	// switch the color codes
	public static String betterColorCode(String s) {
		return s.replaceAll("§", "&");
	}

	public static List<String> betterColorCode(List<String> s) {
		for (int i = 0; i < s.size(); i++) {
			s.set(i, betterColorCode(s.get(i)));
		}
		return s;
	}
}
