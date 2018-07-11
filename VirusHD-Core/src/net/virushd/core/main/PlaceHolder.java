package net.virushd.core.main;

import java.util.List;
import java.util.Random;

import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.arena.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import CoinsAPI.Coins;
import net.virushd.creative.main.CreativeMain;
import net.virushd.citybuild.main.CityBuildMain;

public class PlaceHolder {

	public static String Normal (String s) {
		s = s.replace("{Prefix}", FileManager.messages.getString("Prefix"));
		if (CoreMain.pluginAvailable("VirusHD-CityBuild")) s = CityBuild(s);
		if (CoreMain.pluginAvailable("VirusHD-Creative")) s = Creative(s);
		if (CoreMain.pluginAvailable("VirusHD-Coins")) s = Coins(s);
		if (CoreMain.pluginAvailable("VirusHD-Pets")) s = Pets(s);
		if (CoreMain.pluginAvailable("VirusHD-TTT")) s = TTT(s);
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
	
	public static String WithPlayer (String s, Player p) {
		s = Normal(s);
		s = s.replace("{Coins}", "" + Coins.get(p.getUniqueId()));
		s = s.replace("{World}", p.getWorld().getName());
		s = s.replace("{PlayerName}", p.getName());
		s = s.replace("{DisplayName}", p.getDisplayName());
		s = s.replace("{Rank}", Utils.GetRank(p));
		s = s.replace("{OnlinePlayers}", "" + Bukkit.getOnlinePlayers().size());
		s = s.replace("{MaxOnlinePlayers}", "" + Bukkit.getMaxPlayers());
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
	
	private static String CityBuild(String s) {
		s = s.replace("{CityBuildPrefix}", net.virushd.citybuild.main.FileManager.messages.getString("CityBuildPrefix"));
		return s;
	}
	
	public static String CityBuildSign(String s) {
		if (CoreMain.pluginAvailable("VirusHD-CityBuild")) {
			int MaxPlayers = net.virushd.citybuild.main.FileManager.config.getInt("MaxPlayers");
			String Lobby = net.virushd.citybuild.main.FileManager.config.getString("GameStates.Lobby");
			String LobbyFull = net.virushd.citybuild.main.FileManager.config.getString("GameStates.LobbyFull");
			s = s.replace("{Name}", "CityBuild");
			if (CityBuildMain.players.size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
			if (CityBuildMain.players.size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
			s = s.replace("{MaxPlayers}", "" + MaxPlayers);
			s = s.replace("{Players}", "" + CityBuildMain.players.size());
			s = Normal(s);
		}
		return s;
	}
	
	private static String Creative(String s) {
		s = s.replace("{CreativePrefix}", net.virushd.creative.main.FileManager.messages.getString("CreativePrefix"));
		if (s.contains("{Ideas}")) {
			List<String> ideas = net.virushd.creative.main.FileManager.sco_creative.getStringList("Ideas");
			s = s.replace("{Ideas}", ideas.get(new Random().nextInt(ideas.size())));
		}
		return s;
	}
	
	public static String CreativeSign(String s) {
		if (CoreMain.pluginAvailable("VirusHD-Creative")) {
			int MaxPlayers = net.virushd.creative.main.FileManager.config.getInt("MaxPlayers");
			String Lobby = net.virushd.creative.main.FileManager.config.getString("GameStates.Lobby");
			String LobbyFull = net.virushd.creative.main.FileManager.config.getString("GameStates.LobbyFull");
			s = s.replace("{Name}", "Creative");
			if (CreativeMain.players.size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
			if (CreativeMain.players.size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
			s = s.replace("{MaxPlayers}", "" + MaxPlayers);
			s = s.replace("{Players}", "" + CreativeMain.players.size());
			s = Normal(s);
		}
		return s;
	}
	
	private static String Coins(String s) {
		s = s.replace("{CoinsPrefix}", net.virushd.coins.main.FileManager.messages.getString("CoinsPrefix"));
		s = s.replace("{LotteryPrefix}", net.virushd.coins.main.FileManager.messages.getString("LotteryPrefix"));
		s = s.replace("{NormalPrice}", "" + net.virushd.coins.main.FileManager.inv_lottery.getInt("NormalPrice"));
		s = s.replace("{HighChancePrice}", "" + net.virushd.coins.main.FileManager.inv_lottery.getInt("HighChancePrice"));
		s = s.replace("{WaitTime}", "" + net.virushd.coins.main.FileManager.inv_lottery.getInt("WaitTime"));
		return s;
	}
	
	private static String Pets(String s) {
		s = s.replace("{PetsPrefix}", net.virushd.pets.main.FileManager.messages.getString("PetsPrefix"));
		s = s.replace("{PetPrice}", net.virushd.pets.main.FileManager.messages.getString("PetPrice"));
		return s;
	}
	
	private static String TTT(String s) {
		s = s.replace("{TTTPrefix}", net.virushd.ttt.main.FileManager.messages.getString("TTTPrefix"));
		return s;
	}
	
	public static String TTTSign(String s, int id) {
		if (CoreMain.pluginAvailable("VirusHD-TTT")) {
			Arena arena = ArenaManager.getArenaByID(id);
			if (arena != null && arena.isComplete()) {
				int MaxPlayers = net.virushd.ttt.main.FileManager.config.getInt("MaxPlayers");
				String Lobby = net.virushd.ttt.main.FileManager.config.getString("GameStates.Lobby");
				String LobbyFull = net.virushd.ttt.main.FileManager.config.getString("GameStates.LobbyFull");
				String Ingame = net.virushd.ttt.main.FileManager.config.getString("GameStates.Ingame");
				s = s.replace("{Name}", arena.getName());
				if (arena.getGameState() == GameState.LOBBY && arena.getPlayers().size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
				if (arena.getGameState() == GameState.LOBBY && arena.getPlayers().size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
				if (arena.getGameState() == GameState.STARTING || arena.getGameState() == GameState.GAME || arena.getGameState() == GameState.RESTARTING) s = s.replace("{GameState}", Ingame);
				s = s.replace("{MaxPlayers}", "" + MaxPlayers);
				s = s.replace("{Players}", "" + arena.getPlayers().size());
				s = Normal(s);
			}
		}
		return s;
	}
}
