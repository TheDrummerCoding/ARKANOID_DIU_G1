/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Fuentes.Fuentes;
import Modelo.ArkanoidModel;

/**
 *
 * @author Carlos
 */
public class Niveles extends javax.swing.JPanel {

    /**
     * Creates new form Principal
     */
    public Niveles(ArkanoidModel m) {
        this.model=m;
        initComponents();
        Menu.setContentAreaFilled(false);
        Menu.setBorderPainted(false);
        
        int x = 80;
        int y = 170;
        int bloqueWidth=80;
        int bloqueHeight=80;
        int columnasActuales=0;
        int maxColumnas=3;

        
        for(int i = 0;i<model.niveles.size();i++){
            if(model.niveles.get(i).getConseguido()) jPanel2.add(model.niveles.get(i).getBoton(), new org.netbeans.lib.awtextra.AbsoluteConstraints(x, y, 80, 80));
            else {
            model.niveles.get(i).getBoton().setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/candado (2).png"))); // NOI18N
            jPanel2.add(model.niveles.get(i).getBoton(), new org.netbeans.lib.awtextra.AbsoluteConstraints(x, y, 80, 80));}
            // Aumentar las coordenadas horizontales para la siguiente columna
            x += bloqueWidth + 30;
            columnasActuales++;

            // Saltar a la siguiente fila si hemos agregado el número máximo de columnas
            if (columnasActuales == maxColumnas) {
                x = 80;
                y += bloqueHeight + 30;
                columnasActuales = 0;
            }
        }
    }
    
    
    
    public void actualizaIdioma(){
        this.OpcionesText.setText(this.model.messages.getString("Niveles"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        OpcionesText = new javax.swing.JLabel();
        Menu = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        OpcionesText.setFont(tipoFuente.fuente(tipoFuente.ARK2,0,40));
        OpcionesText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OpcionesText.setText(this.model.messages.getString("Niveles"));
        jPanel2.add(OpcionesText, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 370, 100));

        Menu.setBackground(new java.awt.Color(255, 0, 0));
        Menu.setForeground(new java.awt.Color(240, 0, 0));
        Menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lanzamiento-de-cohete-espacial (1) (1).png"))); // NOI18N
        jPanel2.add(Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 50, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Borde2.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 450, 700));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo2_1.png")));
        jPanel2.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

private Fuentes tipoFuente = new Fuentes();
private ArkanoidModel model;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    public javax.swing.JButton Menu;
    private javax.swing.JLabel OpcionesText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables


    
    
}
