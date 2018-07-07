package net.virushd.citybuild.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Deprecated
public class PlaceHolder {

	public static String Normal (String s) {
		s = s.replace("{CityBuildPrefix}", FileManager.messages.getString("CityBuildPrefix"));
		s = net.virushd.core.main.PlaceHolder.Normal(s);
		return s;
	}
	
	public static String WithPlayer (String s, Player p) {
		s = Normal(s);
		s = net.virushd.core.main.PlaceHolder.WithPlayer(s, p);
		return s;
	}

	public static String Sign (String s) {
		
		int MaxPlayers = FileManager.config.getInt("MaxPlayers");
		String Lobby = FileManager.config.getString("GameStates.Lobby");
		String LobbyFull = FileManager.config.getString("GameStates.LobbyFull");
		
		s = Normal(s);
		
		s = s.replace("{Name}", "CityBuild");
		if (CityBuildMain.players.size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
		if (CityBuildMain.players.size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
		s = s.replace("{MaxPlayers}", "" + MaxPlayers);
		s = s.replace("{Players}", "" + CityBuildMain.players.size());
		
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
}
