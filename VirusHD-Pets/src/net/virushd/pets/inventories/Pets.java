package net.virushd.pets.inventories;

import net.virushd.core.inventories.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import CoinsAPI.Coins;
import net.virushd.core.inventories.Cosmetics;
import net.virushd.core.main.SaveUtils;
import net.virushd.core.main.Utils;
import net.virushd.core.main.PlaceHolder;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;
import net.virushd.core.inventories.AnvilGUI.AnvilClickEvent;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;
import net.virushd.pets.pet.PetManager;
import net.virushd.pets.pet.PetUtils;

public class Pets {
	
	public static void open(Player p) {
		
		String InventoryDisplayName = PlaceHolder.WithPlayer(FileManager.inv_pets.getString("Inventory.DisplayName"), p);
		int PetPrice = FileManager.messages.getInt("PetPrice");
		
		if (PetUtils.hasPet(p)) {
			
			/*
			 * Has Pet
			 */
			Inventory hasPet = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);
			
			String Sell = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Sell"), p);
			String Hide = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Hide"), p);
			String Show = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Show"), p);
			String Name = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Name"), p);
			boolean isHide = FileManager.pets.getBoolean(p.getUniqueId().toString() + ".Hide");
			
			Pet pet = Pet.getPet(FileManager.pets.getInt(p.getUniqueId().toString() + ".ID"));
			String petDisplayName = FileManager.pets.getString(p.getUniqueId().toString() + ".Name");
			
			// hasPet
			hasPet.setSlot(3, SaveUtils.GetItemFromFile(FileManager.inv_pets, "Items.Sell"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					
					// sell
					p.closeInventory();
					p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
					PetUtils.sellPet(p);
					p.sendMessage(Sell);
					p.playSound(p.getLocation(), Sound.BLAZE_HIT, 1, 1);
					Coins.add(p.getUniqueId(), PetPrice);
				}
			});
			hasPet.setSlot(4, SaveUtils.GetItemFromFile(FileManager.inv_pets, "Items.Name"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					
					// name
					AnvilGUI name = new AnvilGUI(p, e -> {
						if (e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
							e.setWillClose(true);
							e.setWillDestroy(true);
							FileManager.pets.set(p.getUniqueId().toString() + ".Name", e.getName());
							if (!isHide) {
								if (p.hasPermission("virushd.pets.color") || p.hasPermission("*")) {
									PetUtils.getPet(p).setCustomName(ChatColor.translateAlternateColorCodes('&', e.getName()));
								} else {
									PetUtils.getPet(p).setCustomName(e.getName());
								}
							}
							p.sendMessage(Name);
							SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
						} else {
							e.setWillClose(false);
							e.setWillDestroy(false);
						}
					});
					
					ItemStack item = new ItemStack(Material.NAME_TAG);
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setDisplayName(petDisplayName);
					item.setItemMeta(itemMeta);
					name.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, item);
					name.open();
				}
			});
			if (isHide) {
				hasPet.setSlot(5, SaveUtils.GetItemFromFile(FileManager.inv_pets, "Items.Show"), new ItemListener() {
					@Override
					public void onItemClick(Player p) {
						
						// show
						p.closeInventory();
						try {
							FileManager.pets.set(p.getUniqueId().toString() + ".Hide", false);
							PetUtils.spawnPet(p, p.getWorld());
							p.sendMessage(Show);
							p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
							p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
							SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
						} catch (Exception e) {
							FileManager.pets.set(p.getUniqueId().toString() + ".Hide", false);
							PetUtils.spawnPet(p, p.getWorld());
							p.sendMessage(Show);
							p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
							p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
							SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
						}
					}
				});
			} else {
				hasPet.setSlot(5, SaveUtils.GetItemFromFile(FileManager.inv_pets, "Items.Hide"), new ItemListener() {
					@Override
					public void onItemClick(Player p) {
						
						// hide
						p.closeInventory();
						try {
							FileManager.pets.set(p.getUniqueId().toString() + ".Hide", true);
							p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
							PetUtils.despawnPet(p, p.getWorld());
							p.sendMessage(Hide);
							p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
							SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
						} catch (Exception ex) {
							PetUtils.spawnPet(p, p.getWorld());
							FileManager.pets.set(p.getUniqueId().toString() + ".Hide", true);
							p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
							PetUtils.despawnPet(p, p.getWorld());
							p.sendMessage(Hide);
							p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
							SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
						}
					}
				});
			}
			
			int s = pet.getOptions().size();
			if (s != 0) {
				int slot;
				if (s % 2 == 0) {
					slot = 13 - ((s) / 2);
				} else {
					slot = 13 - ((s - 1) / 2);
				}
				
				for (Option option : pet.getOptions()) {
					hasPet.setSlot(slot, option.getItem(), new ItemListener() {
						@Override
						public void onItemClick(Player p) {
							option.nextSet(p, PetUtils.getPet(p));
							open(p);
						}
					});
					slot++;
				}
			}
			
			hasPet.setSlot(18, SaveUtils.GetItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Leave"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Cosmetics.open(p);
				}
			});
			
			hasPet.open(p);
			
		} else {
			
			/*
			 * No Pet
			 */
			Inventory noPet_1 = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);
			Inventory noPet_2 = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);
			
			String Buy = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Buy"), p);
			String NotEnoughCoins = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NotEnoughCoins"), p);
			
			// add pets
			int i = 0;
			for (Pet pets : PetManager.pets) {
				if (i < 18) {
					noPet_1.setSlot(i, pets.getItem(), new ItemListener() {
						@Override
						public void onItemClick(Player p) {
							
							// buy
							p.closeInventory();
							String name = pets.getName();
							if (Coins.hasEnough(p.getUniqueId(), PetPrice)) {
								for (Pet pets2 : PetManager.pets) {
									if (pets2.getId() == pets.getId()) {
										Coins.remove(p.getUniqueId(), PetPrice);
										p.sendMessage(Buy);
										Utils.SpawnFirework(p.getLocation().add(0, -1, 0), Color.RED, Type.BALL_LARGE, 1);
										Utils.SpawnFirework(p.getLocation().add(1, -1, 1), Color.BLACK, Type.BALL_LARGE, 1);
										Utils.SpawnFirework(p.getLocation().add(-1, -1, -1), Color.BLUE, Type.BALL_LARGE, 1);
										PetUtils.buyPet(p, pets2, name);
									}
								}
							} else {
								p.sendMessage(NotEnoughCoins);
							}
						}
					});
				} else {
					noPet_2.setSlot(i - 18, pets.getItem(), new ItemListener() {
						@Override
						public void onItemClick(Player p) {
							
							// buy
							p.closeInventory();
							String name = pets.getName();
							if (Coins.hasEnough(p.getUniqueId(), PetPrice)) {
								for (Pet pets2 : PetManager.pets) {
									if (pets2.getId() == pets.getId()) {
										Coins.remove(p.getUniqueId(), PetPrice);
										p.sendMessage(Buy);
										Utils.SpawnFirework(p.getLocation().add(0, -1, 0), Color.RED, Type.BALL_LARGE, 1);
										Utils.SpawnFirework(p.getLocation().add(1, -1, 1), Color.BLACK, Type.BALL_LARGE, 1);
										Utils.SpawnFirework(p.getLocation().add(-1, -1, -1), Color.BLUE, Type.BALL_LARGE, 1);
										PetUtils.buyPet(p, pets2, name);
									}
								}
							} else {
								p.sendMessage(NotEnoughCoins);
							}
						}
					});
				}
				i++;
			}
			
			// noPet_1
			noPet_1.setSlot(18, SaveUtils.GetItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Leave"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Cosmetics.open(p);
				}
			});
			
			noPet_1.setSlot(25, SaveUtils.GetItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Backward"), new ItemListener() {
				@Override
				public void onItemClick(Player player) {} //TODO Fix that
			});
			
			noPet_1.setSlot(26, SaveUtils.GetItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Forward"), new ItemListener() {
				@Override
				public void onItemClick(Player player) {
					noPet_2.open(p);
				}
			});
			
			// noPet_2
			noPet_2.setSlot(18, SaveUtils.GetItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Leave"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Cosmetics.open(p);
				}
			});
			
			noPet_2.setSlot(25, SaveUtils.GetItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Backward"), new ItemListener() {
				@Override
				public void onItemClick(Player player) {
					noPet_1.open(p);
				}
			});
			
			noPet_2.setSlot(26, SaveUtils.GetItemFromFile(net.virushd.core.main.FileManager.itm_inventory, "Forward"), new ItemListener() {
				@Override
				public void onItemClick(Player player) {} // TODO Fix that
			});
			
			noPet_1.open(p);
		}
	}
}
