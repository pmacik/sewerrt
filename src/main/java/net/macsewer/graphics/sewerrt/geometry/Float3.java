/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující obecný třísložkový vektor od které jsou odvozeny další.
 * 
 * @author Pavel Macík
 * 
 */
public class Float3 {
   /**
    * Souřadnice X.
    */
   protected float x;

   /**
    * Souřadnice Y.
    */
   protected float y;

   /**
    * Souřadnice Z.
    */
   protected float z;

   /**
    * Vytvoří nulový (0, 0, 0) vektor.
    */
   public Float3() {
      this(0, 0, 0);
   }

   /**
    * Vytvoří kopii daného vektoru.
    * 
    * @param f
    *           Kopírovaný vektor.
    */
   public Float3(Float3 f) {
      this(f.x, f.y, f.z);
   }

   /**
    * Vytvoří obecný třísložkový vektor s danými souřadnicemi.
    * 
    * @param x
    *           Souřadnice X.
    * @param y
    *           Souřadnice Y.
    * @param z
    *           Souřadnice Z.
    */
   public Float3(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   /**
    * Vrací součet daného vektoru a druhého vektoru <code>f</code>.
    * 
    * @param f
    *           Druhý vektor pro součet.
    * @return Obecný vektor, který je dán součtem obou vektorů.
    */
   public Float3 add(Float3 f) {
      Float3 tmp = new Float3(this);
      tmp.x += f.x;
      tmp.y += f.y;
      tmp.z += f.z;
      return tmp;
   }

   /**
    * Vrací rozdíl daného vektoru a druhého vektoru <code>f</code>.
    * 
    * @param f
    *           Druhý vektor pro rozdíl.
    * @return Obecný vektor, který je dán rozdílem obou vektorů.
    */
   public Float3 sub(Float3 f) {
      Float3 tmp = new Float3(this);
      tmp.x -= f.x;
      tmp.y -= f.y;
      tmp.z -= f.z;
      return tmp;
   }

   /**
    * Vrací hodnotu souřadnice X.
    * 
    * @return Souřadnice X.
    */
   public float getX() {
      return x;
   }

   /**
    * Nastavuje hodnotu souřadnice X.
    * 
    * @param x
    *           Nová souřadnice X.
    */
   public void setX(float x) {
      this.x = x;
   }

   /**
    * Vrací hodnotu souřadnice Y.
    * 
    * @return Souřadnice Y.
    */
   public float getY() {
      return y;
   }

   /**
    * Nastavuje hodnotu souřadnice Y.
    * 
    * @param y
    *           Nová souřadnice Y.
    */
   public void setY(float y) {
      this.y = y;
   }

   /**
    * Vrací hodnotu souřadnice Z.
    * 
    * @return Souřadnice Z.
    */
   public float getZ() {
      return z;
   }

   /**
    * Nastavuje hodnotu souřadnice Z.
    * 
    * @param z
    *           Nová souřadnice Z.
    */
   public void setZ(float z) {
      this.z = z;
   }

   /**
    * Nastavuje hodnoty všech tří souřadnic.
    * 
    * @param x
    *           Souřadnice X.
    * @param y
    *           Souřadnice Y.
    * @param z
    *           Souřadnice Z.
    */
   public void setValues(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   /**
    * Vrací součin daného vektoru a skalární hodnoty <code>m</code>.
    * 
    * @param m
    *           Skalární hodnota pro součin.
    * @return Obecný vektor, který je dán součinem vektoru a skaláru.
    */
   public Float3 mul(float m) {
      Float3 tmp = new Float3(this);
      tmp.x *= m;
      tmp.y *= m;
      tmp.z *= m;
      return tmp;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return new String("(" + x + ", " + y + ", " + z + ")");
   }
}
