package net.virushd.coins.inventories;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import CoinsAPI.Coins;
import net.virushd.coins.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.inventories.Cosmetics;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

public class Lottery {

	public static void open(Player p) {
		
		String InventoryDisplayName = PlaceHolder.Normal(FileManager.inv_lottery.getString("Inventory.DisplayName"));
		String NotEnoughCoins = PlaceHolder.Normal(FileManager.messages.getString("Messages.NotEnoughCoins"));
		String Wait = PlaceHolder.Normal(FileManager.messages.getString("Messages.Wait"));
		
		Inventory inv_lottery = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);
		
		inv_lottery.setSlot(0, SaveUtils.GetItemFromFile(FileManager.inv_lottery, "Items.Info"), new ItemListener() {
			@Override
			public void onItemClick(Player p) {}
		});
		
		inv_lottery.setSlot(1, SaveUtils.GetItemFromFile(FileManager.inv_lottery, "Items.NormalTicket"), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				if (Coins.hasEnough(p.getUniqueId(), FileManager.inv_lottery.getInt("NormalPrice"))) {
					if (canLotto(p)) {
						Coins.remove(p.getUniqueId(), FileManager.inv_lottery.getInt("NormalPrice"));
						lotteryTicket(p, Chance.NORMAL);
					} else {
						p.sendMessage(Wait);
					}
				} else {
					p.sendMessage(NotEnoughCoins);
				}
			}
		});
		
		inv_lottery.setSlot(2, SaveUtils.GetItemFromFile(FileManager.inv_lottery, "Items.HighChanceTicket"), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				if (Coins.hasEnough(p.getUniqueId(), FileManager.inv_lottery.getInt("HighChancePrice"))) {
					if (canLotto(p)) {
						lotteryTicket(p, Chance.HIGH);
						Coins.remove(p.getUniqueId(), FileManager.inv_lottery.getInt("HighChancePrice"));
					} else {
						p.sendMessage(Wait);
					}
				} else {
					p.sendMessage(NotEnoughCoins);
				}
			}
		});
		
		inv_lottery.setSlot(18, SaveUtils.GetItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Leave"), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				Cosmetics.open(p);
			}
		});
		
		inv_lottery.open(p);
	}
	
	private static void lotteryTicket(Player p, Chance chance) {
		String Lost = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Lost"), p);
		String Won = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Won"), p);
		Random random = new Random();
		
		switch (chance) {
		case NORMAL:
			int choose1 = random.nextInt(40);

			if (choose1 < 30) {
				p.sendMessage(Lost);
				
			} else if (30 <= choose1 && choose1 < 34) {
				p.sendMessage(Won.replace("{Won}", "" + 50));
				Coins.add(p.getUniqueId(), 50);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
				
			} else if (34 <= choose1 && choose1 < 37) {
				p.sendMessage(Won.replace("{Won}", "" + 150));
				Coins.add(p.getUniqueId(), 150);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
				
			} else if (37 <= choose1 && choose1 < 39) {
				p.sendMessage(Won.replace("{Won}", "" + 200));
				Coins.add(p.getUniqueId(), 200);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
				
			} else if (choose1 == 39) {
				p.sendMessage(Won.replace("{Won}", "" + 500));
				Coins.add(p.getUniqueId(), 500);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
			}
			
			break;
		case HIGH:
			int choose2 = random.nextInt(6);

			if (choose2 < 3) {
				p.sendMessage(Won.replace("{Won}", "" + 100));
				Coins.add(p.getUniqueId(), 100);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
				
			} else if (3 <= choose2 && choose2 < 5) {
				p.sendMessage(Won.replace("{Won}", "" + 500));
				Coins.add(p.getUniqueId(), 500);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
				
			} else if (choose2 == 6) {
				p.sendMessage(Won.replace("{Won}", "" + 1000));
				Coins.add(p.getUniqueId(), 1000);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
			}
			
			break;
		default:
			break;
		}
		
		// X minutes delay
		int WaitTime = FileManager.inv_lottery.getInt("WaitTime");
		cant.add(p);
		Bukkit.getServer().getScheduler().runTaskLater(CoreMain.main, new Runnable() {
			@Override
			public void run() {
				cant.remove(p);
			}
		}, WaitTime * 60 * 20);
	}
	
	private static boolean canLotto(Player p) {
		if (cant.contains(p)) {
			return false;
		} else {
			return true;
		}
	}
	
	private static ArrayList<Player> cant = new ArrayList<>();
}

enum Chance { NORMAL, HIGH; }
