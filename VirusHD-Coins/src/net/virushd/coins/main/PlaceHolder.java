package net.virushd.coins.main;

import org.bukkit.entity.Player;

@Deprecated
public class PlaceHolder {

	public static String Normal (String s) {
		s = s.replace("{CoinsPrefix}", FileManager.messages.getString("CoinsPrefix"));
		s = s.replace("{LotteryPrefix}", FileManager.messages.getString("LotteryPrefix"));
		s = s.replace("{NormalPrice}", "" + FileManager.inv_lottery.getInt("NormalPrice"));
		s = s.replace("{HighChancePrice}", "" + FileManager.inv_lottery.getInt("HighChancePrice"));
		s = s.replace("{WaitTime}", "" + FileManager.inv_lottery.getInt("WaitTime"));
		s = net.virushd.core.main.PlaceHolder.Normal(s);
		return s;
	}
	
	public static String WithPlayer (String s, Player p) {
		s = Normal(s);
		s = net.virushd.core.main.PlaceHolder.WithPlayer(s, p);
		return s;
	}
}
