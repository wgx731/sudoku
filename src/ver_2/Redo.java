package ver_2;

import java.awt.Point;

public class Redo {
	private Point p;
	private int value;

	public Redo(Point p, int value) {
		this.p = p;
		this.value = value;
	}

	public Point getP() {
		return p;
	}

	public int getValue() {
		return value;
	}

}