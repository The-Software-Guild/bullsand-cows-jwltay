package com.guessthenumber.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.guessthenumber.model.Game;
import com.guessthenumber.model.Round;



@JdbcTest
class GameDAOImplTest {
	
	private GameDAOImpl dao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		dao = new GameDAOImpl();
		dao.setJdbcTemplate(jdbcTemplate);
	}
	
	@Test
	void testGetGames() {
		jdbcTemplate.update(
				"INSERT INTO games  VALUES (?, ?, ?)",
				1,
				true,
				"1234"
				);
		
		Game game = new Game(1, true, "1234");
		
		assertTrue(dao.getGames().contains(game));
	}
	
	@Test
	void testGetGame() {
		jdbcTemplate.update(
				"INSERT INTO games  VALUES (?, ?, ?)",
				1,
				true,
				"1234"
				);
		
		Game game = new Game(1, true, "1234");

		assertEquals(dao.getGame(1), game);
	}
	
	@Test
	void testInsertGame() {
		Game game = new Game(1, true, "5678");
		dao.insertGame(game);
		
		assertEquals(dao.getGame(1), game);
	}
	
	@Test
	void testUpdateGame() {
		jdbcTemplate.update(
				"INSERT INTO games  VALUES (?, ?, ?)",
				1,
				true,
				"1234"
				);
		
		Game game = dao.getGame(1);
		dao.updateGame(game);
		
		assertFalse(dao.getGame(1).getInProgress());
	}
	
	@Test
	void testInsertRound() {
		jdbcTemplate.update(
				"INSERT INTO games  VALUES (?, ?, ?)",
				1,
				true,
				"1234"
				);
		
		Round round = new Round(1, "4860", LocalDateTime.now(), "e:2p:1");
		dao.insertRound(round);
		
		Round result = jdbcTemplate.queryForObject(
				"SELECT guess FROM rounds WHERE gameId = 1",
				BeanPropertyRowMapper.newInstance(Round.class)
				);
		
		assertEquals(result.getGuess(), round.getGuess());
		
	}
	
	@Test
	void testGetRounds() {
		jdbcTemplate.update(
				"INSERT INTO games  VALUES (?, ?, ?)",
				1,
				true,
				"1234"
				);
		
		jdbcTemplate.update(
				"INSERT INTO rounds (gameId, guess, result)  VALUES (?, ?, ?)",
				1,
				"4860",
				"e:2p:1"
				);
		jdbcTemplate.update(
				"INSERT INTO rounds (gameId, guess, result)  VALUES (?, ?, ?)",
				1,
				"3967",
				"e:0p:1"
				);

		assertEquals(dao.getRounds(1).size(), 2);
		
	}
	
	@Test
	void testGetRecentGame() {
		Game game = new Game(1, true, "2394");
		dao.insertGame(game);
		
		assertEquals(dao.getRecentGame().getAnswer(), game.getAnswer());
	}

}
