package jogo;

public class Inimigo extends Pessoa{

	private String nome;
	private int movimento;
	public boolean tiro;
	public Fase fase;
	
	public Inimigo(String nome, int movimento, int ataque, int altura, int largura, int x, int y, boolean tiro) {
		super(x,y,altura,largura);
		this.nome = nome;
		this.movimento = movimento;
		this.tiro=tiro;
		this.fase=fase;
		}
	
	public void atirar(){
		System.out.println("O inimigo atirou!");
	}
		
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getMovimento() {
		return movimento;
	}

	public void setMovimento(int movimento) {
		this.movimento = movimento;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public boolean getTiro() {
		return tiro;
	}

	public void setTiro(boolean tiro) {
		this.tiro = tiro;
	}
	
	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}
}