package de.Moohsassin.LamaLobbys.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.Moohsassin.LamaLobbys.Items;
import de.Moohsassin.LamaLobbys.LamaLobbys;

public class Event_OpenCompass implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand() != null) {
				
				if(p.getItemInHand().equals(Items.COMPASS.stack)) {
					LamaLobbys.serverSelector.open(e.getPlayer());
				}
				
			}	
		}
	}
}
