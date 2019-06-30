package net.virushd.pets.pets;

import org.bukkit.entity.EntityType;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;

public class Creeper extends Pet {

	public Creeper() {
		super(EntityType.CREEPER, FileManager.messages.getString("PetNames.Creeper"));
	}
}
