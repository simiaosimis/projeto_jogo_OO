/* File: Collision.java
 * 
 * Package: src/jogo
 * 
 * Description: This is the Collision interface responsible generalize all the collisions.
 * 
 */

package jogo;

import java.awt.Rectangle;

public interface Collision {

	public boolean collision(Player a, Enemy[] b);
	
	public boolean collisionItem(Player a, Item[] b);
	
	public int collisionShot(Player a, Enemy[] b);
	
	public boolean collisionArrow(Player a, Rectangle b);
	
	public boolean collisionTiroPlayer(Player a, Enemy[] b);
}
