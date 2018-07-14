package net.virushd.coins.events;

import net.virushd.core.main.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import CoinsAPI.Coins;
import net.virushd.coins.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.CoreMain;

public class BlockBreakEvent implements Listener {

	@EventHandler
	public void onBlockBreack(org.bukkit.event.block.BlockBreakEvent e) {
		Player p = e.getPlayer();

		if (PlayerManager.isNormal(p)) {

			// make sure the player isn't cheating
			if (!(p.getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH))) {
				if (!(p.getGameMode().equals(GameMode.CREATIVE))) {

					// if the block is an ore add coins
					Block block = e.getBlock();
					switch (block.getType()) {
						case DIAMOND_ORE:
							Coins.add(p.getUniqueId(), 50);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 50));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						case COAL_ORE:
							Coins.add(p.getUniqueId(), 5);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 5));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						case EMERALD_ORE:
							Coins.add(p.getUniqueId(), 50);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 50));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						case GOLD_ORE:
							Coins.add(p.getUniqueId(), 20);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 20));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						case GLOWING_REDSTONE_ORE:
							Coins.add(p.getUniqueId(), 5);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 5));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						case IRON_ORE:
							Coins.add(p.getUniqueId(), 10);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 10));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						case LAPIS_ORE:
							Coins.add(p.getUniqueId(), 20);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 20));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						case QUARTZ_ORE:
							Coins.add(p.getUniqueId(), 5);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 5));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						case REDSTONE_ORE:
							Coins.add(p.getUniqueId(), 5);
							p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CoinsEarned"), p).replace("{Amount}", "" + 5));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							break;
						default:
							break;
					}
				}
			}
		}
	}
}
