package Vista;

import Fuentes.Fuentes;
import Modelo.ArkanoidModel;
import Modelo.Ladrillo;
import Modelo.Pelota;
import Modelo.Premio;
import Modelo.Raqueta;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author Carlos
 */
public class ArkanoidView extends JPanel implements MouseInputListener, KeyListener {

    private ArkanoidModel model;
    private Image image;
    private Image fondoVidas;
    

    public ArkanoidView(ArkanoidModel model) {
        this.model = model;
        this.fondoVidas = new ImageIcon(this.getClass().getResource("/imagenes/bola_trans.png")).getImage();
        initUI();
    }

    public void setImage(String img) {
        this.image = new ImageIcon(this.getClass().getResource(img)).getImage();
    }

    private void initUI() {
        // Configuración básica de la ventana
        setPreferredSize(new Dimension(ArkanoidModel.FRAME_W, ArkanoidModel.FRAME_H));

        // Añadir el manejador de eventos de ratón a este JPanel
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
    }
    private Fuentes tipoFuente = new Fuentes();
    private Image img;

    // Dibujar elementos del juego en el panel
    public void paint(Graphics g) {
        // Borramos el interior de la ventana.
        Dimension d = getSize();
        model.panelW = d.width;
        model.panelH = d.height;
        if (model.level > model.numLevels || model.lifes == 0) {

            this.setImage("/imagenes/GameOver.png");

            g.drawImage(image, 0, 0, null);
            Font alerta = new Font("Sans Serif", Font.BOLD, 55);

            int heightFinal = 335;
            g.setFont(tipoFuente.fuente(tipoFuente.ARK2, 0, 60));
            g.setColor(Color.BLACK);
            String frases = this.model.messages.getString("FinDel");

            heightFinal += g.getFontMetrics().getHeight();
            g.drawString(frases, this.getWidth() / 2 - g.getFontMetrics().stringWidth(frases) / 2, heightFinal);

            frases = this.model.messages.getString("Juego2");

            heightFinal += g.getFontMetrics().getHeight();
            g.drawString(frases, this.getWidth() / 2 - g.getFontMetrics().stringWidth(frases) / 2, heightFinal);

            g.setColor(Color.WHITE);
            heightFinal = model.panelH - 100;
            alerta = new Font("Sans Serif", Font.BOLD, 20);
            g.setFont(alerta);
            frases = this.model.messages.getString("Desarrollado");
            g.drawString(frases, this.getWidth() / 2 - g.getFontMetrics().stringWidth(frases) / 2, heightFinal);

            alerta = new Font("Sans Serif", Font.BOLD, 12);
            g.setFont(alerta);
            frases = this.model.messages.getString("Grupo");
            heightFinal += g.getFontMetrics().getHeight() + 20;
            g.drawString(frases, this.getWidth() / 2 - g.getFontMetrics().stringWidth(frases) / 2, heightFinal);
            frases = "Universidad de Valladolid. Diseño de Interfaces de Usuario";
            heightFinal += g.getFontMetrics().getHeight();
            alerta = new Font("Sans Serif", Font.BOLD, 10);
            g.setFont(alerta);
            g.drawString(frases, this.getWidth() / 2 - g.getFontMetrics().stringWidth(frases) / 2, heightFinal);
        } else {
            model.raqueta.setCoordY(model.panelH - Raqueta.RACKET_H * 5);
            if (model.pelotas.isEmpty()) {
                model.pelotas.add(new Pelota(model.forma, model.color, model.FRAME_W / 2 - Pelota.BW / 2, model.raqueta.getCoordY() - Raqueta.RACKET_H));
            }
            g.setColor(Color.white);
            g.fillRect(0, 0, model.panelW, model.panelH);
            String stringImg = "/imagenes/fondo" + model.level + ".png";
            img = new ImageIcon(this.getClass().getResource(stringImg)).getImage();
            g.drawImage(img, 0, 0, null);
            // Pintamos la pelota
            for (Pelota pel : model.pelotas) {
                pel.paint(g);
            }
            // Pintamos la raqueta
            model.raqueta.paint(g);

            /*
            if (model.ladrillos != null) {
                Iterator<Ladrillo> iter = model.ladrillos.iterator();

                while (iter.hasNext()) {
                    Ladrillo bloq = iter.next();
                    bloq.paint(g);
                }
            }
            if (model.ladrillos != null) {
                for (Ladrillo bloq : new ArrayList<>(model.ladrillos)) {
                    bloq.paint(g);
                }
            }*/
            // Pintamos los Bloques
            if (model.ladrillos != null) {
                for (Ladrillo bloq : model.ladrillos) {
                    bloq.paint(g);
                }
            }

            // Pintamos los Premios
            if (model.premios != null) {
                for (Premio prem : model.premios) {
                    prem.pinta(g);
                }
            }
            // Pintamos fondos y textos
            g.setColor(Color.black);
            g.fillRect(0, model.raqueta.getCoordY() + Raqueta.RACKET_H, model.panelW, model.raqueta.getCoordY() + Raqueta.RACKET_H);
            g.fillRect(0, 0, model.panelW, 30);
            Image image = new ImageIcon(this.getClass().getResource("/imagenes/corazon.png")).getImage();
            for (int x = 0; x < model.lifes; x++) {
                g.drawImage(image, 15 + (x * (Pelota.BW + 9)), 15 - (Pelota.BH / 2), null);
            }
            g.setColor(Color.white);

            Font contador = new Font("Sans Serif", Font.BOLD, 13);
            g.setFont(contador);
            g.drawString(this.model.messages.getString("Puntuacion") + this.model.puntuacion, (model.panelW - (g.getFontMetrics().stringWidth(this.model.messages.getString("Puntuacion") + this.model.puntuacion)) - 10), 30 - (g.getFontMetrics().getHeight() / 2));
            g.drawString(this.model.messages.getString("Nivel") + model.level, 10, (model.panelH - 10));
            if (model.ladrillos != null) {
                if (model.ladrillos.size() - Ladrillo.Immortales > 0) {
                    if (model.startTime > 0) {
                        long playTimeMillis = System.currentTimeMillis() - model.startTime;
                        Duration playTime = Duration.ofMillis(playTimeMillis);
                        long minutes = playTime.toMinutes();
                        long seconds = playTime.minusMinutes(minutes).getSeconds();
                        String tiempo = String.format("%02d:%02d", minutes, seconds);
                        g.drawString(tiempo, (model.panelW - (g.getFontMetrics().stringWidth(tiempo)) - 10), (model.panelH - 10));
                    }
                } else {
                    if (model.startTime > 0) {
                        if (model.finalTime == 0) {
                            model.finalTime = System.currentTimeMillis() - model.startTime;
                        }
                        Time crono = new Time(model.finalTime);
                        String tiempo = crono.getMinutes() + ":" + crono.getSeconds();
                        g.drawString(tiempo, (model.panelW - (g.getFontMetrics().stringWidth(tiempo)) - 10), (model.panelH - 10));
                    }
                    Font alerta = new Font("Sans Serif", Font.BOLD, 30);
                    g.setFont(alerta);
                    g.setColor(Color.WHITE);
                    String completado = this.model.messages.getString("Completado");
                    g.drawString(completado, this.getWidth() / 2 - g.getFontMetrics().stringWidth(completado) / 2, 100);
                    int nextLevelY = 110 + g.getFontMetrics().getHeight();
                    completado = this.model.messages.getString("ProximoNivel");
                    g.drawString(completado, this.getWidth() / 2 - g.getFontMetrics().stringWidth(completado) / 2, nextLevelY);
                    nextLevelY += 10 + g.getFontMetrics().getHeight();
                    completado = "" + model.timeNextLevel;
                    g.drawString(completado, this.getWidth() / 2 - g.getFontMetrics().stringWidth(completado) / 2, nextLevelY);
                }
            }
            this.updateView();
        }

    }

