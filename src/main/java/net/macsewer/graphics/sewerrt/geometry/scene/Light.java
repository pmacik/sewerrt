/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry.scene;

import net.macsewer.graphics.sewerrt.geometry.ColorRGB;
import net.macsewer.graphics.sewerrt.geometry.Point3D;

/**
 * Třída reprezentující všesměrový zdroj světla.
 * 
 * @author Pavel Macík
 * 
 */
public class Light {
   /**
    * Bod popisující polohu světla ve scéně.
    */
   private Point3D position;

   /**
    * Míra intenzity světla.
    */
   private float intensity;

   /**
    * Barva světla.
    */
   private ColorRGB color;

   /**
    * Vytvoří všesměrové světlo s danými atributy.
    * 
    * @param position
    *           Pozice světla ve scéně.
    * @param intensity
    *           Míra intenzity světla.
    * @param color
    *           Barva světla.
    */
   public Light(Point3D position, float intensity, ColorRGB color) {
      this.position = position;
      this.intensity = intensity;
      this.color = color;
   }

   /**
    * Vrací pozici světla ve scéně.
    * 
    * @return Pozice světla ve scéně.
    */
   public Point3D getPosition() {
      return position;
   }

   /**
    * Mění pozici světla ve scéně.
    * 
    * @param position
    *           Nová pozice světla ve scéně.
    */
   public void setPosition(Point3D position) {
      this.position = position;
   }

   /**
    * Vrací míru intenzity světla.
    * 
    * @return Míra intenzity světla.
    */
   public float getIntensity() {
      return intensity;
   }

   /**
    * Mění míru intenzity světla.
    * 
    * @param intensity
    *           Nová míra intenzity světla.
    */
   public void setIntensity(float intensity) {
      this.intensity = intensity;
   }

   /**
    * Vrací barvu světla.
    * 
    * @return Barva světla.
    */
   public ColorRGB getColor() {
      return color;
   }

   /**
    * Nastavuje barvu světla.
    * 
    * @param color
    *           Nová barva světla.
    */
   public void setColor(ColorRGB color) {
      this.color = color;
   }
}
