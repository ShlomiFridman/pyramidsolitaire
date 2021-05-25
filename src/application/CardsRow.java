package application;

import java.util.Iterator;
import java.util.List;

import javafx.scene.layout.Pane;

public class CardsRow {
	
	private Card[] cards;
	private int row,remains;
	private Pane pane;

	public CardsRow(Pane pane,int row) {
		setPane(pane);
		setRow(row);
	}
	
	public Card remove(Card c) {
		if (c==null || c.getRow()!=this.row || cards[c.getPos()]==null)
			return null;
		pane.getChildren().remove(c.getLabel());
		cards[c.getPos()]=null;
		remains--;
		return c;
	}
	
	public void free(int pos) {
		if (pos<cards.length)
			cards[pos].freeOne();
		if (pos>0)
			cards[pos-1].freeOne();
	}

	public Card[] getCards() {
		return cards;
	}

	public void setCards(List<Card> list) {
		setRemains(list.size());
		this.cards = new Card[list.size()];
		Iterator<Card> it = list.iterator();
		int k=0;
		while (it.hasNext()) {
			cards[k]=it.next();
			cards[k].setLocation(row, k);
			cards[k].getLabel().setLayoutX((330-48*row)+k*100);
			cards[k].getLabel().setLayoutY(70+40*row);
			cards[k].getLabel().toFront();
//			cards[i].getLabel().setLayoutX(339.5-(remains-i)*78-39);
//			pane.getChildren().add(cards[k++].getLabel());
			k++;
		}
	}
	
	public void setCardLoc(Card card) {
		card.getLabel().setLayoutX((330-48*row)+card.getPos()*100);
		card.getLabel().setLayoutY(70+40*row);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int num) {
		this.row = num;
	}

	public int getRemains() {
		return remains;
	}

	public void setRemains(int remain) {
		this.remains = remain;
	}
	
	public void setPane(Pane pane) {
		this.pane=pane;
	}

	public Pane getPane() {
		return pane;
	}

}
