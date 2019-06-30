package net.virushd.pets.pets;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Wolf extends Pet {

	public Wolf() {
		super(EntityType.WOLF, FileManager.messages.getString("PetNames.Wolf"));

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
		addOption(new Option("CollarColor", colors, new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Wolf) ent).setCollarColor(DyeColor.valueOf(theCase.toString().replaceFirst("Color.", "")));
			}
		}, Material.BOOK));

		addOption(new Option("Sitting", new ArrayList<>(Arrays.asList(false, true)), new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Wolf) ent).setSitting((boolean) theCase);
			}
		}, Material.SPRUCE_WOOD_STAIRS));
	}
}
