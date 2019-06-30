package net.virushd.pets.pet;

import java.util.ArrayList;
import java.util.Arrays;

import net.virushd.core.api.SaveUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import net.virushd.inventory.main.InventoryAPI;
import net.virushd.pets.main.FileManager;

public class Option {

	private String name;
	private ArrayList<Object> cases;
	private int cse; // the option case that is currently set
	private Action action;
	private Material material;

	public Option(String name, ArrayList<Object> cases, Action action, Material material) {
		this.name = name;
		this.cases = cases;
		this.cse = 0;
		this.action = action;
		this.material = material;
	}

	// save the option to the pets file
	public void save(Player p) {
		FileManager.pets.set(p.getUniqueId().toString() + ".Options." + name, cse);
		SaveUtils.saveFile(FileManager.petsF, FileManager.pets);
	}

	// apply the option
	public void run(Player p, Entity ent) {
		cse = FileManager.pets.getInt(p.getUniqueId().toString() + ".Options." + name);
		action.run(p, cases.get(cse), ent);
	}

	// set the option
	/* public void setCase(Player p, Entity ent, Object obj) {
		for (int i = 0; i < cases.size(); i++) {
			if (cases.get(i).equals(obj)) {
				this.cse = i;
				save(p);
				run(p, ent);
			}
		}
	} */

	// set the option to the next case
	public void nextCase(Player p, Entity ent) {
		if (this.cse == cases.size() - 1) {
			this.cse = 0;
		} else {
			this.cse += 1;
		}
		save(p);
		run(p, ent);
	}

	// get the item of the option
	public ItemStack getItem() {
		String d = cases.get(cse).toString();
		String name = FileManager.options.getString("Options." + this.name);
		d = d.replaceAll("true", FileManager.options.getString("True"));
		d = d.replaceAll("false", FileManager.options.getString("False"));
		d = d.replaceAll("small", FileManager.options.getString("Size.Small"));
		d = d.replaceAll("medium", FileManager.options.getString("Size.Medium"));
		d = d.replaceAll("big", FileManager.options.getString("Size.Big"));
		for (Horse.Variant variant : Horse.Variant.values()) {
			d = d.replaceAll("\\bHorseType." + variant.toString() + "\\b", FileManager.options.getString("HorseType." + variant.toString()));
		}
		for (Horse.Color color : Horse.Color.values()) {
			d = d.replaceAll("\\bHorseColor." + color.toString() + "\\b", FileManager.options.getString("HorseColor." + color.toString()));
		}
		for (Horse.Style style : Horse.Style.values()) {
			d = d.replaceAll("\\bHorseStyle." + style.toString() + "\\b", FileManager.options.getString("HorseStyle." + style.toString()));
		}
		for (Ocelot.Type type : Ocelot.Type.values()) {
			d = d.replaceAll("\\bCatType." + type.toString() + "\\b", FileManager.options.getString("CatType." + type.toString()));
		}
		for (Rabbit.Type type : Rabbit.Type.values()) {
			d = d.replaceAll("\\bRabbitType." + type.toString() + "\\b", FileManager.options.getString("RabbitType." + type.toString()));
		}
		for (Skeleton.SkeletonType type : Skeleton.SkeletonType.values()) {
			d = d.replaceAll("\\bSkeletonType." + type.toString() + "\\b", FileManager.options.getString("SkeletonType." + type.toString()));
		}
		for (Villager.Profession type : Villager.Profession.values()) {
			d = d.replaceAll("\\bProfession." + type.toString() + "\\b", FileManager.options.getString("Profession." + type.toString()));
		}
		for (DyeColor color : DyeColor.values()) {
			d = d.replaceAll("\\bColor." + color.toString() + "\\b", FileManager.options.getString("Color." + color.toString()));
		}

		return InventoryAPI.createItem(name, Arrays.asList(d), material, null, 1);
	}

	// action class
	public static abstract class Action {
		public abstract void run(Player p, Object theCase, Entity ent);
	}
}
