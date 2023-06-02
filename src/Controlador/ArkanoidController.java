/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Acelerador;
import Modelo.ArkanoidModel;
import Modelo.Expansor;
import Modelo.Freno;
import Modelo.Ladrillo;
import Modelo.MasPelotas;
import Modelo.Pelota;
import Modelo.Premio;
import Modelo.Raqueta;
import Modelo.Reductor;
import Vista.ArkanoidView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Image;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import javazoom.jl.decoder.JavaLayerException;
import reproductor.jlap;


public class ArkanoidController implements MouseListener, MouseMotionListener {
    private ArkanoidModel model;
    private ArkanoidView view;

    public ArkanoidController(ArkanoidModel model, ArkanoidView view) {
        this.model = model;
        this.view = view;
        view.addMouseMotionListener((MouseMotionListener) view);
        view.addMouseListener((MouseListener) view);
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

    // Implementar los otros métodos de MouseListener y MouseMotionListener

    public void generarBloques() {
        model.generarBloques();
        view.repaint();
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

    public void playGame() throws JavaLayerException, IOException {
		view.setImage("/imagenes/fondo"+model.level+".png");
		model.jlPlayer = new jlap("\\UDP\\Arkanoid\\sonidos\\fondo"+model.level+".mp3");
		model.jlPlayer.play();
		this.generarBloques();
		
                long nextTime,currTime;
		int fpsOverflow;
		
		fpsOverflow=0;
		nextTime=System.currentTimeMillis();
		while (true) {
			// Espera de un tiempo fijo
			currTime = System.currentTimeMillis();
			if (currTime<nextTime)
				try { Thread.sleep(nextTime-currTime); }
				catch (Exception e) {}
			else fpsOverflow++;
			nextTime+=model.WAIT_TIME;
			// Actualizaci�n de las coordenadas e incrementos de la pelota
			int cont = 0;
			for (Pelota pel : model.pelotas){
				pel.move(model.panelW, model.panelH);
				if(pel.isDown()){
					if(pel.getCoordY() > (model.raqueta.getCoordY()+Raqueta.RACKET_H)){
						model.pelotas.remove(cont);
						if(model.pelotas.isEmpty()){
							model.lifes --;
						}
						if(model.lifes >= 0 && model.pelotas.isEmpty()){
							model.pelotas.add(new Pelota(model.forma,model.color,model.raqueta.getCoordX() + Raqueta.RACKET_W/2 - Pelota.BW/2,model.raqueta.getCoordY() - Raqueta.RACKET_H));
						}
						break;
					}
					if (((pel.getCoordX()+Pelota.BW) >= model.raqueta.getCoordX())&&(pel.getCoordX() <= model.raqueta.getCoordX()+Raqueta.RACKET_W)&&((pel.getCoordY()+Pelota.BH) >= model.raqueta.getCoordY())&&(pel.getCoordY() < (model.raqueta.getCoordY()+Raqueta.RACKET_H))){
						pel.setDown(false);
						pel.rebota(model.raqueta.getCoordX(), model.raqueta.getCoordY(), Raqueta.RACKET_W, Raqueta.RACKET_H, null);
					}
				}
				int contBloq = 0;
				for (Ladrillo bloq : model.ladrillos){
					if(bloq.destruido(pel)){
						if(bloq.getLifes() == 0){
							switch (bloq.getPremio()) {
								case 1:
									model.premios.add(new Expansor(bloq.getCoordX() + Ladrillo.BlqWidth/2 - Expansor.PW/2, bloq.getCoordY()));
									break;
								case 2:
									model.premios.add(new MasPelotas(bloq.getCoordX() + Ladrillo.BlqWidth/2 - MasPelotas.BW/2, bloq.getCoordY()));
									break;
								case 3:
									model.premios.add(new Reductor(bloq.getCoordX() + Ladrillo.BlqWidth/2 - MasPelotas.BW/2, bloq.getCoordY()));
									break;
								case 4:
									model.premios.add(new Acelerador(bloq.getCoordX() + Ladrillo.BlqWidth/2 - MasPelotas.BW/2, bloq.getCoordY()));
									break;
								case 5:
									model.premios.add(new Freno(bloq.getCoordX() + Ladrillo.BlqWidth/2 - MasPelotas.BW/2, bloq.getCoordY()));
									break;
								default:
									break;
							}
							model.ladrillos.remove(contBloq);
						}
						break;
					}
					contBloq ++;
				}
				cont ++;
			}
			cont = 0;
			if(!model.ladrillos.isEmpty()){
				for(Premio prem : model.premios){
					prem.move();
					if(prem.recivido(model.raqueta)){
						if (prem instanceof Expansor) {
							model.raqueta.ampliar();
						}else if(prem instanceof MasPelotas){
							model.pelotas.add(new Pelota(model.forma,model.color,model.raqueta.getCoordX() + Raqueta.RACKET_W/2 - Pelota.BW/2,model.raqueta.getCoordY() - Raqueta.RACKET_H));
						}else if(prem instanceof Reductor){
							model.raqueta.reduir();
						}else if(prem instanceof Acelerador){
							for(Pelota pelota : model.pelotas){
								pelota.acelera();
							}
						}else if(prem instanceof Freno){
							for(Pelota pelota : model.pelotas){
								pelota.frena();
							}
						}
						model.premios.remove(cont);
						break;
					}else if(prem.getPosY() + prem.getWidth() >= model.raqueta.getCoordY() + Raqueta.RACKET_H){
						model.premios.remove(cont);
						break;
					}
					cont++;
				}
			}
			// Repintado de la ventana para actualizar su contenido
			view.updateView();
			if(model.ladrillos.size()-Ladrillo.Immortales == 0 || model.lifes < 0){
				model.timeLevels.add(new Time(System.currentTimeMillis() - model.startTime));
				model.startTime = 0;
				break;
			}
		}
		model.premios.clear();
		Raqueta.RACKET_W = 50;
		model.raqueta = new Raqueta(model.FRAME_W/2 - Raqueta.RACKET_W/2,0);
		model.jlPlayer.player.close();
		if(model.level < model.numLevels && model.lifes >= 0){
			jlap.iniciar = false;
			for(int y = 0; y < 5; y++){
				model.timeNextLevel = 5-y;
				view.updateView();
				try { Thread.sleep(1000); }
				catch (Exception e) {}
			}
			model.pelotas.clear();
			model.pelotas.add(new Pelota(model.forma,model.color,model.raqueta.getCoordX() + Raqueta.RACKET_W/2 - Pelota.BW/2,model.raqueta.getCoordY() - Raqueta.RACKET_H));
			model.level += 1;
			model.ladrillos = null;
			this.playGame();
		}else if(model.level == model.numLevels){
			model.level++;
			view.updateView();
		}
                
           
	}

}