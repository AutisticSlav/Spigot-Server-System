package net.virushd.pets.pets;

import org.bukkit.entity.EntityType;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;

public class CaveSpider extends Pet {
	
	public CaveSpider() {
		super(3, EntityType.CAVE_SPIDER, FileManager.messages.getString("PetNames.CaveSpider"));
	}
}
