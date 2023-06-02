
package Modelo;

import javax.swing.JButton;

public class Nivel {
    private String nombre;
    private boolean conseguido = false;
    private static int vecesConseguido = 0;
    private int[][] bloques;
    private JButton boton = new JButton();
    
    public Nivel(String nombre, int[][] bloques){
        this.nombre = nombre;
        this.bloques = bloques;
    }
    
    public JButton getBoton(){
        return this.boton;
    }
    
    public int[][] getBloques(){
        return this.bloques;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public int getVecesConseguido(){
        return this.vecesConseguido;
    }
    
    public void conseguido(){
        this.conseguido=true;
    }
    public boolean getConseguido(){
        return this.conseguido;
    }
    
    
}
