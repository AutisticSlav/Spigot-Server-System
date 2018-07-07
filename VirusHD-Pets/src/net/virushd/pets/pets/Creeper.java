package net.virushd.pets.pets;

import org.bukkit.entity.EntityType;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;

public class Creeper extends Pet {

	public Creeper() {
		super(6, EntityType.CREEPER, FileManager.pets.getString("PetNames.Creeper"));
	}
}
