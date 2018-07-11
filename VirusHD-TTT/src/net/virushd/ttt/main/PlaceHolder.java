package net.virushd.ttt.main;

@Deprecated
public class PlaceHolder {
	
/*	public static String Normal (String s) {
		s = s.replace("{TTTPrefix}", FileManager.messages.getString("TTTPrefix"));
		s = net.virushd.core.main.PlaceHolder.Normal(s);
		return s;
	}
	
	public static String WithPlayer (String s, Player p) {
		s = Normal(s);
		s = net.virushd.core.main.PlaceHolder.WithPlayer(s, p);
		return s;
	}

	public static String Sign(String s, int id) {
		
		int MaxPlayers = FileManager.config.getInt("MaxPlayers");
		String Lobby = FileManager.config.getString("GameStates.Lobby");
		String LobbyFull = FileManager.config.getString("GameStates.LobbyFull");
		String Ingame =  FileManager.config.getString("GameStates.Ingame");
		
		s = Normal(s);
		
		s = s.replace("{ID}", "" + id);
		s = s.replace("{Name}", ArenaManager.GetArenaById(id).GetName());
		if (ArenaManager.GetArenaById(id).GameState.equals(GameState.LOBBY)) {
			if (ArenaManager.GetArenaById(id).players.size() == MaxPlayers) s = s.replace("{GameState}", Lobby);
			if (ArenaManager.GetArenaById(id).players.size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
		} else {
			s = s.replace("{GameState}", Ingame);
		}
		s = s.replace("{MaxPlayers}", "" + MaxPlayers);
		s = s.replace("{Players}", "" + ArenaManager.GetArenaById(id).players.size());
		
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}*/
}
