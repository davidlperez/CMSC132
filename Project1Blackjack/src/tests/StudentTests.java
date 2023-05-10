package tests;

import blackjack.*;
import deckOfCards.*;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

public class StudentTests {

	public BlackjackModel loadGame1() {
		Random random = new Random(6969);
		BlackjackModel game = new BlackjackModel();
		game.createAndShuffleDeck(random);
		game.initialPlayerCards(); // EIGHT of CLUBS, JACK of DIAMONDS
		game.initialDealerCards(); // KING of CLUBS, SEVEN of DIAMONDS
		return game;
	}

	public BlackjackModel loadGame2() {
		BlackjackModel game = new BlackjackModel();
		ArrayList<Card> playerCards = new ArrayList<>();
		ArrayList<Card> dealerCards = new ArrayList<>();

		playerCards.add(new Card(Rank.KING, Suit.DIAMONDS));
		playerCards.add(new Card(Rank.THREE, Suit.SPADES));
		playerCards.add(new Card(Rank.EIGHT, Suit.HEARTS));

		dealerCards.add(new Card(Rank.ACE, Suit.DIAMONDS));
		dealerCards.add(new Card(Rank.KING, Suit.CLUBS));

		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);

		return game;
	}

	@Test
	public void testPossibleHandValues1() {
		BlackjackModel game = loadGame1();
		assertTrue(BlackjackModel.possibleHandValues(game.getPlayerCards()).contains(18));
		assertTrue(BlackjackModel.possibleHandValues(game.getDealerCards()).contains(17));
	}

	@Test
	public void testHandAssess1() {
		BlackjackModel game = loadGame1();
		assertTrue(BlackjackModel.assessHand(game.getDealerCards()) == HandAssessment.NORMAL);
		assertTrue(BlackjackModel.assessHand(game.getPlayerCards()) == HandAssessment.NORMAL);
	}

	@Test
	public void testGameAssess1() {
		BlackjackModel game = loadGame1();
		game.playerTakeCard();
		assertTrue(game.gameAssessment() == GameResult.PLAYER_LOST);
	}

	@Test
	public void testGameAssess2() {
		BlackjackModel game = loadGame2();
		assertTrue(BlackjackModel.assessHand(game.getPlayerCards()) == HandAssessment.NORMAL);
		assertTrue(BlackjackModel.assessHand(game.getDealerCards()) == HandAssessment.NATURAL_BLACKJACK);
		assertTrue(game.gameAssessment() == GameResult.PUSH);
		assertTrue(BlackjackModel.possibleHandValues(game.getPlayerCards()).get(0) == 21);
	}
}