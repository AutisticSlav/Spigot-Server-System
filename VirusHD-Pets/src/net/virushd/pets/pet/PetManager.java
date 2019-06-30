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
		pets.add(new Pig());
		pets.add(new PigZombie());
		pets.add(new Rabbit());
		pets.add(new Sheep());
		pets.add(new Skeleton());
		pets.add(new Slime());
		pets.add(new Spider());
		pets.add(new Villager());
		pets.add(new Witch());
		pets.add(new Wolf());
		pets.add(new Zombie());
	}
}
