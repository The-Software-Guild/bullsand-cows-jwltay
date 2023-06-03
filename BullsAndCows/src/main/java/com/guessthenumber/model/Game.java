package com.guessthenumber.model;

import java.util.Objects;
import java.util.Random;

public class Game {
	private int id;
	private Boolean inProgress = true;
	private String answer = "";
	
	public Game() {}
	
	public Game(int id, Boolean inProgress, String answer) {
		super();
		this.id = id;
		this.inProgress = inProgress;
		this.answer = answer;
	}
	
	public Game(Game game) {
		this.id = game.getId();
		this.inProgress = game.inProgress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void generateAnswer() {
		Random random = new Random();
		
		
		while (this.answer.length() < 4) {
			int digit = random.nextInt(10);
			if (!this.answer.contains(String.valueOf(digit)))
				this.answer += digit;
		}
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public Boolean getInProgress() {
		return inProgress;
	}

	public void setInProgress(Boolean inProgress) {
		this.inProgress = inProgress;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", inProgress=" + inProgress + ", answer=" + answer + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(answer, id, inProgress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return Objects.equals(answer, other.answer) && id == other.id && Objects.equals(inProgress, other.inProgress);
	}
	

}
