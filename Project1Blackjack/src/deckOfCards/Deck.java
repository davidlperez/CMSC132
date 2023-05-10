 package deckOfCards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This creates and fills a group of 52 playing cards into a single deck.
 * 
 * @author David Perez
 */
public class Deck {

	private ArrayList<Card> deckCards;

	/**
	 * Constructs a new Deck and fills it with 52 playing cards in 
	 * order from Ace-King Spades, Clubs, Diamonds, Hearts
	 */
	public Deck() {
		deckCards = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				deckCards.add(new Card(rank, suit));
			}
		}
	}
/**
 * Uses the .shuffle method of the Collections class to randomize the 
 * order of the playing cards
 * @param seed for randomNumberGenerator
 */
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(deckCards, randomNumberGenerator);
	}
/**
 * Deals the top card of the deck
 * @return top card of Deck
 */
	public Card dealOneCard() {
		Card dealt = deckCards.get(0);
		deckCards.remove(0);
		return dealt;
	}
}
