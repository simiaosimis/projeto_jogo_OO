package jogo;

import java.awt.Rectangle;

public interface Colisao {

	public boolean colisao(Player a, Inimigo[] b);
	
	public boolean colisaoItem(Player a, Item[] b);
	
	public int colisaoTiro(Player a, Inimigo[] b);
	
	public boolean colisaoSeta(Player a, Rectangle b);
	
	public boolean colisaoTiroPlayer(Player a, Inimigo[] b);
}
