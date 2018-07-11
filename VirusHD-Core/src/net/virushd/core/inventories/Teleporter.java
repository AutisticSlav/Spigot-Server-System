package net.virushd.core.inventories;

import net.virushd.core.main.*;
import org.bukkit.Bukkit;
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

		String InventoryDisplayName = PlaceHolder.Normal(FileManager.inv_teleporter.getString("Inventory.DisplayName"));
		ItemStack SpawnItem = SaveUtils.GetItemFromFile(FileManager.inv_teleporter, "Items.Spawn");
		ItemStack CityBuildItem = SaveUtils.GetItemFromFile(FileManager.inv_teleporter, "Items.CityBuild");
		ItemStack CreativeItem = SaveUtils.GetItemFromFile(FileManager.inv_teleporter, "Items.Creative");
		ItemStack TTTItem = SaveUtils.GetItemFromFile(FileManager.inv_teleporter, "Items.TTT");
		int SpawnSlot = FileManager.inv_teleporter.getInt("Items.Spawn.Slot");
		int CityBuildSlot = FileManager.inv_teleporter.getInt("Items.CityBuild.Slot");
		int CreativeSlot = FileManager.inv_teleporter.getInt("Items.Creative.Slot");
		int TTTSlot = FileManager.inv_teleporter.getInt("Items.Creative.TTT");

		Inventory inv = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);
		
		// background items
		ItemStack[] BGItems = new ItemStack[27];
		ItemMeta[] BGItemMeta = new ItemMeta[27];

		for (int i = 0; i < 27; i++) {
			BGItems[i] = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) FileManager.inv_teleporter.getInt("Background." + (i + 1)));
			BGItemMeta[i] = BGItems[i].getItemMeta();
			BGItemMeta[i].setDisplayName(" ");
			BGItems[i].setItemMeta(BGItemMeta[i]);
			inv.setSlot(i, BGItems[i], null);
		}
		
		// Spawn Item
		inv.setSlot(SpawnSlot - 1, SpawnItem, new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				SetLobby.setLobby(p);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			}
		});
		
		// CityBuild Item
		if (CoreMain.pluginAvailable("VirusHD-CityBuild")) {
			inv.setSlot(CityBuildSlot - 1, CityBuildItem, new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Utils.SmoothTeleport(p, SaveUtils.GetLocationFromFile(FileManager.locations, "CityBuild"));
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				}
			});
		}

		// Creative Item
		if (CoreMain.pluginAvailable("VirusHD-Creative")) {
			inv.setSlot(CreativeSlot - 1, CreativeItem, new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Utils.SmoothTeleport(p, SaveUtils.GetLocationFromFile(FileManager.locations, "Creative"));
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				}
			});
		}

		// TTT Item
		if (CoreMain.pluginAvailable("VirusHD-TTT")) {
			inv.setSlot(TTTSlot - 1, TTTItem, new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Utils.SmoothTeleport(p, SaveUtils.GetLocationFromFile(FileManager.locations, "TTT"));
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				}
			});
		}
		
		inv.open(p);
	}
}
