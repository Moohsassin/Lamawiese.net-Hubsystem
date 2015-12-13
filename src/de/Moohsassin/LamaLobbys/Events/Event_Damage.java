package de.Moohsassin.LamaLobbys.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Event_Damage implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		
		if(e.getCause() == DamageCause.VOID) {
			e.getEntity().teleport(e.getEntity().getWorld().getSpawnLocation());
		}
		e.setCancelled(true);
				
	}
	
}
