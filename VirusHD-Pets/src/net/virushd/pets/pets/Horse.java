package net.virushd.pets.pets;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;

public class Horse extends Pet {

	public Horse() {
		super(EntityType.HORSE, FileManager.messages.getString("PetNames.Horse"));

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

		ArrayList<Object> types = new ArrayList<>();
		for (Variant type : Variant.values()) {
			types.add("HorseType." + type.toString());
		}
		addOption(new Option("HorseType", types, new Option.Action() {
			
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Horse) ent).setVariant(Variant.valueOf(theCase.toString().replaceFirst("HorseType.", "")));
			}
		}, Material.BOOK));

		ArrayList<Object> colors = new ArrayList<>();
		for (Color type : Color.values()) {
			colors.add("HorseColor." + type.toString());
		}
		addOption(new Option("HorseColor", colors, new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Horse) ent).setColor(Color.valueOf(theCase.toString().replaceFirst("HorseColor.", "")));
			}
		}, Material.BOOK));

		ArrayList<Object> styles = new ArrayList<>();
		for (Style type : Style.values()) {
			styles.add("HorseStyle." + type.toString());
		}
		addOption(new Option("HorseStyle", styles, new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Horse) ent).setStyle(Style.valueOf(theCase.toString().replaceFirst("HorseStyle.", "")));
			}
		}, Material.BOOK));
	}
}
