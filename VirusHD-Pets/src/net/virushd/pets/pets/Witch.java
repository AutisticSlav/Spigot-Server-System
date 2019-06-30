package net.virushd.pets.pets;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;
import org.bukkit.entity.EntityType;

public class Witch extends Pet {

	public Witch() {
		super(EntityType.WITCH, FileManager.messages.getString("PetNames.Witch"));
	}
}
