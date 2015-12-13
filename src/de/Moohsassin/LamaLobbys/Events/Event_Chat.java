package de.Moohsassin.LamaLobbys.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("deprecation")
public class Event_Chat implements Listener {

	@EventHandler
	public void onChat(PlayerChatEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		Team team = p.getScoreboard().getEntryTeam(p.getName());
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(!Event_Hider.hiddenEnabled.contains(players.getName())) {
				players.sendMessage(team.getPrefix() + p.getName() + " §8» §7" + e.getMessage());
			}
		}
	}
}
