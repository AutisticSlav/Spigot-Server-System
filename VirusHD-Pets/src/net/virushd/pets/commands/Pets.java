package net.virushd.pets.commands;

import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.SaveUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import CoinsAPI.Coins;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.Utils;
import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Pet;
import net.virushd.pets.pet.PetManager;
import net.virushd.pets.pet.PetUtils;

@Deprecated
public class Pets implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			
			Player p = (Player) sender;
				
			String NoPerm = PlaceHolder.WithPlayer(net.virushd.core.main.FileManager.messages.getString("Messages.NoPerm"), p);
			String Usage = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Usage"), p);
			String Sell = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Sell"), p);
			String Buy = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Buy"), p);
			String NoPet = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NoPet"), p);
			String Name = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Name"), p);
			String Hide = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Hide"), p);
			String Show = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Show"), p);
			String AlreadyHide = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.AlreadyHide"), p);
			String AlreadyShown = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.AlreadyShown"), p);
			String NotANumber = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NotANumber"), p);
			String AlreadyAPet = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.AlreadyAPet"), p);
			String WrongID = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.WrongID"), p);
			String NotEnoughCoins = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NotEnoughCoins"), p);
			String IDs = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.IDs"), p);
			String IDFormat;
			int PetPrice = FileManager.messages.getInt("PetPrice");
			
			boolean isHide = FileManager.pets.getBoolean(p.getUniqueId().toString() + ".Hide");
			
			if(cmd.getName().equalsIgnoreCase("pet")) {
				if (p.hasPermission("virushd.pets") || p.hasPermission("*")) {
					if (CoreMain.players.contains(p)) {
						switch (args.length) {
						case 0:
							p.sendMessage(Usage);
							break;
	
						// sell, hide oder show
						case 1:
							if (args[0].equalsIgnoreCase("sell")) {
								if (PetUtils.hasPet(p)) {
									p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
									PetUtils.sellPet(p);
									p.sendMessage(Sell);
									p.playSound(p.getLocation(), Sound.BLAZE_HIT, 1, 1);
									Coins.add(p.getUniqueId(), PetPrice);
								} else {
									p.sendMessage(NoPet);
								}
	
							} else if (args[0].equalsIgnoreCase("hide")) {
								if (PetUtils.hasPet(p)) {
									try {
										if (isHide == false) {
											FileManager.pets.set(p.getUniqueId().toString() + ".Hide", true);
											p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
											PetUtils.despawnPet(p, p.getWorld());
											p.sendMessage(Hide);
											p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
											SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
										} else {
											p.sendMessage(AlreadyHide);
										}
									} catch (Exception ex) {
										PetUtils.spawnPet(p, p.getWorld());
										FileManager.pets.set(p.getUniqueId().toString() + ".Hide", true);
										p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
										PetUtils.despawnPet(p, p.getWorld());
										p.sendMessage(Hide);
										p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
										SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
									}
								} else {
									p.sendMessage(NoPet);
								}
							} else if (args[0].equalsIgnoreCase("show")){
								if (PetUtils.hasPet(p)) {
									try {
										if (isHide == true) {
											FileManager.pets.set(p.getUniqueId().toString() + ".Hide", false);
											PetUtils.spawnPet(p, p.getWorld());
											p.sendMessage(Show);
											p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
											p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
											SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
										} else {
											p.sendMessage(AlreadyShown);
										}
									} catch (Exception e) {
										FileManager.pets.set(p.getUniqueId().toString() + ".Hide", false);
										PetUtils.spawnPet(p, p.getWorld());
										p.sendMessage(Show);
										p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
										p.playEffect(PetUtils.getPet(p).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
										SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
									}
								} else {
									p.sendMessage(NoPet);
								}
							} else {
								p.sendMessage(Usage);
							}
							break;
	
						// name
						case 2:
							if (args[0].equalsIgnoreCase("name")) {
								if (PetUtils.hasPet(p)) {
									FileManager.pets.set(p.getUniqueId().toString() + ".Name", args[1]);
	
									if (isHide == false) {
										if (p.hasPermission("virushd.pets.color") || p.hasPermission("*")) {
											PetUtils.getPet(p)
													.setCustomName(ChatColor.translateAlternateColorCodes('&', args[1]));
										} else {
											PetUtils.getPet(p).setCustomName(args[1]);
										}
									}
									p.sendMessage(Name);
									SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
								} else {
									p.sendMessage(NoPet);
								}
							} else {
								p.sendMessage(Usage);
							}
							break;
	
						// buy
						case 3:
							if (args[0].equalsIgnoreCase("buy")) {
								try {
									int id = Integer.parseInt(args[2]);
									String name;
									
									if (p.hasPermission("virushd.pets.color") || p.hasPermission("*")) {
										name = ChatColor.translateAlternateColorCodes('&', args[1]);
									} else {
										name = args[1];
									}
	
									if (PetUtils.hasPet(p)) {
										p.sendMessage(AlreadyAPet);
									} else {
										if (Coins.hasEnough(p.getUniqueId(), PetPrice)) {
											for (Pet pets : PetManager.pets) {
												if (pets.getId() == id) {
													Coins.remove(p.getUniqueId(), PetPrice);
													p.sendMessage(Buy);
													Utils.SpawnFirework(p.getLocation().add(0, -1, 0), Color.RED, Type.BALL_LARGE, 1);
													Utils.SpawnFirework(p.getLocation().add(1, -1, 1), Color.BLACK, Type.BALL_LARGE, 1);
													Utils.SpawnFirework(p.getLocation().add(-1, -1, -1), Color.BLUE, Type.BALL_LARGE, 1);
													PetUtils.buyPet(p, pets, name);
													return true;
												}
											}
											p.sendMessage(WrongID);
											p.sendMessage(IDs);
											for (Pet pets : PetManager.pets) {
												IDFormat = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.IDFormat"), p);
												IDFormat = IDFormat.replace("{ID}", "" + pets.getId());
												IDFormat = IDFormat.replace("{Name}", "" + "" + pets.getType());
												p.sendMessage(IDFormat);
											}
										} else {
											p.sendMessage(NotEnoughCoins);
										}
									}
								} catch (NumberFormatException ex) {
									p.sendMessage(NotANumber);
								}
							} else {
								p.sendMessage(Usage);
							}
							break;
	
						// default
						default:
							p.sendMessage(Usage);
							break;
						}
					}
				} else {
					p.sendMessage(NoPerm);
				}
			}
		}
		return false;
	}
}
