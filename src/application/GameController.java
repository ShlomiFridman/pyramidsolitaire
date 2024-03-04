package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {
	
	private Stage stage;
	
    public final Pane board;
	
	public CardsRow[] rows;
	private ArrayList<Card> freed;
	private Card first;
	private DrawPane drawPane;
	private Clock clock;
	private int score,orgZ;
	private double x,y;
	private boolean drag;
	
	public GameController(Stage stage) {
		this.board = new Pane();
		this.stage = stage;
		drawPane = new DrawPane(board);
		clock = new Clock(drawPane.getClock());
		freed = new ArrayList<>();
		rows = new CardsRow[7];
		for (int i=0;i<rows.length;i++) {
			rows[i] = new CardsRow(board,i);
		}
		reset();
		board.setPrefSize(750, 450);
		board.getStyleClass().add("board");
		drawPane.getResetBtn().setOnMouseClicked(ev->{ if (ev.getClickCount()==2) reset();});
		stage.sizeToScene();
	}
	
	public void setCardEvent(Card card) {
//		card.getLabel().setOnMouseClicked(ev->{
//			if (!card.isFree() && card.getRow()!=-1) return;
////			if (first!=null)
////				System.out.printf("(%d && %b && %b && (%b || %b))\n",first.getFree()+card.getFree() , Math.abs(first.getRow()-card.getRow())==1 , Math.abs(card.getPos()-first.getPos())<2 , card.isReallyFree() , first.isReallyFree());
//			if (first==null && card.isReallyFree() && card.getRank().val==13) {
//				fade(card);
//				drawGrid.getInfo().setText("Score: "+ ++score);
//				return;
//			}
//			else if (first==null || !first.getLabel().getStyleClass().contains("selected")) {
//				first=card;
//				first.getLabel().getStyleClass().add("selected");
//			}
//			else if ( Rank.is13(first, card) && ((first.isReallyFree() && card.isReallyFree()) || (first.isFree()&card.isFree() && Math.abs(first.getRow()-card.getRow())==1 && Math.abs(card.getPos()-first.getPos())<2 && (card.isReallyFree() || first.isReallyFree())))) {
//				fade(card);
//				fade(first);
//				first.getLabel().getStyleClass().remove("selected");
//				first=null;
//				score+=2;
//				drawGrid.getInfo().setText("Score: "+score);
//			}
//			else {
//				first.getLabel().getStyleClass().remove("selected");
//				first=null;
//			}
//		});
		
		// initialize drag and move
		card.getLabel().setOnMousePressed(ev->{
//			System.out.println(card);
			if (!clock.running)
				clock.start();
			if (!card.isFree()) return;
			if (first==null && card.isReallyFree() && card.getRank().val==13) {
				fade(card);
				drawPane.getInfo().setText("Score: "+ ++score);
				return;
			}
			first=card;
			orgZ=board.getChildren().indexOf(card.getLabel());
//			System.out.printf("From %.2f|%.2f\n",orgX,orgY);
			x=ev.getX();
			y=ev.getY();
			card.getLabel().setMouseTransparent(true);
			card.getLabel().toFront();
			drag=true;
		});
		// for the overlapping
		card.getLabel().setOnDragDetected(ev->{
			if (!drag) return;
			card.getLabel().startFullDrag();
			});
		// moves the card
		card.getLabel().setOnMouseDragged(ev->{
			if (!drag) return;
			card.getLabel().setLayoutX(card.getLabel().getLayoutX()+ev.getX()-x);
			card.getLabel().setLayoutY(card.getLabel().getLayoutY()+ev.getY()-y);
		});
		card.getLabel().setOnMouseDragReleased(ev->{
			if (!drag || !(ev.getSource() instanceof Label)) return;
			Label l = (Label) ev.getSource();
			cardDown(l);
		});
		// done moving, return transparency
		card.getLabel().setOnMouseReleased(ev->{
			if (!drag) return;
			card.getLabel().setMouseTransparent(false);
			board.getChildren().remove(card.getLabel());
			board.getChildren().add(orgZ,card.getLabel());
			if (card.getRow()>=0)
				rows[card.getRow()].setCardLoc(card);
			else
				drawPane.setCardLocation(card);
			drag=false;
			first=null;
		});
	}
	
	private boolean cardDown(Label label) {
		Card card = getCard(label);
		if (card.getRow()<0 && card.getPos()==first.getPos()) return false;
		if ( Rank.is13(first, card) && ((first.isReallyFree() && card.isReallyFree()) || (first.isFree()&card.isFree() && Math.abs(first.getRow()-card.getRow())==1 && Math.abs(card.getPos()-first.getPos())<2 && (card.isReallyFree() || first.isReallyFree())))) {
			fade(card);
			fade(first);
			first.getLabel().getStyleClass().remove("selected");
			score+=2;
			drawPane.getInfo().setText("Score: "+score);
			return true;
		}
		return false;
	}
	
	private Card getCard(Label label) {
		int i=-1,j=-1;
		try {
			i = Integer.parseInt(label.getId().substring(0,label.getId().indexOf('|')));
			j = Integer.parseInt(label.getId().substring(label.getId().indexOf('|')+1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i<0 || j<0)
			return drawPane.getCard(i);
		return rows[i].getCards()[j];
	}
	
	public void fade(Card card) {
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.6));
		fadeIn.setNode(card.getLabel());
		fadeIn.setFromValue(1.0);
		fadeIn.setToValue(0.0);
		fadeIn.setCycleCount(1);
		fadeIn.setAutoReverse(false);
		fadeIn.setOnFinished(ev->{
			Card tmp=null;
			if (card.getRow()>=0)
				 tmp = rows[card.getRow()].remove(card);
			else
				tmp = drawPane.removeCard(card);
			if (tmp!=null)
				freed.add(tmp);
			if (rows[0].getRemains()==0)
				gameWon();
		});
		free(card);
		card.getLabel().setMouseTransparent(true);
		// if was in draw pane, remove it
		if (card.getRow() == -1)
			drawPane.removeCard(card);
		fadeIn.play();
	}
	
	public void free(Card c) {
		if (c.getRow()>0) {
			rows[c.getRow()-1].free(c.getPos());
		}
	}
	
	public void reset() {
		score=0;
		board.getChildren().clear();
		drawPane.reset();
//		board.getChildren().add(drawGrid.getGrid());
		clock.reset();
		List<Card> tmp;
		Iterator<Card> it = drawPane.getCards().iterator();
		while (it.hasNext())
			setCardEvent(it.next());
		for (int i=0;i<rows.length;i++) {
			tmp = drawPane.draw(i+1);
			rows[i].setCards(tmp);
//			rows[i].getPane().setLayoutY(i*40);
//			board.getChildren().add(rows[i].getPane());
		}
//		System.out.println(board.getChildren());
	}
	
	public void gameWon() {
		if (drawPane.getClock().getText().indexOf("You Win")==-1)
			drawPane.getInfo().setText(drawPane.getInfo().getText()+"\nYou Win");
		drawPane.setTransparent(true);
		clock.pause();
	}

    public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