    public void updateView() {
        this.repaint();
    }

    public void mouseMoved(MouseEvent evt) {
        model.raqueta.setCoordX(evt.getX() - Raqueta.RACKET_W / 2);
        if (model.raqueta.getCoordX() < 0) {
            model.raqueta.setCoordX(0);
        }
        if (model.raqueta.getCoordX() + Raqueta.RACKET_W >= model.panelW) {
            model.raqueta.setCoordX(model.panelW - Raqueta.RACKET_W);
        }
        int cont = 0;
        for (Pelota pel : model.pelotas) {
            if (pel.getMovX() == 0 && pel.getMovY() == 0) {
                pel.setCoordX(evt.getX() - Pelota.BW / 2);
                if (model.raqueta.getCoordX() <= 0) {
                    pel.setCoordX(Raqueta.RACKET_W / 2 - Pelota.BW / 2);
                }
                if (model.raqueta.getCoordX() + Raqueta.RACKET_W >= model.panelW) {
                    pel.setCoordX(model.panelW - Raqueta.RACKET_W / 2 - Pelota.BW / 2);
                }
            } else if (pel.isDown()) {
                if (pel.getCoordY() > (model.raqueta.getCoordY() + Raqueta.RACKET_H)) {
                    model.pelotas.remove(cont);
                    if (model.pelotas.isEmpty()) {
                        model.lifes--;
                    }
                    if (model.lifes >= 0 && model.pelotas.isEmpty()) {
                        model.pelotas.add(new Pelota(model.forma, model.color, model.raqueta.getCoordX() + Raqueta.RACKET_W / 2 - Pelota.BW / 2, model.raqueta.getCoordY() - Raqueta.RACKET_H));
                    }
                    break;
                }
                if (((pel.getCoordX() + Pelota.BW) >= model.raqueta.getCoordX()) && (pel.getCoordX() <= model.raqueta.getCoordX() + Raqueta.RACKET_W) && ((pel.getCoordY() + Pelota.BH) >= model.raqueta.getCoordY()) && (pel.getCoordY() < (model.raqueta.getCoordY() + Raqueta.RACKET_H))) {
                    pel.setDown(false);
                    try {
                        pel.rebota(model.raqueta.getCoordX(), model.raqueta.getCoordY(), Raqueta.RACKET_W, Raqueta.RACKET_H, null);
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            cont++;
        }
        updateView();
    }

    public void mouseClicked(MouseEvent arg0) {
        for (Pelota pel : model.pelotas) {
            if (pel.getMovX() == 0 && pel.getMovY() == 0) {
                pel.setMovX(0.0);
                pel.setMovY(-3.0);
            }
        }
        if (model.startTime == 0) {
            model.startTime = System.currentTimeMillis();
        }
        updateView();
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        if (!model.pauseX.isEmpty()) {
            int cont = 0;
            for (@SuppressWarnings("unused") Object pausX : model.pauseX) {
                model.pelotas.get(cont).setMovX(Float.parseFloat(model.pauseX.get(cont).toString()));
                model.pelotas.get(cont).setMovY(Float.parseFloat(model.pauseY.get(cont).toString()));
                cont++;
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        model.pauseX.removeAll(model.pauseX);
        model.pauseY.removeAll(model.pauseY);
        for (Pelota pelota : model.pelotas) {
            model.pauseX.add(pelota.getMovX());
            model.pauseY.add(pelota.getMovY());
            pelota.setMovX(0);
            pelota.setMovY(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            model.raqueta.setCoordX(model.raqueta.getCoordX() - 15);
            if (model.raqueta.getCoordX() < 0) {
                model.raqueta.setCoordX(0);
            }
        }

        if (key == KeyEvent.VK_RIGHT) {
            model.raqueta.setCoordX(model.raqueta.getCoordX() + 15);
            if (model.raqueta.getCoordX() + Raqueta.RACKET_W >= model.panelW) {
                model.raqueta.setCoordX(model.panelW - Raqueta.RACKET_W);
            }
        }
        updateView();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
