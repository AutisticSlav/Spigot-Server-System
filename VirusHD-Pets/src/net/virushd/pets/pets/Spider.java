package net.virushd.pets.pets;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;
import org.bukkit.entity.EntityType;

public class Spider extends Pet {
	public Spider() {
		super(EntityType.SPIDER, FileManager.messages.getString("PetNames.Spider"));
	}
}
