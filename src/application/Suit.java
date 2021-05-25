package application;

public enum Suit {
	
	DIAMOND(1,"D"),CLUB(0,"C"),HEART(1,"H"),SPADE(0,"S");

	private final int color;
	final String img;
	private Suit(int color,String str) {
		this.color=color;
		this.img=str;
	}
	
	public String getColor() {
		return color==0? "BLACK":"RED";
	}
	
	@Override
	public String toString() {
		return this.getColor()+" "+this.name();
	}
	
	public static boolean diffColor(Suit a,Suit b) {
		return !a.getColor().equals(b.getColor());
	}
	
	public static boolean sameSuit(Suit a,Suit b) {
		return a.equals(b);
	}

}