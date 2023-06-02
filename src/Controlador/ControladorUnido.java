package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javazoom.jl.decoder.JavaLayerException;
import reproductor.jlap;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class ControladorUnido implements MouseListener, MouseMotionListener, ActionListener {

    private ArkanoidModel model;
    private ArkanoidView view;
    private Vista vista;
    private Modelo modelo;
    private boolean isPaused = false;


    public ControladorUnido(ArkanoidModel model, ArkanoidView view, Modelo modelo, Vista vista) {
        this.model = model;
        this.view = view;
        this.modelo = modelo;
        this.vista = vista;

        view.addMouseMotionListener((MouseMotionListener) view);
        view.addMouseListener((MouseListener) view);
        this.vista.principal.Menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vista.principal2opciones();
            }
        });
        this.vista.opciones.Menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vista.opciones2principal();
            }
        });
        this.vista.principal.Jugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vista.principal2juego();
                new Thread(() -> {
                    try {
                        playGame();
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(ControladorUnido.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ControladorUnido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
        });
        this.vista.principal.Niveles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vista.principal2niveles();
            }
        });
        this.vista.niveles.Menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vista.niveles2principal();
            }
        });
        
        this.vista.opciones.IdiomaSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdiomaSelectorActionPerformed(evt);
            }
        });
        
        
        
    }
    
    private void IdiomaSelectorActionPerformed(java.awt.event.ActionEvent evt) {                                               
        JComboBox comboBox = (JComboBox) evt.getSource();
        String selected = (String) comboBox.getSelectedItem();

        switch (selected) {
            case "Español": model.messages=ResourceBundle.getBundle("MessagesBundle_es_ES");
                            
                            //this.actualizaIdioma();
                            break;
            case "Inglés": model.messages=ResourceBundle.getBundle("MessagesBundle_en_US");
                            //this.actualizaIdioma();                
                            break;
            case "Alemán": model.messages=ResourceBundle.getBundle("MessagesBundle_ge_GE");
                            //this.actualizaIdioma();                
                            break;
            case "Francés": model.messages=ResourceBundle.getBundle("MessagesBundle_fr_FR");
                            //this.actualizaIdioma();                
                            break;
            
        }
        this.vista.principal.actualizaIdioma();
        this.vista.opciones.actualizaIdioma();
        this.vista.niveles.actualizaIdioma();
    }     

    @Override
    public void mouseMoved(MouseEvent evt) {
        //model.mouseMoved(evt);
        view.updateView();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        //model.mouseClicked(arg0);
        view.updateView();
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        //model.mouseEntered(arg0);
        view.updateView();
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        //model.mouseExited(arg0);
        view.updateView();
    }
    

    public void generarBloques() {
        model.generarBloques();
        view.repaint();
    }

    public void playGame() throws JavaLayerException, IOException {

        view.setImage("/imagenes/fondo" + model.level + ".png");

        int volumenMusica = model.getVolumenMusica();
        int volumenGeneral = model.getVolumenGeneral();
        
        //OJO! Tenemos que tener en cuenta que el volumen general tiene mucha más prioridad que el volumen de la música/juego.
        //Si el Volumen General está en un punto medio entonces simplemente pasamos a evaluar los niveles de la música.
        if (volumenGeneral > 35 && volumenGeneral < 70){
            if (volumenMusica < 35 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_suave.mp3");
            }
            
            if (volumenMusica < 70 && volumenMusica > 30 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_medio.mp3");
            }
            
            if (volumenMusica > 70 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_medio.mp3");
            }
            if (volumenMusica == 0){
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_muteado.mp3");
            }
        }
        
        if (volumenGeneral < 35 && volumenGeneral != 0){
            if (volumenMusica > 70 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_medio.mp3");
            }
            if (volumenMusica < 70 && volumenMusica != 0){
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_suave.mp3");
            } 
        }
        
        if (volumenGeneral > 70 && volumenGeneral != 0){
            if (volumenMusica < 70 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_medio.mp3");
            }
            if (volumenMusica > 70 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_fuerte.mp3");
            }
        }
        
        if (volumenGeneral == 0){
            if (volumenMusica < 70 && volumenMusica > 30 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_suave.mp3");
            }
            
            if (volumenMusica < 28 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_muteado.mp3");
            }
            
            if (volumenMusica > 90 && volumenMusica != 0) {
                model.jlPlayer = new jlap("src/src/sonidos/fondo" + model.level + "_medio.mp3");
            }
        }
        
    

        //model.jlPlayer = new jlap(audioFilePath);
        model.jlPlayer.play();
        this.generarBloques();
        
        long nextTime, currTime;
        int fpsOverflow;

        fpsOverflow = 0;
        nextTime = System.currentTimeMillis();
        while (true) {
            // Espera de un tiempo fijo
            currTime = System.currentTimeMillis();
            if (currTime < nextTime) {
                try {
                    Thread.sleep(nextTime - currTime);
                } catch (Exception e) {
                }
            } else {
                fpsOverflow++;
            }
            nextTime += model.WAIT_TIME;
            // Actualizacion de las coordenadas e incrementos de la pelota
            int cont = 0;
            for (Pelota pel : model.pelotas) {
                pel.move(model.panelW, model.panelH);
                if (pel.isDown()) {
                    if (pel.getCoordY() > (model.raqueta.getCoordY() + Raqueta.RACKET_H)) {
                        model.pelotas.remove(cont);
                        if (model.pelotas.isEmpty()) {
                            model.lifes--;
                        }
                        if (model.lifes >= 0 && model.pelotas.isEmpty()) {
                            model.pelotas.add(new Pelota(model.forma,model.color,model.raqueta.getCoordX() + Raqueta.RACKET_W / 2 - Pelota.BW / 2, model.raqueta.getCoordY() - Raqueta.RACKET_H));
                        }
                        break;
                    }
                    if (((pel.getCoordX() + Pelota.BW) >= model.raqueta.getCoordX()) && (pel.getCoordX() <= model.raqueta.getCoordX() + Raqueta.RACKET_W) && ((pel.getCoordY() + Pelota.BH) >= model.raqueta.getCoordY()) && (pel.getCoordY() < (model.raqueta.getCoordY() + Raqueta.RACKET_H))) {
                        pel.setDown(false);
                        pel.rebota(model.raqueta.getCoordX(), model.raqueta.getCoordY(), Raqueta.RACKET_W, Raqueta.RACKET_H, null);
                    }
                }
                int contBloq = 0;
                for (Ladrillo bloq : model.ladrillos) {

                    if (bloq.destruido(pel)) {
                        if (bloq.getLifes() == 0) {
                            switch (bloq.getPremio()) {
                                case 1:
                                    model.premios.add(new Expansor(bloq.getCoordX() + Ladrillo.BlqWidth / 2 - Expansor.PW / 2, bloq.getCoordY()));
                                    break;
                                case 2:
                                    model.premios.add(new MasPelotas(bloq.getCoordX() + Ladrillo.BlqWidth / 2 - MasPelotas.BW / 2, bloq.getCoordY()));
                                    break;
                                case 3:
                                    model.premios.add(new Reductor(bloq.getCoordX() + Ladrillo.BlqWidth / 2 - MasPelotas.BW / 2, bloq.getCoordY()));
                                    break;
                                case 4:
                                    model.premios.add(new Acelerador(bloq.getCoordX() + Ladrillo.BlqWidth / 2 - MasPelotas.BW / 2, bloq.getCoordY()));
                                    break;
                                case 5:
                                    model.premios.add(new Freno(bloq.getCoordX() + Ladrillo.BlqWidth / 2 - MasPelotas.BW / 2, bloq.getCoordY()));
                                    break;
                                default:
                                    break;
                            }
                            model.ladrillos.remove(contBloq);
                        }
                        break;
                    }
                    contBloq++;
                }
                cont++;
            }
            cont = 0;
            if (!model.ladrillos.isEmpty()) {
                for (Premio prem : model.premios) {
                    prem.move();
                    if (prem.recivido(model.raqueta)) {
                        if (prem instanceof Expansor) {
                            model.raqueta.ampliar();
                        } else if (prem instanceof MasPelotas) {
                            model.pelotas.add(new Pelota(model.forma,model.color,model.raqueta.getCoordX() + Raqueta.RACKET_W / 2 - Pelota.BW / 2, model.raqueta.getCoordY() - Raqueta.RACKET_H));
                        } else if (prem instanceof Reductor) {
                            model.raqueta.reduir();
                        } else if (prem instanceof Acelerador) {
                            for (Pelota pelota : model.pelotas) {
                                pelota.acelera();
                            }
                        } else if (prem instanceof Freno) {
                            for (Pelota pelota : model.pelotas) {
                                pelota.frena();
                            }
                        }
                        model.premios.remove(cont);
                        break;
                    } else if (prem.getPosY() + prem.getWidth() >= model.raqueta.getCoordY() + Raqueta.RACKET_H) {
                        model.premios.remove(cont);
                        break;
                    }
                    cont++;
                }
            }
            // Repintado de la ventana para actualizar su contenido
            view.updateView();
            if (model.ladrillos.size() - Ladrillo.Immortales == 0 || model.lifes < 0) {
                model.timeLevels.add(new Time(System.currentTimeMillis() - model.startTime));
                model.startTime = 0;
                break;
            }
        }
        model.premios.clear();
        Raqueta.RACKET_W = 50;
        model.raqueta = new Raqueta(model.FRAME_W / 2 - Raqueta.RACKET_W / 2, 0);
        model.jlPlayer.player.close();
        if (model.level < model.numLevels && model.lifes >= 0) {
            jlap.iniciar = false;
            for (int y = 0; y < 5; y++) {
                model.timeNextLevel = 5 - y;
                view.updateView();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
            model.pelotas.clear();
            model.pelotas.add(new Pelota(model.forma,model.color,model.raqueta.getCoordX() + Raqueta.RACKET_W / 2 - Pelota.BW / 2, model.raqueta.getCoordY() - Raqueta.RACKET_H));
            model.niveles.get(model.level-1).conseguido();
            model.level += 1;
            model.ladrillos = null;
            this.playGame();
        } else if (model.level == model.numLevels) {
            model.level++;
            view.updateView();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
