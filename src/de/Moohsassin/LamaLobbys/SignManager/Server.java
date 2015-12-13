package de.Moohsassin.LamaLobbys.SignManager;

import de.Moohsassin.LamaLobbys.ServerManager.GameStatus;

public class Server {

	int online;
	int max;
	GameStatus gStatus;
	
	String name;
	String motd;
	
	public Server(String name, int online, int max, String motd, GameStatus gameStatus) {
		this.name = name;
		this.online = online;
		this.max = max;
		this.motd = motd;
		this.gStatus = gameStatus;
	}
}
