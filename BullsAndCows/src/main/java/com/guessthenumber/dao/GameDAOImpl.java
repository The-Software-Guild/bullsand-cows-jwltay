package com.guessthenumber.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.guessthenumber.model.Game;
import com.guessthenumber.model.Round;

@Repository
public class GameDAOImpl implements GameDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Game> getGames() {
		return jdbcTemplate.query(
				"SELECT * FROM games",
				BeanPropertyRowMapper.newInstance(Game.class)
				);
	}
	
	@Override
	public Game getGame(int id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM games WHERE id = ?", 
				BeanPropertyRowMapper.newInstance(Game.class),
				id
				);
	}
	
	@Override
	public void insertGame(Game game) {
		jdbcTemplate.update(
				"INSERT INTO games (answer) VALUES (?)",
				game.getAnswer()
				);
	}
	
	@Override
	public void updateGame(Game game) {
		jdbcTemplate.update(
				"UPDATE games SET inProgress = false WHERE id = ?",
				game.getId()
				);		
	}
	
	@Override
	public void insertRound(Round round) {
		jdbcTemplate.update(
				"INSERT INTO rounds (gameId, guess, result) VALUES (?, ?, ?)",
				round.getGameId(),
				round.getGuess(),
				round.getResult()
				);	
	}
	
	@Override
	public List<Round> getRounds(int id) {
		return jdbcTemplate.query(
				"SELECT * FROM rounds WHERE gameId = ?",
				BeanPropertyRowMapper.newInstance(Round.class),
				id
				);
	}
	
	@Override
	public Game getRecentGame() {
		Game recentGame = jdbcTemplate.queryForObject(
				"SELECT * FROM games ORDER BY id DESC LIMIT 1", 
				BeanPropertyRowMapper.newInstance(Game.class)
				);
		return recentGame;
		}
 
}
