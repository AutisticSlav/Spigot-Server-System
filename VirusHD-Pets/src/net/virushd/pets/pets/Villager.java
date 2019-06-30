package net.virushd.pets.pets;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import java.util.ArrayList;
import java.util.Arrays;

public class Villager extends Pet {

	public Villager() {
		super(EntityType.VILLAGER, FileManager.messages.getString("PetNames.Villager"));

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
		for (Profession type : Profession.values()) {
			types.add("Profession." + type.toString());
		}
		addOption(new Option("Profession", types, new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Villager) ent).setProfession(Profession.valueOf(theCase.toString().replaceFirst("Profession.", "")));
			}
		}, Material.BOOK));
	}
}
