package net.virushd.coins.inventories;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import CoinsAPI.Coins;
import net.virushd.coins.main.FileManager;
import net.virushd.core.api.PlaceHolder;
import net.virushd.core.inventories.Cosmetics;
import net.virushd.core.main.CoreMain;
import net.virushd.core.api.SaveUtils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

public class Lottery {

	public static void open(Player p) {

		String InventoryDisplayName = PlaceHolder.normal(FileManager.inv_lottery.getString("Inventory.DisplayName"));
		String NotEnoughCoins = PlaceHolder.normal(FileManager.messages.getString("Messages.NotEnoughCoins"));
		String Wait = PlaceHolder.normal(FileManager.messages.getString("Messages.Wait"));

		Inventory inv_lottery = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);

		// an info
		inv_lottery.setSlot(0, SaveUtils.getItemFromFile(FileManager.inv_lottery, "Items.Info"), null);

		// get a normal ticket
		inv_lottery.setSlot(1, SaveUtils.getItemFromFile(FileManager.inv_lottery, "Items.NormalTicket"), new ItemListener() {
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

		// get a high chance ticket
		inv_lottery.setSlot(2, SaveUtils.getItemFromFile(FileManager.inv_lottery, "Items.HighChanceTicket"), new ItemListener() {
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

		// go back to cosmetics
		inv_lottery.setSlot(18, SaveUtils.getItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Leave"), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				Cosmetics.open(p);
			}
		});

		inv_lottery.open(p);
	}

	// the win algoritm
	private static void lotteryTicket(Player p, Chance chance) {
		String Lost = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Lost"), p);
		String Won = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Won"), p);
		Random random = new Random();

		// switch depending on the ticket
		switch (chance) {
			case NORMAL:

				// normal chance

				int choose1 = random.nextInt(40);

				if (choose1 < 30) {
					p.sendMessage(Lost);

				} else if (choose1 < 34) {
					p.sendMessage(Won.replace("{Won}", "" + 50));
					Coins.add(p.getUniqueId(), 50);
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);

				} else if (choose1 < 37) {
					p.sendMessage(Won.replace("{Won}", "" + 150));
					Coins.add(p.getUniqueId(), 150);
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);

				} else if (choose1 < 39) {
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

				// high chance

				int choose2 = random.nextInt(6);

				if (choose2 < 3) {
					p.sendMessage(Won.replace("{Won}", "" + 100));
					Coins.add(p.getUniqueId(), 100);
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);

				} else if (choose2 < 5) {
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

		// set a delay of x minutes
		int WaitTime = FileManager.inv_lottery.getInt("WaitTime");
		cant.add(p);
		Bukkit.getServer().getScheduler().runTaskLater(CoreMain.main, () -> cant.remove(p), WaitTime * 60 * 20);
	}

	private static boolean canLotto(Player p) {
		return !cant.contains(p);
	}

	private static ArrayList<Player> cant = new ArrayList<>();
}

enum Chance {NORMAL, HIGH}
