package net.virushd.pets.pets;

import org.bukkit.entity.EntityType;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;

public class Enderman extends Pet {

	public Enderman() {
		super(7, EntityType.ENDERMAN, FileManager.pets.getString("PetNames.Enderman"));
	}
}
