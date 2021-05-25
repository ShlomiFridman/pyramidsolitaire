package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Clock {
	
	private Timeline tl;
	private int sec;
	private Label clock;
	public boolean running;
	
	public Clock(Label clock){
		this.clock=clock;
		tl = new Timeline();
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				updateText();
			}
		}));
	}
	
	public void start() {
		tl.play();
		running=true;
	}
	
	public void pause() {
		tl.pause();
		running=false;
	}
	
	public void reset() {
		tl.stop();
		sec=-1;
		updateText();
		running=false;
	}
	
	private void updateText() {
		this.clock.setText(getTemplate(++sec));
	}
	
	private String getTemplate(int s) {
		return String.format("%02d:%02d", s/60,s%60);
	}
}
