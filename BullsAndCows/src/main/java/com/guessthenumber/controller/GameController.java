package com.guessthenumber.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guessthenumber.util.ResponseHandler;
import com.guessthenumber.model.Game;
import com.guessthenumber.model.Round;
import com.guessthenumber.service.GameService;

@RestController
public class GameController {
	
	@Autowired
	GameService gameService;
	
	@GetMapping("/game")
	public ResponseEntity<Object> findAllGames() {
		try {
			List<Game> games =  gameService.retrieveGames();			
			return ResponseHandler.generateResponse("Games retrieved.", HttpStatus.OK, games);
		} catch (Exception e){
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@GetMapping("/game/{gameId}")
	public ResponseEntity<Object> getGameById(@PathVariable("gameId") int id) {
		try {
			Game game =  gameService.retrieveGameById(id);			
			return ResponseHandler.generateResponse("Game retrieved.", HttpStatus.OK, game);
		} catch (Exception e){
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@PostMapping("/begin")
	public ResponseEntity<Object> beginGame() {
		try {
			Game newGame = gameService.startGame();
			String message = "Game started! ID: " + newGame.getId();
			return ResponseHandler.generateResponse(message, HttpStatus.CREATED, newGame.getInProgress());
		} catch (Exception e){
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@PostMapping("/guess")
	public ResponseEntity<Object> guessNumber(@RequestBody HashMap<String, String> idAndGuess) {

		try {
			gameService.runRound(idAndGuess);			
			String message = "This round's result: " + gameService.runRound(idAndGuess);
			return ResponseHandler.generateResponse(message, HttpStatus.CREATED, idAndGuess);
		} catch (Exception e){
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@GetMapping("/rounds/{gameId}")
	public ResponseEntity<Object> getRoundsByGameId(@PathVariable("gameId") int id) {
		try {
			List<Round> rounds =  gameService.retrieveRounds(id);			
			return ResponseHandler.generateResponse("Games retrieved.", HttpStatus.OK, rounds);
		} catch (Exception e){
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

}
