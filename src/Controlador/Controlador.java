
package Controlador;

import Modelo.Modelo;
import Vista.Vista;
import java.awt.event.*;
import modelo.*;
import vista.*;

public class Controlador implements ActionListener{

    private Vista vista;
    private Modelo modelo;
    
    //private Modelo model;
   
    public Controlador(Modelo modelo, Vista vista){
        this.vista = vista;
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
            }
        });
    }
    
    public void iniciar(){
        
    }
    
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
