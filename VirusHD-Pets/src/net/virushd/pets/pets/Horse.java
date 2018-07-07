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

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;

public class Horse extends Pet {

	public Horse() {
		super(8, EntityType.HORSE, FileManager.messages.getString("PetNames.Horse"));
		
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
		
		addOption(new Option("HorseType", new ArrayList<>(Arrays.asList(Variant.values())), new Option.Action() {
			
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Horse) ent).setVariant(Variant.valueOf(theCase.toString()));
			}
		}, Material.BOOK));
		
		
//		addOption(new Option("HorseColor", new ArrayList<>(Arrays.asList(Color.values())), new Option.Action() {
//			
//			@Override
//			public void run(Player p, Object theCase, Entity ent) {
//				((org.bukkit.entity.Horse) ent).setColor(Color.valueOf(theCase.toString()));
//			}
//		}, Material.BOOK));
//		
//		addOption(new Option("HorseStyle", new ArrayList<>(Arrays.asList(Style.values())), new Option.Action() {
//			
//			@Override
//			public void run(Player p, Object theCase, Entity ent) {
//				((org.bukkit.entity.Horse) ent).setStyle(Style.valueOf(theCase.toString()));
//			}
//		}, Material.BOOK));
	}
}
