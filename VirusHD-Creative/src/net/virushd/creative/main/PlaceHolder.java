package net.virushd.creative.main;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Deprecated
public class PlaceHolder {

	public static String Normal (String s) {
		s = s.replace("{CreativePrefix}", FileManager.messages.getString("CreativePrefix"));
		if (s.contains("{Ideas}")) {
			List<String> ideas = FileManager.sco_creative.getStringList("Ideas");
			Random random = new Random();
			int x = random.nextInt(ideas.size());
			s = s.replace("{Ideas}", ideas.get(x));
		}
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
		s = s.replace("{Name}", "Creative");
		if (CreativeMain.players.size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
		if (CreativeMain.players.size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
		s = s.replace("{MaxPlayers}", "" + MaxPlayers);
		s = s.replace("{Players}", "" + CreativeMain.players.size());
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
}
