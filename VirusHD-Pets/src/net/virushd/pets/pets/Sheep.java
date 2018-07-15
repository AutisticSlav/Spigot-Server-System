package net.virushd.pets.pets;

import java.util.ArrayList;
import java.util.Arrays;

import net.virushd.core.api.PlaceHolder;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;

@Deprecated
public class Sheep extends Pet{

	public Sheep() {
		super(5, EntityType.SHEEP, FileManager.messages.getString("PetNames.Sheep"));
		
		String baby = PlaceHolder.normal(FileManager.options.getString("Options.Baby"));
		String ride = PlaceHolder.normal(FileManager.options.getString("Options.Ride"));
		
		addOption(new Option(baby, new ArrayList<>(Arrays.asList(true, false)), new Option.Action() {
			
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				
				Ageable age = (Ageable) ent;
				
				if (theCase.equals(true)) {
					age.setBaby();
				} else {
					age.setAdult();
				}
			}
		}, Material.EGG));
		
		addOption(new Option(ride, new ArrayList<>(Arrays.asList(true, false)), new Option.Action() {
			
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				if (theCase.equals(true)) {
					ent.setPassenger(p);
				} else {
					p.leaveVehicle();
				}
			}
		}, Material.SADDLE));
	}
}
