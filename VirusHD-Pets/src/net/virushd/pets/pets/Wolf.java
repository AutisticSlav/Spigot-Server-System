package net.virushd.pets.pets;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;

public class Wolf extends Pet {

	public Wolf() {
		super(23, EntityType.WOLF, FileManager.messages.getString("PetNames.Wolf"));
		
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
		
		addOption(new Option("Tamed", new ArrayList<>(Arrays.asList(false, true)), new Option.Action() {
			
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((Tameable) ent).setTamed((boolean) theCase);
			}
		}, Material.BONE));
		
		addOption(new Option("CollarColor", new ArrayList<>(Arrays.asList(DyeColor.values())), new Option.Action() {
			
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Wolf) ent).setCollarColor(DyeColor.valueOf(theCase.toString()));
			}
		}, Material.BOOK));
		
		addOption(new Option("Sitting", new ArrayList<>(Arrays.asList(false, true)), new Option.Action() {
			
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Ocelot) ent).setSitting((boolean) theCase);
			}
		}, Material.SPRUCE_WOOD_STAIRS));
	}
}
