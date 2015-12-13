package de.Moohsassin.LamaLobbys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import de.Moohsassin.LamaLobbys.SignManager.Server;

public class ServerManager {

	public static MySQL sql;
	
	public static void openConnection() {
		try {
			sql = new MySQL("GeneralDatas");
			sql.openConnection();
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS Server (Name VARCHAR(40), Status INT, Current INT, Max INT, Map VARCHAR(50))");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Server> getAllServerFromGame(String game) {
		
		ArrayList<Server> server = new ArrayList<>();
		
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM Server WHERE Name LIKE '" + game + "%'");
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				server.add(new Server(rs.getString("Name"), rs.getInt("Current"), rs.getInt("Max"), rs.getString("Map"), GameStatus.fromInt(rs.getInt("Status"))));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sql.closeRessources(rs, st);
		}
		
		return server;
		
	}
	
	public static void closeConnection() {
		sql.closeConnection();
	}
	
	public enum GameStatus {
		LOBBY(1),
		WARMPUP(2),
		INGAME(3),
		DEATHMATCH(4),
		ENDING(5);
		
		public int value;
		
		private GameStatus(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public static GameStatus fromInt(int i) {
			for(GameStatus gs : GameStatus.values()) {
				if(gs.getValue() == i) return gs;
			}
			return null;
		}
	}
}
