package net.virushd.pets.pet;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.SpawnEgg;

public class Pet {

	private int id;
	private EntityType type;
	private ItemStack item;
	private String name;
	private ArrayList<Option> options = new ArrayList<>();

	public static int ids = 0;

	public Pet(EntityType type, String name) {
		this.id = ids; ids++;
		this.type = type;
		this.name = name;
		
		SpawnEgg egg = new SpawnEgg(type);
		ItemStack item = egg.toItemStack();
		item.setAmount(1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		this.item = item;
	}

	// getters

	public int getId() {
		return id;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public String getName() {
		return name;
	}

	// static method to get a pet by its id
	public static Pet getPet(int id) {
		for (Pet pet : PetManager.pets) {
			if(pet.getId() == id) {
				return pet;
			}
		} return null;
	}

	public ArrayList<Option> getOptions() {
		return options;
	}

	// add an option
	public void addOption(Option option) {
		options.add(option);
	}
}
