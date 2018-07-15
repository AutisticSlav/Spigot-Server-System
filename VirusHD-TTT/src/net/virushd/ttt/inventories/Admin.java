package net.virushd.ttt.inventories;

import net.virushd.core.inventories.AnvilGUI;
import net.virushd.core.main.FileManager;
import net.virushd.core.api.SaveUtils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Admin {

	public static void open(Player p) {
		Inventory inv = InventoryAPI.createInventory("&cAdmin Menu", InventoryType.CHEST);

		// display all the arenas
		int i = 0;
		for (Arena a : ArenaManager.getArenas()) {
			ItemStack item = InventoryAPI.createItem("&c" + a.getName() + " (" + a.getID() + ")", Arrays.asList("&7Edit the arena with the ID " + a.getName() + " (" + a.getID() + ")."), Material.WOOL, null, 1);
			if (a.isComplete()) item.setDurability((short) 13);
			else item.setDurability((short) 14);
			inv.setSlot(i, item, new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					openArenaInventory(p, a);
				}
			});
		}

		inv.setSlot(26, InventoryAPI.createItem("&cCreate Arena", Arrays.asList("&7Create an arena"), Material.WORKBENCH, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {

				// create an arena
				AnvilGUI create = new AnvilGUI(p, e -> {
					if (e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
						e.setWillClose(true);
						e.setWillDestroy(true);
						ArenaManager.createArena(e.getName());
					} else {
						e.setWillClose(false);
						e.setWillDestroy(false);
					}
				});

				ItemStack item = new ItemStack(Material.NAME_TAG);
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName("Arena");
				item.setItemMeta(itemMeta);
				create.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, item);
				create.open();
			}
		});

		// a small info
		inv.setSlot(18, InventoryAPI.createItem("&cInfo", Arrays.asList("&4Red &7means the arena is not complete (missing spawns).", "&2Green &7means the arena is complete."), Material.TORCH, null, 1), null);

		inv.open(p);
	}

	private static void openArenaInventory(Player p, Arena a) {

		// the arena menu
		Inventory arena = InventoryAPI.createInventory("&cArena: &7" + a.getName(), InventoryType.HOPPER);

		// delete the arena
		arena.setSlot(0, InventoryAPI.createItem("&cDelete Arena", Arrays.asList("&7Delete the arena (&4CANNOT BE UNDONE!&7)."), Material.BARRIER, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				ArenaManager.deleteArena(a.getID());
				p.closeInventory();
			}
		});

		// add a spawn
		arena.setSlot(1, InventoryAPI.createItem("&cAdd Spawn", Arrays.asList("&7Add a spawn."), Material.valueOf(net.virushd.core.main.FileManager.inv_teleporter.getString("Items.Spawn.Item")), null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				a.addSpawn(p.getLocation());
				ArenaManager.saveArena(a.getID());
			}
		});

		// set the lobby
		arena.setSlot(2, InventoryAPI.createItem("&cSet Lobby", Arrays.asList("&7Set the lobby."), Material.COMPASS, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				a.setLobby(p.getLocation());
				ArenaManager.saveArena(a.getID());
			}
		});

		// delete all the spawns
		arena.setSlot(3, InventoryAPI.createItem("&cDelete Spawns", Arrays.asList("&7Delete all spawns of this arena (&4CANNOT BE UNDONE!&7)."), Material.BARRIER, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				a.deleteSpawns();
				ArenaManager.saveArena(a.getID());
			}
		});

		// leave the menu
		arena.setSlot(4, SaveUtils.getItemFromFile(FileManager.itm_inventory, "Leave"), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				open(p);
			}
		});

		arena.open(p);
	}
}
