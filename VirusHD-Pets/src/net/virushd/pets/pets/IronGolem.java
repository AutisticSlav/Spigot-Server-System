package net.virushd.pets.pets;

import org.bukkit.entity.EntityType;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;

public class IronGolem extends Pet {

	public IronGolem() {
		super(EntityType.IRON_GOLEM, FileManager.messages.getString("PetNames.IronGolem"));
	}
}