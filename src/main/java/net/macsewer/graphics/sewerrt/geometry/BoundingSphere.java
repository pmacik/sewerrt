/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující obalovou kouli používanou pro urychlení výpočtu
 * algoritmu sledování paprsku.
 * 
 * @author Pavel Macík
 * 
 */
public class BoundingSphere {

   /**
    * Střed koule.
    */
   private Point3D pivot;

   /**
    * Poloměr koule.
    */
   private float radius;

   /**
    * Vytvoří obalovou kouli s danými atributy.
    * 
    * @param center
    *           Střed koule.
    * @param radius
    *           Poloměr koule.
    */
   public BoundingSphere(Point3D center, float radius) {
      pivot = center;
      this.radius = radius;
   }

   /**
    * Vrací poloměr koule.
    * 
    * @return Poloměr koule.
    */
   public float getRadius() {
      return radius;
   }

   /**
    * Nastavuje poloměr koule.
    * 
    * @param radius
    *           Nový poloměr koule.
    */
   public void setRadius(float radius) {
      this.radius = radius;
   }

   /**
    * Vrací informaci o tom, zda-li daný paprsek kouli protíná nebo ne.
    * 
    * @param ray
    *           Paprsek, jehož průsečík určujeme.
    * @return <code>true</code>, pokud průsečík daného paprsku s koulí
    *         existuje, jinak <code>false</code>.
    */
   public boolean rayCrossed(Ray ray) {
      Point3D p = ray.getOrigin();
      Vector3 s = ray.getDirection();

      float a = s.dot(s);
      float b = 2 * (s.x * p.x + s.y * p.y + s.z * p.z - (s.x * pivot.x + s.y * pivot.y + s.z * pivot.z));
      float c = p.x * p.x + p.y * p.y + p.z * p.z - 2 * (p.x * pivot.x + p.y * pivot.y + p.z * pivot.z) + (pivot.x * pivot.x + pivot.y * pivot.y + pivot.z * pivot.z) - radius * radius;

      float d = b * b - 4 * a * c;
      return d >= 0;
   }

   /**
    * Vrací střed koule.
    * 
    * @return Střed koule.
    */
   public Point3D getPivot() {
      return pivot;
   }

   /**
    * Mění pozici středu koule.
    * 
    * @param pivot
    *           Nová pozice středu koule.
    */
   public void setPivot(Point3D pivot) {
      this.pivot = pivot;
   }
}
