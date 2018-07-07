package net.virushd.coins.events;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import CoinsAPI.Coins;
import net.virushd.coins.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.CoreMain;

public class KillEvent implements Listener {

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		LivingEntity ent = e.getEntity();
		if (ent.getKiller() instanceof Player) {
			
			Player p = (Player) ent.getKiller();
			
			if (CoreMain.isNormal(p)) {
				if (!(p.getGameMode().equals(GameMode.CREATIVE))) {
					if (e.getEntity() instanceof Player) {
						Coins.add(p.getUniqueId(), 30);
						p.sendMessage(PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 30));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
						
					} else if (e.getEntity() instanceof Animals) {
						Coins.add(p.getUniqueId(), 10);
						p.sendMessage(PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 10));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
						
					} else if (e.getEntity() instanceof Monster) {
						Coins.add(p.getUniqueId(), 20);
						p.sendMessage(PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 20));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
					}
				}
			}
		}
	}
}
