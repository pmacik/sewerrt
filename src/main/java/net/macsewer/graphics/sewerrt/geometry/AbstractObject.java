/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Abstraktní třída reprezentující abstraktní objekt.
 * 
 * @author Pavel Macík
 * 
 */
public abstract class AbstractObject {

   /**
    * Bod popisující počátek soustavy souřadnic objektu.
    */
   protected Point3D pivot;

   /**
    * Obalová koule svázaná s objektem. Používá se pro urychlení výpočtu.
    */
   protected BoundingSphere bSphere;

   /**
    * Transformační matice.
    */
   protected Matrix4 transform;

   /**
    * Vytvoří nový abstraktní objekt.
    */
   public AbstractObject() {
      pivot = new Point3D();
      bSphere = new BoundingSphere(new Point3D(), 0);
   }

   /**
    * Vrací průsečík objektu s daným paprskem.
    * 
    * @param ray
    *           Paprsek, s nímž je průsečík určován.
    * @return Pokud průsečík existuje, metoda vrátí vrchol, který leží v daném
    *         průsečíku. Pokud průsečík neexistuje, metoda vrátí <code>null</code>
    */
   public Vertex crossRay(Ray ray) {
      if (bSphere.rayCrossed(ray)) {
         Vertex tmp = _crossRay(ray);
         if (tmp != null) {
            Vector3 k = new Vector3(ray.getOrigin(), tmp.getPosition()).normalized();
            if (k.dot(ray.getDirection()) > 0) {
               tmp.setOwner(this);
               return tmp;
            }
         }
      }
      return null;
   }

   /**
    * Přepočítá parametry obalové koule.
    */
   protected abstract void updateBoundingSphere();

   /**
    * MetoDa, která provádí vlastní výpočet průsečíku paprsku s objektem.
    * 
    * @param ray
    *           Paprsek, s nímž je průsečík určován.
    * @return Pokud průsečík existuje, metoda vrátí vrchol, který leží v daném
    *         průsečíku. Pokud průsečík neexistuje, metoda vrátí <code>null</code>.
    */
   protected abstract Vertex _crossRay(Ray ray);

}
