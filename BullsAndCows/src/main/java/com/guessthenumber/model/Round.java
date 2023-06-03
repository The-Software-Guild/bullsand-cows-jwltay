package com.guessthenumber.model;

import java.time.LocalDateTime;

public class Round {
	private int gameId;
	private String guess;
	private LocalDateTime guessTime;
	private String result;
	
	public Round() {}

	public Round(int gameId,String guess, LocalDateTime guessTime, String result) {
		super();
		this.gameId = gameId;
		this.guess = guess;
		this.guessTime = guessTime;
		this.result = result;
	}
	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LocalDateTime getGuessTime() {
		return guessTime;
	}

	public void setGuessTime(LocalDateTime guessTime) {
		this.guessTime = guessTime;
	}

	@Override
	public String toString() {
		return "Round [guess=" + guess + "]";
	}
}
