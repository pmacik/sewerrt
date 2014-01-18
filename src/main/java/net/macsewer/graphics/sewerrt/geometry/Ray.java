/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující paprsek.
 * 
 * @author Pavel Macík
 * 
 */
public class Ray {
   /**
    * Výchozí bod paprsku.
    */
   private Point3D origin;

   /**
    * Směrový vektor paprsku.
    */
   private Vector3 direction;

   /**
    * Vytvoří nový paprsek s danými atributy.
    * 
    * @param origin
    *           Výchozí bod paprsku.
    * @param direction
    *           Směrový vektor paprsku.
    */
   public Ray(Point3D origin, Vector3 direction) {
      this.origin = origin;
      this.direction = direction.normalized();
   }

   /**
    * Vrací výchozí bod paprsku.
    * 
    * @return Výchozí bod paprsku.
    */
   public Point3D getOrigin() {
      return origin;
   }

   /**
    * Nastavuje výchozí bod paprsku.
    * 
    * @param origin
    *           Nový výchozí bod paprsku.
    */
   public void setOrigin(Point3D origin) {
      this.origin = origin;
   }

   /**
    * Vrací směrový vektor paprsku.
    * 
    * @return Směrový vektor paprsku.
    */
   public Vector3 getDirection() {
      return direction;
   }

   /**
    * Nastavuje směrový vektor paprsku.
    * 
    * @param direction
    *           Nový směrový vektor paprsku.
    */
   public void setDirection(Vector3 direction) {
      this.direction = direction;
   }

}
