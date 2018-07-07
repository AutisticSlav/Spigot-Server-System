package net.virushd.pets.main;

import org.bukkit.entity.Player;

@Deprecated
public class PlaceHolder {

	public static String Normal (String s) {
		s = s.replace("{PetsPrefix}", FileManager.messages.getString("PetsPrefix"));
		s = s.replace("{PetPrice}", FileManager.messages.getString("PetPrice"));
		s = net.virushd.core.main.PlaceHolder.Normal(s);
		return s;
	}
	
	public static String WithPlayer (String s, Player p) {
		s = Normal(s);
		s = net.virushd.core.main.PlaceHolder.WithPlayer(s, p);
		return s;
	}
}
