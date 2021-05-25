package application;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.geometry.HPos;
//import javafx.geometry.Pos;
//import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
//import javafx.scene.layout.RowConstraints;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class DrawPane {
	
	private LinkedList<Card> draw,deck;
//	private StackPane deckStack,drawStack;
//	private GridPane grid;
//	private VBox infoVBox;
	private Button drawBtn,resetBtn;
	private Label info,clockLabel;
	private Pane pane;
	private VBox bgBoard;
	
	public DrawPane(Pane pane) {
		this.pane=pane;
//		deckStack = new StackPane();
//		drawStack = new StackPane();
//		infoVBox = new VBox();
		draw = new LinkedList<>();
		deck = new LinkedList<>();
		drawBtn = new Button("Draw");
		resetBtn = new Button("Reset");
		info = new Label();
		resetBtn.getStyleClass().add("resetBtn");
		drawBtn.getStyleClass().add("drawBtn");
		info.setTextAlignment(TextAlignment.CENTER);
		info.setWrapText(false);
		clockLabel = new Label();
//		infoVBox.setAlignment(Pos.CENTER);
//		infoVBox.getChildren().addAll(clockLabel,drawBtn,resetBtn,info);
//		deckStack.setPrefSize(79, 120);
//		GridPane.setMargin(infoVBox, new Insets(5));
//		Iterator<Node> it = infoVBox.getChildren().iterator();
//		while (it.hasNext())
//			VBox.setMargin(it.next(), new Insets(2));
		resetBtn.setOnMousePressed(ev->resetBtn.getStyleClass().add("resetBtnClicked"));
		resetBtn.setOnMouseReleased(ev->resetBtn.getStyleClass().remove("resetBtnClicked"));
		drawBtn.setOnMousePressed(ev->drawBtn.getStyleClass().add("drawBtnClicked"));
		drawBtn.setOnMouseReleased(ev->drawBtn.getStyleClass().remove("drawBtnClicked"));
		resetBtn.setOnMouseEntered(ev->resetBtn.getStyleClass().add("resetBtnHover"));
		resetBtn.setOnMouseExited(ev->resetBtn.getStyleClass().remove("resetBtnHover"));
		drawBtn.setOnMouseEntered(ev->drawBtn.getStyleClass().add("drawBtnHover"));
		drawBtn.setOnMouseExited(ev->drawBtn.getStyleClass().remove("drawBtnHover"));
		setupBoard();
		reset();
		eventSetup();
	}

	private void setupBoard() {
//		grid = new GridPane();
//		grid.setLayoutX(0);
//		grid.setLayoutY(0);
//		grid.setPadding(new Insets(5));
//		GridPane.setHalignment(drawStack,HPos.CENTER);
//		GridPane.setHalignment(deckStack,HPos.CENTER);
//		GridPane.setHalignment(infoVBox,HPos.CENTER);
//		GridPane.setValignment(infoVBox,VPos.CENTER);
//		grid.add(deckStack,0,0);
//		grid.add(drawStack,2,0);
//		grid.add(infoVBox,1,0);
//		ColumnConstraints col = new ColumnConstraints();
//		RowConstraints row = new RowConstraints();
//		col.setPrefWidth(84);
//		row.setPrefHeight(124);
//		grid.getRowConstraints().add(row);
//		grid.getColumnConstraints().addAll(col,col,col);
		bgBoard = new VBox();
		bgBoard.setMinSize(270, 130);
		bgBoard.getStyleClass().add("drawPane");
		bgBoard.setLayoutX(0);
		bgBoard.setLayoutY(0);
		bgBoard.setAlignment(Pos.CENTER);
		VBox.setMargin(this.clockLabel, new Insets(2));
		VBox.setMargin(this.drawBtn, new Insets(2));
		VBox.setMargin(this.resetBtn, new Insets(2));
		VBox.setMargin(this.info, new Insets(2));
		bgBoard.getChildren().addAll(this.clockLabel,this.drawBtn,this.resetBtn,this.info);
	}
	
	private void eventSetup() {
		drawBtn.setOnMouseClicked(ev->{
			if (!deck.isEmpty() && deck.peekLast().getLabel().getStyleClass().contains("selected"))
				deck.peekLast().getLabel().getStyleClass().remove("selected");
			if (!draw.isEmpty() && draw.peekLast().getLabel().getStyleClass().contains("selected"))
				draw.peekLast().getLabel().getStyleClass().remove("selected");
			drawCard();
		});
	}
	
	private void reShuffle() {
		if (!draw.isEmpty())
			deck.addAll(draw.subList(0, draw.size()));
		draw.clear();
		Collections.shuffle(deck);
		Iterator<Card> it = deck.iterator();
		Card tmp;
		while (it.hasNext()) {
			tmp = it.next();
			tmp.getLabel().setLayoutX(16);
			tmp.getLabel().setLayoutY(5);
			tmp.getLabel().toFront();
			tmp.setLocation(-1, -1);
		}
//		drawStack.getChildren().clear();
	}
	
	private void drawCard() {
		if (isEmpty()) return;
		if (deck.isEmpty())
			reShuffle();
		draw.addLast(deck.removeLast());
//		deckStack.getChildren().remove(draw.peekLast().getLabel());
//		drawStack.getChildren().add(draw.peekLast().getLabel());
		Card card = draw.peekLast();
		card.getLabel().setLayoutX(175);
		card.getLabel().toFront();
		card.setLocation(-2, -2);
	}
	
	public boolean isEmpty() {
		return (deck.isEmpty() && draw.isEmpty());
	}

	public List<Card> draw(int num){
		LinkedList<Card> list = new LinkedList<>();
		while (num-->0 && !deck.isEmpty())
			list.add(deck.removeLast());
		return list;
	}
	
	public Card removeCard(Card card) {
		Card res=card;
		if (deck.contains(card)) {
			deck.remove(card);
//			deckStack.getChildren().remove(card.getLabel());
		}
		else {
			draw.remove(card);
//			drawStack.getChildren().remove(card.getLabel());
		}
		return res;
	}
	
	public void setCardLocation(Card card) {
		if (deck.contains(card)) {
			card.getLabel().setLayoutX(16);
		}
		else {
			card.getLabel().setLayoutX(175);
		}
		card.getLabel().setLayoutY(5);
	}
	
	public void reset() {
		deck.clear();
		draw.clear();
//		deckStack.getChildren().clear();
//		drawStack.getChildren().clear();
//		getDeckStack().setMouseTransparent(false);
//		getDrawStack().setMouseTransparent(false);
//		getDrawBtn().setMouseTransparent(false);
		deck = Deck.newDeck(false);
		reShuffle();
		Iterator<Card> it = deck.iterator();
		while (it.hasNext())
			pane.getChildren().add(it.next().getLabel());
//		Iterator<Card> it = deck.iterator();
		info.setText("Good Luck");
		clockLabel.setText("00:00");
//		while (it.hasNext())
//			deckStack.getChildren().add(it.next().getLabel());
		this.setTransparent(false);
		pane.getChildren().add(bgBoard);
		bgBoard.toBack();
	}
	
	public List<Card> getCards(){
		List<Card> res = new LinkedList<>();
		res.addAll(deck);
		res.addAll(draw);
		return res;
	}
	
	public void setTransparent(boolean flag) {
		this.drawBtn.setMouseTransparent(flag);
//		this.resetBtn.setMouseTransparent(flag);
	}
	
	public Card getCard(int ind) {
		if (ind==-1) return deck.peekLast();
		return draw.peekLast();
	}

	public LinkedList<Card> getDraw() {
		return draw;
	}

	public void setDraw(LinkedList<Card> draw) {
		this.draw = draw;
	}

	public LinkedList<Card> getDeck() {
		return deck;
	}

	public void setDeck(LinkedList<Card> deck) {
		this.deck = deck;
	}

//	public StackPane getDeckStack() {
//		return deckStack;
//	}
//
//	public void setDeckStack(StackPane deckStack) {
//		this.deckStack = deckStack;
//	}
//
//	public StackPane getDrawStack() {
//		return drawStack;
//	}
//
//	public void setDrawStack(StackPane drawStack) {
//		this.drawStack = drawStack;
//	}
//
//	public GridPane getGrid() {
//		return grid;
//	}
//
//	public void setGrid(GridPane grid) {
//		this.grid = grid;
//	}

	public Button getDrawBtn() {
		return drawBtn;
	}

	public void setDrawBtn(Button drawBtn) {
		this.drawBtn = drawBtn;
	}

	public Button getResetBtn() {
		return resetBtn;
	}

	public void setResetBtn(Button resetBtn) {
		this.resetBtn = resetBtn;
	}

	public Label getInfo() {
		return info;
	}

	public void setInfo(Label info) {
		this.info = info;
	}

	public Label getClock() {
		return clockLabel;
	}

	public void setClock(Label clock) {
		this.clockLabel = clock;
	}

}
