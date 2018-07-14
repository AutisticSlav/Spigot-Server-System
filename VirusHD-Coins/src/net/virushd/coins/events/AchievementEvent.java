package net.virushd.coins.events;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import CoinsAPI.Coins;
import net.virushd.coins.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.CoreMain;

public class AchievementEvent implements Listener {

	@EventHandler
	public void onAchievement(PlayerAchievementAwardedEvent e) {

		Player p = e.getPlayer();

		// add 50 coins
		if (CoreMain.isNormal(p)) {
			if (!(p.getGameMode().equals(GameMode.CREATIVE))) {
				Coins.add(p.getUniqueId(), 50);
				p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 50));
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
			}
		}
	}
}
