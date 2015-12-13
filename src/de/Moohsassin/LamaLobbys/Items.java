package de.Moohsassin.LamaLobbys;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {

	HIDER_ENABLE(Methods.getItem(Material.INK_SACK, 1, 10, "§7Spieler: §eSichtbar")),
	HIDER_DISABLE(Methods.getItem(Material.INK_SACK, 1, 8, "§7Spieler: §eUnsichtbar")),
	COMPASS(Methods.getItem(Material.COMPASS, 1, 0, "§7Wähle ein Spiel"));
	
	public ItemStack stack;
	
	Items(ItemStack stack) {
		this.stack = stack;
	}
	
}
