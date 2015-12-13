package de.Moohsassin.LamaLobbys.Events;

import java.io.File;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.Moohsassin.LamaLobbys.Methods;
import net.md_5.bungee.api.ChatColor;

public class Event_InvClick implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("/minecraft", "hub-compass.yml"));
		
		if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', cfg.getString("Inv_Name")))) {
			
			int slot = e.getSlot() + 1;
			String server = cfg.getString("Slot." + slot + ".Server");

			e.setCancelled(true);
			
			if(server != null) {
				Methods.sendToServer((Player) e.getWhoClicked(), server);
			}
			
		}

		if(e.getWhoClicked().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
		
	}
	
}
