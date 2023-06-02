
package arkanoid;

import Controlador.ArkanoidController;
import Controlador.Controlador;
import Controlador.ControladorUnido;
import Modelo.ArkanoidModel;
import Modelo.Modelo;
import Vista.ArkanoidView;
import Vista.Vista;
import java.io.IOException;

/**
 *
 * @author Carlos
 */

public class principal {
    public static void main(String[] args) throws IOException {
        // Crear los modelosModelo modelo = new Modelo();
        ArkanoidModel arkanoidModel = new ArkanoidModel();
        Modelo modelo = new Modelo();
        
        // Crear las vistas
        ArkanoidView arkanoidView = new ArkanoidView(arkanoidModel);
        Vista vista = new Vista(arkanoidModel);
        
        // Crear el controlador pasándole las vistas y los modelos
        ControladorUnido controller = new ControladorUnido(arkanoidModel, arkanoidView, modelo, vista);
    
}
    
}
