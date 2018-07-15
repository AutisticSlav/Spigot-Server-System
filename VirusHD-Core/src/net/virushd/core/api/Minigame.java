package net.virushd.core.api;

import net.virushd.core.main.CoreMain;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Minigame extends JavaPlugin {

	// the type (Normal or Arena)
	private MinigameType type;

	// constructor (not public so you can only create ArenaMinigame or NormalMinigame)
	Minigame(MinigameType type) {
		super();
		this.type = type;
	}

	// methods that core uses
	public abstract void leave(Player p);

	public abstract String normalPlaceholder(String s);

	public abstract ItemStack getDefaultItem();

	public abstract int getDefaultSlot();

	public abstract Location getDefaultLocation();

	// getName returns VirusHD-{MinigameName} and getRealName only returns {MinigameName}
	public String getRealName() {
		return getName().replaceFirst("VirusHD-", "");
	}

	// get the type to cast it to ArenaMinigame or NormalMinigame
	public MinigameType getType() {
		return type;
	}

	// register the minigame in the core plugin
	public final void register() {
		CoreMain.registerMinigame(this);
	}

	// the minigame types
	public enum MinigameType {
		NORMAL, ARENA
	}

	// minigames with multiple arenas
	public static abstract class ArenaMinigame extends Minigame {

		// constructor
		public ArenaMinigame() {
			super(MinigameType.ARENA);
		}

		// methods that require an arena id
		public abstract void join(Player p, int id);

		public abstract String signPlaceholder(String s, int id);
	}

	// normal minigames
	public static abstract class NormalMinigame extends Minigame {

		// constructor
		public NormalMinigame() {
			super(MinigameType.NORMAL);
		}

		// methods that don't require an arena id
		public abstract void join(Player p);

		public abstract String signPlaceholder(String s);
	}
}
