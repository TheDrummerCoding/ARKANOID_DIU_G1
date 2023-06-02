package Modelo;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javazoom.jl.decoder.JavaLayerException;
import reproductor.jlap;


/**
 *
 * @author Carlos
 */

public class ArkanoidModel {
    
    private static int volumenMusica = 50;
    private static int volumenSonido = 50;
    private static int volumenGeneral = 50;

    public int panelW;
    public int panelH;
    // Constants
    public static final int FRAME_W = 277;
    public static final int FRAME_H = 450;
    public static final int FPS = 50;
    public static int WAIT_TIME = 1000 / FPS;

    // Attributes
    public long startTime = 0;
    public long finalTime = 0;
    public int level = 1;
    public int numLevels = 5;
    public int lifes = 4;
    public int timeNextLevel = 0;
    public static int puntuacion = 0;
    public ArrayList<Pelota> pelotas;
    public ArrayList<Premio> premios;
    public Raqueta raqueta;
    public ArrayList<Object> pauseX;
    public ArrayList<Object> pauseY;
    public ArrayList<Ladrillo> ladrillos = null;
    public ArrayList<Time> timeLevels;
    public Random random;
    public Random randomPremios;
    public static jlap jlPlayer;
    public int forma = 0;
    public int color = 0;
    public ArrayList<Nivel> niveles = new ArrayList<>();
    //public Locale currentLocale = new Locale("es", "ES");
    public ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle_es_ES");

    public ArkanoidModel() throws IOException {
        // Initialization of attributes
        pelotas = new ArrayList<>();
        premios = new ArrayList<>();
        pauseX = new ArrayList<>();
        pauseY = new ArrayList<>();
        
        timeLevels = new ArrayList<>();
        
        randomPremios = new Random();
        raqueta = new Raqueta(FRAME_W/2 - Raqueta.RACKET_W/2,0);
       //this.crearFicheros();
       this.inicializaNiveles();
    }
    
    public static void setVolumenMusica(int volumen) {
        volumenMusica = volumen;
    }

    public static void setVolumenSonido(int volumen) {
        volumenSonido = volumen;
    }

    public static void setVolumenGeneral(int volumen) {
        volumenGeneral = volumen;
    }

    public static int getVolumenMusica() {
        return volumenMusica;
    }

    public static int getVolumenSonido() {
        return volumenSonido;
    }

    public static int getVolumenGeneral() {
        return volumenGeneral;
    }
    
