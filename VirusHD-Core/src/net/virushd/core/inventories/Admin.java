package net.virushd.core.inventories;

import java.util.Arrays;

import net.virushd.core.main.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import net.virushd.core.main.FileManager;
import net.virushd.core.main.SaveUtils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

public class Admin {
	
	public static void open(Player p) {

		Inventory inv = InventoryAPI.createInventory("&cAdmin Menu", InventoryType.CHEST);
		Inventory locations = InventoryAPI.createInventory("&cLocations", InventoryType.CHEST);
		
		// inv
		inv.setSlot(0, InventoryAPI.createItem("&cSpawn", Arrays.asList("&7Change the spawn location."), Material.valueOf(FileManager.inv_teleporter.getString("Items.Spawn.Item")), null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				SaveUtils.SaveLocationToFile(FileManager.configF, FileManager.config, "Spawns.Lobby", p.getLocation());
			}
		});
		
		inv.setSlot(1, InventoryAPI.createItem("&cTeleporter Locations", Arrays.asList("&7Set the Locations of the Teleporter."), Material.valueOf(FileManager.itm_teleporter.getString("Teleporter.Item")), null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				locations.open(p);
			}
		});
		
		// locations
		int i = 0;
		if (CoreMain.pluginAvailable("VirusHD-CityBuild")) {
			locations.setSlot(i, InventoryAPI.createItem("&cCityBuild", Arrays.asList("&7Change the CityBuild location."), Material.valueOf(FileManager.inv_teleporter.getString("Items.CityBuild.Item")), null, 1), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					SaveUtils.SaveLocationToFile(FileManager.locationsF, FileManager.locations, "CityBuild", p.getLocation());
				}
			});
			i++;
		}
		
		if (CoreMain.pluginAvailable("VirusHD-Creative")) {
			locations.setSlot(i, InventoryAPI.createItem("&cCreative", Arrays.asList("&7Change the Creative location."), Material.valueOf(FileManager.inv_teleporter.getString("Items.Creative.Item")), null, 1), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					SaveUtils.SaveLocationToFile(FileManager.locationsF, FileManager.locations, "Creative", p.getLocation());
				}
			});
			i++;
		}

		if (CoreMain.pluginAvailable("VirusHD-TTT")) {
			locations.setSlot(i, InventoryAPI.createItem("&cTTT", Arrays.asList("&7Change the TTT location."), Material.valueOf(FileManager.inv_teleporter.getString("Items.TTT.Item")), null, 1), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					SaveUtils.SaveLocationToFile(FileManager.locationsF, FileManager.locations, "TTT", p.getLocation());
				}
			});
		}
		
		locations.setSlot(18, SaveUtils.GetItemFromFile(FileManager.itm_inventory, "Leave"), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				inv.open(p);
			}
		});
		
		inv.open(p);
	}
}
