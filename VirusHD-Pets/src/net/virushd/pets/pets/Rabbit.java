package net.virushd.pets.pets;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.main.PlaceHolder;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;

@Deprecated
public class Rabbit extends Pet{

	public Rabbit() {
		super(4, EntityType.RABBIT, FileManager.messages.getString("PetNames.Rabbit"));
		
		String baby = PlaceHolder.Normal(FileManager.options.getString("Options.Baby"));
		String ride = PlaceHolder.Normal(FileManager.options.getString("Options.Ride"));
		
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
