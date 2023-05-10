package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;
/**
 * This class provides the mechanics behind the BlackJack game created 
 * by the BlackJackGUI Class.
 * @author David Perez
 *
 */
public class BlackjackModel {

	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	private Deck deck;
/**
 * Getter for the dealer's cards
 * @return copy of the dealer's current cards
 */
	public ArrayList<Card> getDealerCards() {
		return new ArrayList<>(dealerCards);
	}
/**
 * Getter for the player's cards
 * @return copy of the player's current cards
 */
	public ArrayList<Card> getPlayerCards() {
		return new ArrayList<>(playerCards);
	}
/**
 * Manually sets the dealer's cards
 * @param ArrayList of cards
 */
	public void setDealerCards(ArrayList<Card> cards) {
		dealerCards = new ArrayList<>(cards);
	}
/**
 * Manually sets the player's cards
 * @param ArrayList of cards
 */
	public void setPlayerCards(ArrayList<Card> cards) {
		playerCards = new ArrayList<>(cards);
	}
/**
 * Makes a new deck and randomly shuffles it
 * @param seed for random generator
 */
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}
/**
 * Makes a new list of cards for the dealer and adds the top 
 * 2 cards of the deck to it.
 */
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		dealerCards.add(deck.dealOneCard());
		dealerCards.add(deck.dealOneCard());
	}
/**
 * Makes a new list of cards for the player and adds the top
 * 2 cards of the deck to it.
 */
	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		playerCards.add(deck.dealOneCard());
		playerCards.add(deck.dealOneCard());
	}
/**
 * Adds the top card of the deck to the player's cards ("Hitting")
 */
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}
/**
 * Adds the top card of the deck to the dealer's cards ("Hitting")
 */
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}
/**
 * Takes the hand that is passed as a parameter and creates an ArrayList of
 * possible values the cards can add to
 * Number cards: face value
 * Face cards: 10
 * Ace: 1 or 11
 * @param cards in the player's/dealer's hand
 * @return Integer ArrayList with the possible hand values 
 * (either of size 1 or 2)
 */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand) {
		ArrayList<Integer> value = new ArrayList<>();
		int sum1 = 0, sum2 = 0;

		for (Card c : hand) {
			sum1 += c.getRank().getValue();
			sum2 += c.getRank().getValue();
			if (c.getRank() == Rank.ACE && sum2 < 12) {
				sum2 += 10;
			}
		}
		if (sum1 == sum2) {
			value.add(sum1);
		} else if (sum1 != sum2 && sum2 <= 21) {
			value.add(sum1);
			value.add(sum2);
		} else if (sum1 != sum2 && sum1 <= 21 && sum2 > 21) {
			value.add(sum1);
		} else if (sum1 != sum2 && sum1 > 21 && sum2 > 21) {
			value.add(sum1);
		}
		return value;
	}
/**
 * Determines the type of hand based on the values of the cards
 * @param hand of cards
 * @return HandAssessment
 */
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if (hand == null || hand.size() < 2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		} else if (hand.size() == 2 && possibleHandValues(hand).contains(21)) {
			return HandAssessment.NATURAL_BLACKJACK;
		} else if (possibleHandValues(hand).get(0) > 21) {
			return HandAssessment.BUST;
		} else {
			return HandAssessment.NORMAL;
		}
	}
/**
 * Compares the HandAssessment of the player's cards and the dealer's cards 
 * to determine win/loss and how much money is paid out
 * @return GameResult
 */
	public GameResult gameAssessment() {
		int dHigh = 0, pHigh = 0;
		for (Integer value : possibleHandValues(getDealerCards())) {
			dHigh = value > dHigh ? value : dHigh;
		}
		for (Integer value : possibleHandValues(getPlayerCards())) {
			pHigh = value > pHigh ? value : pHigh;
		}
		if (assessHand(getPlayerCards()) == HandAssessment.NATURAL_BLACKJACK
				&& assessHand(getDealerCards()) != HandAssessment.NATURAL_BLACKJACK) {
			return GameResult.NATURAL_BLACKJACK;
		} else if (assessHand(getPlayerCards()) == HandAssessment.NATURAL_BLACKJACK
				&& assessHand(getDealerCards()) == HandAssessment.NATURAL_BLACKJACK) {
			return GameResult.PUSH;
		} else if (assessHand(getPlayerCards()) != HandAssessment.NATURAL_BLACKJACK) {
			if (assessHand(getPlayerCards()) == HandAssessment.BUST) {
				return GameResult.PLAYER_LOST;
			} else if (assessHand(getPlayerCards()) != HandAssessment.BUST
					&& assessHand(getDealerCards()) == HandAssessment.BUST) {
				return GameResult.PLAYER_WON;
			} else if (assessHand(getPlayerCards()) != HandAssessment.BUST
					&& assessHand(getDealerCards()) != HandAssessment.BUST) {
				if (pHigh > dHigh) {
					return GameResult.PLAYER_WON;
				} else if (pHigh < dHigh) {
					return GameResult.PLAYER_LOST;
				} else if (pHigh == dHigh) {
					return GameResult.PUSH;
				}
			} else if (assessHand(getDealerCards()) == HandAssessment.NATURAL_BLACKJACK && pHigh == 21) {
				return GameResult.PUSH;
			}
		}
		return null;
	}
/**
 * Evaluates the dealer's hand to determine whether or not the dealer 
 * should take another card ("hit")
 * Takes a card if the value of the hand <= 17
 * No if greater than 17
 * @return true or false
 */
	public boolean dealerShouldTakeCard() {
		int dHigh = 0;
		for (Integer value : possibleHandValues(getDealerCards())) {
			dHigh = value > dHigh ? value : dHigh;
		}
		if (assessHand(getDealerCards()) == HandAssessment.NORMAL) {
			if (dHigh < 17) {
				return true;
			} else if (dHigh > 17) {
				return false;
			} else if (possibleHandValues(getDealerCards()).size() == 2
					&& dHigh == 17) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}
}
