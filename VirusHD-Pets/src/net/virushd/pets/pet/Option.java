package net.virushd.pets.pet;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.virushd.inventory.main.InventoryAPI;
import net.virushd.pets.main.FileManager;

public class Option {

	private String name;
	private ArrayList<Object> cases;
	private int set;
	private Action action;
	private Material material;

	public Option(String name, ArrayList<Object> cases, Action action, Material material) {
		this.name = name;
		this.cases = cases;
		this.set = 0;
		this.action = action;
		this.material = material;
	}
	
	public void save(Player p) {
		FileManager.pets.set(p.getUniqueId().toString() + ".Options." + name, set);
		FileManager.savePetsFile();
	}

	public void run(Player p, Entity ent) {
		set = FileManager.pets.getInt(p.getUniqueId().toString() + ".Options." + name);
		action.run(p, cases.get(set), ent);
	}

	public void set(Player p, Entity ent, Object obj) {
		for (int i = 0; i < cases.size(); i++) {
			if (cases.get(i).equals(obj)) {
				this.set = i;
				save(p);
				run(p, ent);
			}
		}
	}
	
	public void nextSet(Player p, Entity ent) {
		if (this.set == cases.size() - 1) {
			this.set = 0;
		} else {
			this.set += 1;
		}
		save(p);
		run(p, ent);
	}

	public ItemStack getItem() {
		String d = cases.get(set).toString();
		String name = FileManager.options.getString("Options." + this.name);
		d = d.replace("true", FileManager.options.getString("True"));
		d = d.replace("false", FileManager.options.getString("False"));
		d = d.replace("small", FileManager.options.getString("Size.Small"));
		d = d.replace("medium", FileManager.options.getString("Size.Medium"));
		d = d.replace("big", FileManager.options.getString("Size.Big"));
		for (Variant variant : Variant.values()) { d = d.replace(variant.toString(), FileManager.options.getString("HorseType." + variant.toString())); }
		for (Type type : Type.values()) { d = d.replace(type.toString(), FileManager.options.getString("CatType." + type.toString())); }
		for (DyeColor color : DyeColor.values()) { d = d.replace(color.toString(), FileManager.options.getString("Color." + color.toString())); }
		
		return InventoryAPI.createItem(name, Arrays.asList(d), material, null, 1);
	}

	public static abstract class Action {
		public abstract void run(Player p, Object theCase, Entity ent);
	}
}
