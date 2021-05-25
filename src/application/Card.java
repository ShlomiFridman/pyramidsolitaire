package application;

import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class Card{
	
	private int row,pos,isFree;
	private Rank rank;
	private Suit suit;
	private Label label;
	private String img;
	
	public Card(Suit suit,Rank rank) {
		super();
		this.suit=suit;
		this.rank=rank;
		this.img=rank.img+suit.img+".png";
		labelSetup();
		setLocation(-1,-1);
	}
	
	public void setLocation(int row,int pos) {
		setRow(row);
		setPos(pos);
		label.setId(String.format("%d|%d", row,pos));
	}
	
	private void labelSetup() {
		label = new Label();
		label.setAlignment(Pos.CENTER);
//		label.setPadding(new Insets(10));
		HBox.setMargin(getLabel(), new Insets(5));
		label.setMinWidth(Region.USE_PREF_SIZE);
		updateText();
	}
	
	public Suit getSuit() {
		return suit;
	}
	public Rank getRank() {
		return rank;
	}

	public Label getLabel() {
		return label;
	}
	
	private void updateText() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			URL str = classLoader.getResource(this.img);
			Image img = new Image(str.toString());
			ImageView view = new ImageView(img);
			view.setFitHeight(120);
			view.setPreserveRatio(true);
			label.setGraphic(view);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public boolean isFree() {
		return row==6 || row<0 || isFree>0;
	}

	public boolean isReallyFree() {
		return row==6 || row<0 || isFree==2;
	}
	
	public int getFree() {
		return this.isFree;
	}

	public void freeOne() {
		this.isFree++;
	}

	@Override
	public String toString() {
		return String.format("%s %s(%d) (%d|%d) Free? %b", suit,rank,rank.val,row,pos,isFree());
	}

}
