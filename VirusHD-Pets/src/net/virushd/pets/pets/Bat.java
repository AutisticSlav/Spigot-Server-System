package net.virushd.pets.pets;

import org.bukkit.entity.EntityType;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;

public class Bat extends Pet {

	public Bat() {
		super(EntityType.BAT, FileManager.messages.getString("PetNames.Bat"));
	}
}
