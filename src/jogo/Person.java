/* File: Person.java
 * 
 * Package: src/jogo
 * 
 * Description: This is the Person class responsible for generalize the attributes from dynamic entities.
 * 
 */

package jogo;

public class Person {

	protected float x;
	protected float y;
	protected int height;
	protected int width;
	protected boolean shoot;
	protected boolean left;
	protected boolean right;

	public Person() {
	}

	public Person(int x, int y, int height, int width, boolean shoot, boolean left, boolean right) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.shoot = shoot;
		this.left = left;
		this.right = right;
	}

	public void shoot() {
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isShoot() {
		return shoot;
	}

	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}

}
