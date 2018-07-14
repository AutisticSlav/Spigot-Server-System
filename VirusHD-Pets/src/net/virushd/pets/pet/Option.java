package net.virushd.pets.pet;

import java.util.ArrayList;
import java.util.Arrays;

import net.virushd.core.main.SaveUtils;
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
	private int set; // the option case that is currently set
	private Action action;
	private Material material;

	public Option(String name, ArrayList<Object> cases, Action action, Material material) {
		this.name = name;
		this.cases = cases;
		this.set = 0;
		this.action = action;
		this.material = material;
	}

	// save the option to the pets file
	public void save(Player p) {
		FileManager.pets.set(p.getUniqueId().toString() + ".Options." + name, set);
		SaveUtils.saveFile(FileManager.petsF, FileManager.pets);
	}

	// apply the option
	public void run(Player p, Entity ent) {
		set = FileManager.pets.getInt(p.getUniqueId().toString() + ".Options." + name);
		action.run(p, cases.get(set), ent);
	}

	// set the option
	public void set(Player p, Entity ent, Object obj) {
		for (int i = 0; i < cases.size(); i++) {
			if (cases.get(i).equals(obj)) {
				this.set = i;
				save(p);
				run(p, ent);
			}
		}
	}

	// set the option to the next case
	public void nextSet(Player p, Entity ent) {
		if (this.set == cases.size() - 1) {
			this.set = 0;
		} else {
			this.set += 1;
		}
		save(p);
		run(p, ent);
	}

	// get the item of the option
	public ItemStack getItem() {
		String d = cases.get(set).toString();
		String name = FileManager.options.getString("Options." + this.name);
		d = d.replace("true", FileManager.options.getString("True"));
		d = d.replace("false", FileManager.options.getString("False"));
		d = d.replace("small", FileManager.options.getString("Size.Small"));
		d = d.replace("medium", FileManager.options.getString("Size.Medium"));
		d = d.replace("big", FileManager.options.getString("Size.Big"));
		for (Variant variant : Variant.values()) {
			d = d.replace(variant.toString(), FileManager.options.getString("HorseType." + variant.toString()));
		}
		for (Type type : Type.values()) {
			d = d.replace(type.toString(), FileManager.options.getString("CatType." + type.toString()));
		}
		for (DyeColor color : DyeColor.values()) {
			d = d.replace(color.toString(), FileManager.options.getString("Color." + color.toString()));
		}

		return InventoryAPI.createItem(name, Arrays.asList(d), material, null, 1);
	}

	// action class
	public static abstract class Action {
		public abstract void run(Player p, Object theCase, Entity ent);
	}
}
