package application;

public enum Rank {
	
	ACE(1,"A"),TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),NINE(9),TEN(10),JACK(11,"J"),QUEEN(12,"Q"),KING(13,"K");

	final int val;
	final String img;
	
	private Rank(int val) {
		this.val=val;
		this.img=val+"";
	}
	
	private Rank(int val,String img) {
		this.val=val;
		this.img=img;
	}
	
	public static boolean is13(Card a, Card b) {
		return a.getRank().val+b.getRank().val==13;
	}

}
