/**
 * 
 */
package net.macsewer.graphics.sewerrt.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Třída okna aplikace, ve kterém se zobrazuje výsledek promítání scény.
 * 
 * @author Pavel Macík
 * 
 */
public class SewerRTFrame extends JFrame {
   /**
    * ID pro serializaci.
    */
   private static final long serialVersionUID = 3636991323685726233L;

   /**
    * Rastr průmětny.
    */
   private BufferedImage canvas;

   /**
    * Plátno, do kterého se vykresluje obraz scény.
    */
   private Canvas pnlCanvas;

   /**
    * Vytvoří okno aplikace s daným rastrem.
    * 
    * @param canvas
    *           Rastr průmětny.
    */
   public SewerRTFrame(BufferedImage canvas) {
      this.canvas = canvas;
      initGUI();
   }

   /**
    * Inicializuje GUI.
    */
   private void initGUI() {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setTitle("SewerRT");
      int w = canvas.getWidth();
      int h = canvas.getHeight();

      pnlCanvas = new Canvas(canvas);
      pnlCanvas.setPreferredSize(new Dimension(w, h));

      add(pnlCanvas, BorderLayout.CENTER);
      pack();
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.awt.Container#paint(java.awt.Graphics)
    */
   @Override
   public void paint(Graphics g) {
      super.paint(g);
      pnlCanvas.repaint();
   }

   /**
    * Nastavuje rastr.
    * 
    * @param canvas
    *           Nový rastr.
    */
   public void setCanvas(BufferedImage canvas) {
      this.canvas = canvas;
      repaint();
   }
}
