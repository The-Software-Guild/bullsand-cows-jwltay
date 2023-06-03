package com.guessthenumber.service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guessthenumber.dao.GameDAOImpl;
import com.guessthenumber.model.Game;
import com.guessthenumber.model.Round;

@Service
public class GameService {
	
	@Autowired
	GameDAOImpl daoImpl;
	
	public List<Game> retrieveGames() {
		return daoImpl.getGames().stream()
				// returns a game with no answer if game is in progress
				.map(game -> game.getInProgress() == true ? new Game(game) : game)
				.collect(Collectors.toList());
	}
	
	public Game retrieveGameById(int id) {
		Game game = daoImpl.getGame(id);
		return game.getInProgress() == true ? new Game(game) : game;
	}
	
	public Game startGame() {
		Game game = new Game();
		game.generateAnswer();
		System.out.println(game.getAnswer());
		daoImpl.insertGame(game);
		return daoImpl.getRecentGame();
	}
	
	public String runRound(HashMap<String, String> idAndGuess) {
		Map<Character, Integer> result = new HashMap<>();
		result.put('e', 0);
		result.put('p', 0);
		
		int id = Integer.valueOf(idAndGuess.entrySet().stream().findFirst().get().getKey());
		Game game = daoImpl.getGame(id);
		String guess = idAndGuess.get(String.valueOf(id));
		String answer = game.getAnswer();
		
		// find exact and partial matches
		for (int i = 0; i < guess.length(); i++) {
			if (answer.charAt(i) == guess.charAt(i)) 
				result.put('e', result.get('e') + 1);
			else if (answer.contains(String.valueOf(guess.charAt(i))))
				result.put('p', result.get('p') + 1);
		}
		
		// set game status to ended
		if (result.get('e') == 4) {
			game.setInProgress(false);
			daoImpl.updateGame(game);
		}
		
		// save round
		String resultString = "e:" + result.get('e') + "p:" + result.get('p');
		Round round = new Round(id, guess, LocalDateTime.now(), resultString);
		daoImpl.insertRound(round);
		
		return resultString;
	}
	
	public List<Round> retrieveRounds(int id) {
		return daoImpl.getRounds(id);
	}
	
}
