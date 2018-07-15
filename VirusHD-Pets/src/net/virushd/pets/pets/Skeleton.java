package net.virushd.pets.pets;

import java.util.ArrayList;
import java.util.Arrays;

import net.virushd.core.api.PlaceHolder;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;

@Deprecated
public class Skeleton extends Pet{

	public Skeleton() {
		super(6, EntityType.SKELETON, FileManager.messages.getString("PetNames.Skeleton"));
		
		String ride = PlaceHolder.normal(FileManager.options.getString("Options.Ride"));
		
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
