package de.Moohsassin.LamaLobbys.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.Moohsassin.LamaLobbys.PlayerDatas;

public class Event_FullJoin implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		
		Player p = e.getPlayer();
		
		if(Bukkit.getOnlinePlayers().size() >= 200) {
			
			String rank = PlayerDatas.getRank(p.getName());
			if(rank == null | rank.equalsIgnoreCase("User")) {
				e.disallow(Result.KICK_FULL, "§cDie Lamawiese ist leider voll!");
			}
		}
	}
}
 