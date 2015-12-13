package de.Moohsassin.LamaLobbys.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Event_FoodLevel implements Listener {

	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}
	
}