     public  void inicializaNiveles(){
         /*
            niveles.add(new Nivel("Nivel 1", new int[][]{
				{0,1,1,1,1,1,1,1,0},
				{0,1,1,1,1,1,1,1,0},
				{0,1,1,1,1,1,1,1,0}
			}));
            
            niveles.add(new Nivel("Nivel 2", new int[][]{
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,1,0,0,0,0},
				{0,0,0,1,1,1,0,0,0},
				{0,0,1,1,1,1,1,0,0},
				{0,1,1,1,-1,1,1,1,0},
				{0,0,1,1,1,1,1,0,0},
				{0,0,0,1,1,1,0,0,0},
				{0,0,0,0,1,0,0,0,0}
			}));
            
            niveles.add(new Nivel("Nivel 3", new int[][]{
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,2,0,0,0,0},
				{0,0,0,1,2,1,0,0,0},
				{0,0,1,1,2,1,1,0,0},
				{0,3,1,1,-1,1,1,3,0},
				{0,0,1,1,2,1,1,0,0},
				{0,0,0,1,2,1,0,0,0},
				{0,0,0,0,2,0,0,0,0}
			}));
            niveles.add(new Nivel("Nivel 4", new int[][]{
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{1,2,3,4,-1,4,3,2,1},
				{0,1,2,3,4,3,2,1,0},
				{0,0,1,2,3,2,1,0,0},
				{0,0,0,1,2,1,0,0,0},
				{0,0,0,0,-1,0,0,0,0},
				{0,0,0,1,2,1,0,0,0},
				{0,0,1,2,3,2,1,0,0},
				{0,1,2,3,4,3,2,1,0},
				{1,2,3,4,-1,4,3,2,1}
			}));

            niveles.add(new Nivel("Nivel 5", new int[][]{
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,5,5,5,0,0,0},
				{0,0,5,1,1,1,5,0,0},
				{0,0,5,2,2,2,5,0,0},
				{0,0,5,2,2,2,5,0,0},
				{0,0,5,3,3,3,5,0,0},
				{0,0,5,3,3,3,5,0,0},
				{0,0,5,4,4,4,5,0,0},
				{0,0,5,-1,5,-1,5,0,0},
				{0,5,4,0,4,0,4,5,0},
				{0,5,4,0,4,0,4,5,0},
				{0,5,4,3,4,3,4,5,0},
				{0,0,5,4,4,4,5,0,0},
				{0,0,0,-1,-1,-1,0,0,0},
				{0,0,0,5,-1,5,0,0,0},
				{0,0,0,5,5,5,0,0,0}
			}));
            */
            niveles.add(new Nivel("Nivel 6", new int[][]{
                                {0, 3, 3, 4, 5, 4, 3, 3, 0},
                                {0, 0, 0, 5, 6, 5, 0, 0, 0},
                                {1, 2, 0, 0, 5, 0, 0, 2, 1},
                                {-1, -1, 0, 3, -1, 3, 0, -1, -1},
                                {4, 0, 0, 4, 0, 4, 0, 0, 4},
                                {0, 4, 4, 5, 4, 5, 4, 4, 0},
                                {0, 0, 0, 4, 0, 4, 0, 0, 0},
                                {2, 2, 2, -1, 0, -1, 2, 2, 2},
                                {0, 3, 2, -1, 1, -1, 2, 3, 0},
                                {0, 3, -1, -1, 1, -1, -1, 3, 0},
                                {2, -1, 2, -1, 1, -1, 2, -1, 2},
                                {1, 2, 3, 0, 5, 0, 3, 2, 1},
                                {1, 2, 0, 4, 5, 4, 0, 2, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {0, 0, -1, 0, -1, 0, -1, 0, 0},
            }));
            
           
        }
    
 
   
    public void generarBloques(){
        
		int constBloques[][] = null;
                
		constBloques = this.niveles.get(level-1).getBloques();
                
		if(constBloques != null){
			this.ladrillos = new ArrayList<Ladrillo>();
			Ladrillo.Immortales = 0;
			for(int x = 0; x < constBloques.length; x++){
				for(int y = 0; y < constBloques[x].length; y++){
					if(constBloques[x][y] != 0){
						this.ladrillos.add(new Ladrillo((Ladrillo.BlqWidth * y),(Ladrillo.BlqHeight * x)+Ladrillo.posYinicial,constBloques[x][y]));
					}
					if(constBloques[x][y] < 0){
						Ladrillo.Immortales ++;
					}
				}
			}
			random = new Random();
			randomPremios = new Random();
			int number = 0;
			int premio = 0;
			for(int x = 0; x < ladrillos.size()/3; x++){
				premio = Math.abs( randomPremios.nextInt() % 5) + 1;
				number = Math.abs( random.nextInt() % ladrillos.size());
				ladrillos.get(number).setPremio(premio);
			}
		}
	}
    
    
        private void crearFicheros() throws IOException {
        String[] directorios = {"sonidos"};
        String[][] archivos = new String[][]{
            {"weak_ball.mp3","click.mp3","fondo1.mp3","fondo2.mp3","fondo3.mp3","fondo4.mp3","fondo5.mp3"}
        };
        for(int x = 0; x < directorios.length; x++){
            File directorio = new File("\\UDP\\Arkanoid" + "\\"+directorios[x]);
            directorio.mkdirs();
            directorio.setWritable(true);
            for(int y = 0; y < archivos[x].length; y++){
                String archivo = directorio.getCanonicalPath() + "\\"+archivos[x][y];

                File temp = new File(archivo);
                InputStream is = (InputStream) this.getClass().getResourceAsStream("/"+directorios[x]+"/"+archivos[x][y]);
                FileOutputStream archivoDestino = new FileOutputStream(temp);
                byte[] buffer = new byte[512*1024];
                int nbLectura;

                while ((nbLectura = is.read(buffer)) != -1)
                    archivoDestino.write(buffer, 0, nbLectura);
            }
        }       
    }

}
