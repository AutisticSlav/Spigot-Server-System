package net.virushd.core.inventories;

import net.virushd.core.api.Minigame;
import net.virushd.core.api.PlaceHolder;
import net.virushd.core.api.SaveUtils;
import net.virushd.core.api.Utils;
import net.virushd.core.main.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

public class Teleporter {

	public static void open(Player p) {

		String InventoryDisplayName = PlaceHolder.normal(FileManager.inv_teleporter.getString("Inventory.DisplayName"));
		ItemStack SpawnItem = SaveUtils.getItemFromFile(FileManager.inv_teleporter, "Items.Spawn");
		ItemStack CityBuildItem = SaveUtils.getItemFromFile(FileManager.inv_teleporter, "Items.CityBuild");
		ItemStack CreativeItem = SaveUtils.getItemFromFile(FileManager.inv_teleporter, "Items.Creative");
		ItemStack TTTItem = SaveUtils.getItemFromFile(FileManager.inv_teleporter, "Items.TTT");
		int SpawnSlot = FileManager.inv_teleporter.getInt("Items.Spawn.Slot");
		int CityBuildSlot = FileManager.inv_teleporter.getInt("Items.CityBuild.Slot");
		int CreativeSlot = FileManager.inv_teleporter.getInt("Items.Creative.Slot");
		int TTTSlot = FileManager.inv_teleporter.getInt("Items.TTT.Slot");

		Inventory inv = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);

		// background items
		ItemStack[] BGItems = new ItemStack[27];
		ItemMeta[] BGItemMeta = new ItemMeta[27];

		for (int i = 0; i < 27; i++) {
			BGItems[i] = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) FileManager.inv_teleporter.getInt("Background." + (i)));
			BGItemMeta[i] = BGItems[i].getItemMeta();
			BGItemMeta[i].setDisplayName(" ");
			BGItems[i].setItemMeta(BGItemMeta[i]);
			inv.setSlot(i, BGItems[i], null);
		}

		// spawn item
		inv.setSlot(SpawnSlot, SpawnItem, new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				Utils.smoothTeleport(p, SaveUtils.getLocationFromFile(FileManager.config, "Spawns.Lobby"), GameMode.ADVENTURE);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			}
		});

		// minigame items
		for (Minigame minigame : CoreMain.getMinigames()) {
			inv.setSlot(FileManager.inv_teleporter.getInt("Items." + minigame.getRealName() + ".Slot"), SaveUtils.getItemFromFile(FileManager.inv_teleporter, "Items." + minigame.getRealName()), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Utils.smoothTeleport(p, SaveUtils.getLocationFromFile(FileManager.locations, minigame.getRealName()), GameMode.ADVENTURE);
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				}
			});
		}

		inv.open(p);
	}
}
