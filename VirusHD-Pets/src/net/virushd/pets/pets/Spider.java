package net.virushd.pets.pets;

import org.bukkit.entity.EntityType;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;

public class Spider extends Pet {

	public Spider() {
		super(19, EntityType.SPIDER, FileManager.messages.getString("PetNames.Spider"));
	}
}
