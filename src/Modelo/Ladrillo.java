package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import reproductor.jlap;
import javazoom.jl.decoder.JavaLayerException;

public class Ladrillo {

    public static int BlqWidth = 50;

    public static int BlqHeight = 25;

    public static int posYinicial = 50;

    public static int Immortales = 0;

    private int posX;

    private int posY;

    private int lifes;

    private int points;

    private Image fondo = null;

    private jlap mediaRebote = null;

    private int premio = 0;
    
    private int volumenJuego = ArkanoidModel.getVolumenSonido();
    private int volumenGeneral = ArkanoidModel.getVolumenGeneral();

    public Ladrillo(int posX, int posY, int life) {
        this.setCoordX(posX);
        this.setCoordY(posY);
        this.setLifes(life);
        //this.fondo = new ImageIcon(this.getClass().getResource("/imagenes/ladrillo.png")).getImage();

        ImageIcon iconoOriginal = new ImageIcon(this.getClass().getResource("/imagenes/ladrillo.png"));
        Image imagenOriginal = iconoOriginal.getImage();

        

        Image imagenEscalada = imagenOriginal.getScaledInstance(BlqWidth, BlqHeight, Image.SCALE_SMOOTH);

        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

        this.fondo = imagenEscalada;

        this.setPoints(life * 100);
        try {
            if (volumenGeneral > 35 && volumenGeneral < 70){
                if (volumenJuego > 92){
                    mediaRebote = new jlap("src/sonidos/weak_ball_fuerte.mp3");
                }
                if (volumenJuego > 35 && volumenJuego < 70){
                    mediaRebote = new jlap("src/sonidos/weak_ball_medio.mp3");
                }
                if (volumenJuego < 10){
                    mediaRebote = new jlap("src/sonidos/weak_ball_suave.mp3");
                } 
            }
            
            if (volumenGeneral > 70){
                if (volumenJuego < 20){
                    mediaRebote = new jlap("src/sonidos/weak_ball_medio.mp3");
                }else{
                    mediaRebote = new jlap("src/sonidos/weak_ball_fuerte.mp3");
                }
            }
            
            if (volumenGeneral < 35 && volumenGeneral != 0){
                if (volumenJuego > 40){
                    mediaRebote = new jlap("src/sonidos/weak_ball_medio.mp3");
                }
                if (volumenJuego < 35){
                    mediaRebote = new jlap("src/sonidos/weak_ball_suave.mp3");
                }
            }
            
            if (volumenGeneral == 0){
                if(volumenJuego > 85){
                    mediaRebote = new jlap("src/sonidos/weak_ball_medio.mp3");
                }
                if(volumenJuego < 60){
                    mediaRebote = new jlap("src/sonidos/weak_ball_suave.mp3");
                }
                if(volumenJuego == 0){
                    mediaRebote = new jlap("src/sonidos/weak_ball_muteado.mp3");
                }
                
            }
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLifes(int life) {
        this.lifes = life;
    }

    public int getLifes() {
        return this.lifes;
    }

    public void setCoordX(int pos) {
        this.posX = pos;
    }

    public int getCoordX() {
        return this.posX;
    }

    public void setCoordY(int pos) {
        this.posY = pos;
    }

    public int getCoordY() {
        return this.posY;
    }

    public void setPoints(int puntos) {
        this.points = puntos;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPremio(int prem) {
        this.premio = prem;
    }

    public int getPremio() {
        return this.premio;
    }

    public void paint(Graphics gr) {
        if (this.getLifes() != 0) {
            switch (this.getLifes()) {
                case -1:
                    gr.setColor(Color.BLACK);
                    break;
                case 1:
                    gr.setColor(Color.GREEN);
                    break;
                case 2:
                    gr.setColor(Color.YELLOW);
                    break;
                case 3:
                    gr.setColor(Color.CYAN);
                    break;
                case 4:
                    gr.setColor(Color.ORANGE);
                    break;
                case 5:
                    gr.setColor(Color.MAGENTA);
                    break;
                default:
                    break;
            }
            gr.fillRect(this.getCoordX(), this.getCoordY(), BlqWidth, BlqHeight);
            gr.setColor(Color.GRAY);
            gr.drawRect(this.getCoordX(), this.getCoordY(), BlqWidth, BlqHeight);
            gr.drawImage(fondo, this.getCoordX(), this.getCoordY(), null);
        }
    }

    public boolean destruido(Pelota pelota) throws JavaLayerException, IOException {
        if (pelota.rebota(this.getCoordX(), this.getCoordY(), Ladrillo.BlqWidth, Ladrillo.BlqHeight, this)) {
            mediaRebote.play();
            return true;
        } else {
            return false;
        }
    }

}
