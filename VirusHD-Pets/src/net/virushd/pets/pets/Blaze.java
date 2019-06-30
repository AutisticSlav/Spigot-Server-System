package net.virushd.pets.pets;

import org.bukkit.entity.EntityType;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;

public class Blaze extends Pet {

	public Blaze() {
		super(EntityType.BLAZE, FileManager.messages.getString("PetNames.Blaze"));
	}
}