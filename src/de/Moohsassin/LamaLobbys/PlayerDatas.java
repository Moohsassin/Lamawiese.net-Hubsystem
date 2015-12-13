package de.Moohsassin.LamaLobbys;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerDatas {

	public static MySQL sql;
	public static MySQL pex;
	
	public static void openConnection() {

		try {
			pex = new MySQL("LamaPex");
			pex.openConnection();
			sql = new MySQL("Players");
			sql.openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection() {
		pex.closeConnection();
		sql.closeConnection();
	}
	
	public static int getCoins(String name) {
		return sql.getInt("Lamas", "Name", name, "Coins");
	}
	
	public static int getLevel(String name) {
		return sql.getInt("Lamas", "Name", name, "Lamalvl");
	}
	
	public static int getXP(String name) {
		return sql.getInt("Lamas", "Name", name, "Lamaxp");
	}
	
	public static void addPoints(Player p, int coins, int xp) {
		
		String name = p.getName();
		
		coins += getCoins(name); xp += getXP(name); int level = getLevel(name);
		if(xp >= 5000) { 
			level ++; xp -= 5000;
		}
		
		sql.queryUpdate("DELETE FROM Lamas WHERE Uuid='" + p.getUniqueId() + "'");
		sql.queryUpdate("INSERT INTO Lamas (Uuid, Name, Rank, Coins, Lamaxp, Lamalvl) VALUES ('" + p.getUniqueId() + "','" + p.getName() + "','" + getPexGroup(p.getUniqueId()) + "','" + coins + "','" + xp + "','" + level + "')");
		
	}
	
	public static boolean hasPlayerBefore(String name) {
		return getRank(name) != null;
	}
	
	public static void upDatePlayer(Player p) {
		
		int coins = getCoins(p.getName()), lxp = getXP(p.getName()), lvl = getLevel(p.getName());
		
		if(!hasPlayerBefore(p.getName())) {
			sql.queryUpdate("DELETE FROM Lamas WHERE Uuid='" + p.getUniqueId() + "'");
			sql.queryUpdate("INSERT INTO Lamas (Uuid, Name, Rank, Coins, Lamaxp, Lamalvl) VALUES ('" + p.getUniqueId() + "','" + p.getName() + "','" + getPexGroup(p.getUniqueId()) + "','0','0','0')");
		} else {
			sql.queryUpdate("DELETE FROM Lamas WHERE Uuid='" + p.getUniqueId() + "'");
			sql.queryUpdate("INSERT INTO Lamas (Uuid, Name, Rank, Coins, Lamaxp, Lamalvl) VALUES ('" + p.getUniqueId() + "','" + p.getName() + "','" + getPexGroup(p.getUniqueId()) + "','" + coins + "','" + lxp + "','" + lvl + "')");
		}
	}
	
	public static String getRank(String name) {
		return sql.getString("Lamas", "Name", name, "Rank");	
	}

	public static String getName(UUID UUID) {
		return sql.getString("Lamas", "Uuid", UUID, "Name");
	}
	
	public static String getUUID(String name) {
		return sql.getString("Lamas", "Name", name, "Uuid");
	}
	
	public static int getRankValueForTab(String rank) {
		return (9 - getRankValue(rank));
	}
	
	public static int getRankValue(String rank) {
		if(rank.equalsIgnoreCase("Owner")) return 8;
		if(rank.equalsIgnoreCase("Admin")) return 7;
		if(rank.contains("Dev")) return 6;
		if(rank.contains("Mod")) return 5;
		if(rank.equalsIgnoreCase("Vip")) return 4;
		if(rank.equalsIgnoreCase("Premium")) return 3;
		if(rank.equalsIgnoreCase("BauTeam")) return 2;
		return 1;
	}
	
	public static String getPexGroup(UUID u) {
		String rank = pex.getString("PlayerPerms", "Permission LIKE 'GROUP_%' AND Uuid", u, "Permission");
		if(rank == null) return "User";
		return rank.split("_")[1];
	}
	
	public static String getChatColor(UUID u) {
		String group = getPexGroup(u);
		if(group.equalsIgnoreCase("User")) return "§9";
		return pex.getString("Groups", "Permission LIKE 'COLOR_%' AND Name", group, "Permission").split("_")[1];
	}
	
}
