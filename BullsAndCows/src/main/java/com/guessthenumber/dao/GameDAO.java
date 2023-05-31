package com.guessthenumber.dao;

import java.util.List;

import com.guessthenumber.model.Game;
import com.guessthenumber.model.Round;

public interface GameDAO {
	List<Game> getGames();
	Game getGame(int id);
	void insertGame(Game game);
	void updateGame(Game game);
	void insertRound(Round round);
	List<Round> getRounds(int id);
	Game getRecentGame();
	
}
