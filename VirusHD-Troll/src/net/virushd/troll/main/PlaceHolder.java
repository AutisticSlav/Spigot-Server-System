package net.virushd.troll.main;

import org.bukkit.entity.Player;

@Deprecated
public class PlaceHolder {

	public static String Normal (String s) {
		s = s.replace("{TrollPrefix}", FileManager.messages.getString("TrollPrefix"));
		s = net.virushd.core.main.PlaceHolder.Normal(s);
		return s;
	}
	
	public static String WithPlayer (String s, Player p) {
		s = Normal(s);
		s = net.virushd.core.main.PlaceHolder.WithPlayer(s, p);
		return s;
	}
}
