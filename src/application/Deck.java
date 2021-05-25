package application;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.Label;

public class Deck {
	
	public static void shuffle(List<Card> deck) {
		Collections.shuffle(deck);
	}

	public static LinkedList<Card> newDeck(boolean shuffle) {
		LinkedList<Card> res = new LinkedList<>();
		for (Suit s:Suit.values())
			for (Rank r:Rank.values())
				res.add(new Card(s,r));
		if (shuffle)
			shuffle(res);
		return res;
	}
}
