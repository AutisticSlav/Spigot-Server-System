package net.virushd.core.main;

import java.util.List;
import java.util.Random;

import net.virushd.creative.main.PlayerManager;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.arena.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import CoinsAPI.Coins;
import net.virushd.creative.main.CreativeMain;

public class PlaceHolder {

	// normal placeholder replacement
	public static String normal(String s) {
		s = s.replace("{Prefix}", FileManager.messages.getString("Prefix"));
		if (CoreMain.pluginAvailable("VirusHD-CityBuild")) s = citybuild(s);
		if (CoreMain.pluginAvailable("VirusHD-Creative")) s = creative(s);
		if (CoreMain.pluginAvailable("VirusHD-Coins")) s = coins(s);
		if (CoreMain.pluginAvailable("VirusHD-Pets")) s = pets(s);
		if (CoreMain.pluginAvailable("VirusHD-TTT")) s = ttt(s);
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

	// normal citybuild placeholders
	private static String citybuild(String s) {
		s = s.replace("{CityBuildPrefix}", net.virushd.citybuild.main.FileManager.messages.getString("CityBuildPrefix"));
		return s;
	}

	// citybuild sign placeholders
	public static String citybuildSign(String s) {
		if (CoreMain.pluginAvailable("VirusHD-CityBuild")) {
			int MaxPlayers = net.virushd.citybuild.main.FileManager.config.getInt("MaxPlayers");
			String Lobby = net.virushd.citybuild.main.FileManager.config.getString("GameStates.Lobby");
			String LobbyFull = net.virushd.citybuild.main.FileManager.config.getString("GameStates.LobbyFull");
			s = s.replace("{Name}", "CityBuild");
			if (net.virushd.citybuild.main.PlayerManager.getPlayers().size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
			if (net.virushd.citybuild.main.PlayerManager.getPlayers().size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
			s = s.replace("{MaxPlayers}", "" + MaxPlayers);
			s = s.replace("{Players}", "" + net.virushd.citybuild.main.PlayerManager.getPlayers().size());
			s = normal(s);
		}
		return s;
	}

	// normal cretive placeholders
	private static String creative(String s) {
		s = s.replace("{CreativePrefix}", net.virushd.creative.main.FileManager.messages.getString("CreativePrefix"));
		if (s.contains("{Ideas}")) {
			List<String> ideas = net.virushd.creative.main.FileManager.sco_creative.getStringList("Ideas");
			s = s.replace("{Ideas}", ideas.get(new Random().nextInt(ideas.size())));
		}
		return s;
	}

	// creative sign placeholders
	public static String creativeSign(String s) {
		if (CoreMain.pluginAvailable("VirusHD-Creative")) {
			int MaxPlayers = net.virushd.creative.main.FileManager.config.getInt("MaxPlayers");
			String Lobby = net.virushd.creative.main.FileManager.config.getString("GameStates.Lobby");
			String LobbyFull = net.virushd.creative.main.FileManager.config.getString("GameStates.LobbyFull");
			s = s.replace("{Name}", "Creative");
			if (net.virushd.creative.main.PlayerManager.getPlayers().size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
			if (net.virushd.creative.main.PlayerManager.getPlayers().size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
			s = s.replace("{MaxPlayers}", "" + MaxPlayers);
			s = s.replace("{Players}", "" + net.virushd.creative.main.PlayerManager.getPlayers().size());
			s = normal(s);
		}
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

	// normal ttt placeholders
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
	}

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
