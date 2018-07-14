package net.virushd.pets.pet;

import java.util.ArrayList;

import net.virushd.pets.pets.*;

public class PetManager {

	public static ArrayList<Pet> pets = new ArrayList<>();

	// register all the pets
	public static void registerPets() {
		pets.add(new Bat());
		pets.add(new Blaze());
		pets.add(new CaveSpider());
		pets.add(new Chicken());
		pets.add(new Cow());
		pets.add(new Creeper());
		pets.add(new Enderman());
		pets.add(new Horse());
		pets.add(new IronGolem());
		pets.add(new MagmaCube());
		pets.add(new MushroomCow());
		pets.add(new Ocelot());

		// id:19
		pets.add(new Spider());
	}
}
