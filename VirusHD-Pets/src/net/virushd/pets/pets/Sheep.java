package net.virushd.pets.pets;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.material.Colorable;

import java.util.ArrayList;
import java.util.Arrays;

public class Sheep extends Pet {

	public Sheep() {
		super(EntityType.SHEEP, FileManager.messages.getString("PetNames.Sheep"));

		addOption(new Option("Baby", new ArrayList<>(Arrays.asList(false, true)), new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {

				Ageable age = (Ageable) ent;
				age.setAgeLock(true);

				if (theCase.equals(true)) {
					age.setBaby();
				} else {
					age.setAdult();
				}
			}
		}, Material.EGG));

		ArrayList<Object> colors = new ArrayList<>();
		for (DyeColor color : DyeColor.values()) {
			colors.add("Color." + color.toString());
		}
		addOption(new Option("Color", colors, new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((Colorable) ent).setColor(DyeColor.valueOf(theCase.toString().replaceFirst("Color.", "")));
			}
		}, Material.BOOK));
	}
}
