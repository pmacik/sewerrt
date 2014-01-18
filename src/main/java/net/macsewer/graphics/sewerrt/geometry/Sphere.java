/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující kouli, jako objekt scény.
 * 
 * @author Pavel Macík
 * 
 */
public class Sphere extends AbstractObject {

   /**
    * Difúzní barva povrchu.
    */
   private ColorRGB diffuseColor;

   /**
    * Poloměr koule.
    */
   private float radius;

   /**
    * Vytvoří kouli s danými atributy.
    * 
    * @param center
    *           Střed koule.
    * @param radius
    *           Poloměr koule.
    */
   public Sphere(Point3D center, float radius) {
      pivot = center;
      this.radius = radius;
      diffuseColor = new ColorRGB(255, 255, 255);
      updateBoundingSphere();
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.geometry.AbstractObject#_crossRay(net.macsewer.graphics.sewerrt.geometry.Ray)
    */
   @Override
   protected void updateBoundingSphere() {
      bSphere = new BoundingSphere(pivot, radius);
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.geometry.AbstractObject#_crossRay(net.macsewer.graphics.sewerrt.geometry.Ray)
    */
   @Override
   protected Vertex _crossRay(Ray ray) {
      Point3D p = ray.getOrigin();
      Vector3 s = ray.getDirection();

      float a = s.dot(s);
      float b = 2 * (s.x * p.x + s.y * p.y + s.z * p.z - (s.x * pivot.x + s.y * pivot.y + s.z * pivot.z));
      float c = p.x * p.x + p.y * p.y + p.z * p.z - 2 * (p.x * pivot.x + p.y * pivot.y + p.z * pivot.z) + (pivot.x * pivot.x + pivot.y * pivot.y + pivot.z * pivot.z) - radius * radius;

      float d = b * b - 4 * a * c;
      if (d >= 0) {
         float t = (-b - (float) Math.sqrt(d)) / (2 * a);
         Point3D position = p.move(s.mul(t));
         Vertex cross = new Vertex();
         cross.setPosition(position);
         cross.setNormal(new Vector3(pivot, position).normalized());
         cross.setDiffuse(diffuseColor);
         cross.setTextureCoord(new Float3());
         cross.setOwner(this);
         return cross;
      } else {
         return null;
      }
   }

   /**
    * Vrací difúzní barvu povrchu.
    * 
    * @return Difúzní barva povrchu.
    */
   public Float3 getDiffuseColor() {
      return diffuseColor;
   }

   /**
    * Nastavuje difúzní barvu povrchu.
    * 
    * @param diffuseColor
    *           Nová difúzní barva povrchu.
    */
   public void setDiffuseColor(ColorRGB diffuseColor) {
      this.diffuseColor = diffuseColor;
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
}
