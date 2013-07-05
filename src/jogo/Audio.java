package jogo;
import java.net.MalformedURLException;  
import java.io.File;  
import java.io.IOException;
import java.applet.*;  
  
public class Audio {  
      
    private AudioClip   somMorreu; //quando morre  
    private AudioClip   somPassou; //quando passa de fase  
    private AudioClip   somVida;   //quando ganha vida  
    private AudioClip   somFim;    //quando acaba o jogo  
  
    public Audio() {  
          
    //Arquivos de som  
      
        try {  
            somMorreu   = Applet.newAudioClip(new File("/asdfsdfsdfsdfmusic/Tchaykowsky-Concert_no.1_For_Piano.mid").toURL()); 
        }  
        catch (IOException e) {  
              
            System.out.println("Erro. Verifique o diretorio de sons");  
        }  
    }  
      
    public void tocarMorreu() {  
        try{
        somMorreu.play();  				
        System.out.println("Deu certo");	
        }					
        catch(Exception e){
        	 System.out.println("Erro. Verifique o diretorio de sons");  
        }
    }  
}  