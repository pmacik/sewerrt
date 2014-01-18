/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující jeden trojúhelník.
 * 
 * @author Pavel Macík
 * 
 */
public class Triangle {

   /**
    * Vrchol trojúhelníku.
    */
   protected Vertex vertexA;

   /**
    * Vrchol trojúhelníku.
    */
   protected Vertex vertexB;

   /**
    * Vrchol trojúhelníku.
    */
   protected Vertex vertexC;

   /**
    * Normála k rovině trojúhelníku.
    */
   protected Vector3 normal;

   /**
    * Difúzní barva povrchu.
    */
   protected ColorRGB diffueColor = ColorRGB.white;

   /**
    * Vytvoří trojúhelník, jehož vrcholy jsou v počátku soustavy souřadnic
    * scény.
    */
   public Triangle() {
      vertexA = new Vertex();
      vertexB = new Vertex();
      vertexC = new Vertex();
   }

   /**
    * Vytvoří trojúhelník složený z daných vrcholů.
    * 
    * @param v1
    *           Vrchol trojúhelníku.
    * @param v2
    *           Vrchol trojúhelníku.
    * @param v3
    *           Vrchol trojúhelníku.
    */
   public Triangle(Vertex v1, Vertex v2, Vertex v3) {
      vertexA = new Vertex(v1);
      vertexB = new Vertex(v2);
      vertexC = new Vertex(v3);
      normal = computeNormal(v1.getPosition(), v2.getPosition(), v3.getPosition());
      vertexA.setNormal(normal);
      vertexB.setNormal(normal);
      vertexC.setNormal(normal);
   }

   /**
    * Vrací průsečík trojúhelníku s daným paprskem.
    * 
    * @param ray
    *           Paprsek, s nímž je průsečík určován.
    * @return Pokud průsečík existuje, metoda vrátí vrchol, který leží v daném
    *         průsečíku. Pokud průsečík neexistuje, metoda vrátí <code>null</code>
    */
   public Vertex crossRay(Ray ray) {
      Vector3 n = getNormal();
      Point3D pA = vertexA.getPosition();
      Point3D pB = vertexB.getPosition();
      Point3D pC = vertexC.getPosition();

      // parametry rovnice roviny
      float a = n.x;
      float b = n.y;
      float c = n.z;
      float d = -(a * pA.x + b * pA.y + c * pA.z);

      // vychozi bod paprsku
      Point3D point = ray.getOrigin();
      float px = point.x;
      float py = point.y;
      float pz = point.z;

      // smerovy vektor paprsku
      Vector3 direction = ray.getDirection();
      float sx = direction.x;
      float sy = direction.y;
      float sz = direction.z;

      float jmenovatel = a * sx + b * sy + c * sz;

      if (jmenovatel == 0) { // paprsek rovnobezny s trojuhelnikem
         return null;
      }

      float citatel = a * px + b * py + c * pz + d;

      float t = -citatel / jmenovatel;

      float x = px + t * sx;
      float y = py + t * sy;
      float z = pz + t * sz;

      float u0 = 0, v0 = 0, u1 = 0, v1 = 0, u2 = 0, v2 = 0;
      a = Math.abs(a);
      b = Math.abs(b);
      c = Math.abs(c);
      if (a >= b && a >= c) {
         u0 = y - pA.y;
         v0 = z - pA.z;
         u1 = pB.y - pA.y;
         v1 = pB.z - pA.z;
         u2 = pC.y - pA.y;
         v2 = pC.z - pA.z;
      } else if (b >= c && b >= a) {
         u0 = x - pA.x;
         v0 = z - pA.z;
         u1 = pB.x - pA.x;
         v1 = pB.z - pA.z;
         u2 = pC.x - pA.x;
         v2 = pC.z - pA.z;
      } else if (c >= a && c >= b) {
         u0 = x - pA.x;
         v0 = y - pA.y;
         u1 = pB.x - pA.x;
         v1 = pB.y - pA.y;
         u2 = pC.x - pA.x;
         v2 = pC.y - pA.y;
      }

      float aa = 0, bb = 0;

      bb = (u1 * v0 - u0 * v1) / (u1 * v2 - u2 * v1);
      aa = (u0 - bb * u2) / u1;

      if (aa >= 0.0f && bb >= 0.0f && (aa + bb) <= 1.0f) {
         Point3D pX = new Point3D(x, y, z);

         Vector3 midNormal = new Vector3(computeMidPoint(vertexA.getNormal(), vertexB.getNormal(), vertexC.getNormal(), aa, bb));

         ColorRGB midColor = new ColorRGB(computeMidPoint(vertexA.getDiffuse(), vertexB.getDiffuse(), vertexC.getDiffuse(), aa, bb));
         Float3 midTextureCoordinates = new Float3(computeMidPoint(vertexA.getTextureCoord(), vertexB.getTextureCoord(), vertexC.getTextureCoord(), aa, bb));

         return new Vertex(pX, midNormal, midColor, midTextureCoordinates);

      } else {
         return null;
      }
   }

   /**
    * Počítá (interpoluje) hodnotu třísložkového vektoru uvnitř trojúhelníku
    * (daného trojsložkovými vektory), na základě daných barycentrických
    * souřadnic.
    * 
    * @param pA
    *           Trojsložkový vektor.
    * @param pB
    *           Trojsložkový vektor.
    * @param pC
    *           Trojsložkový vektor.
    * @param aa
    *           Barycentrická souřadnice.
    * @param bb
    *           Barycentrická souřadnice.
    * @return Interpolovaná hodnota mezi třemi třísložkovými vektory určená na
    *         základě barycentrických souřadnic.
    */
   private static Float3 computeMidPoint(Float3 pA, Float3 pB, Float3 pC, float aa, float bb) {

      Vector3 vAB = new Vector3(pA, pB);
      Vector3 vAC = new Vector3(pA, pC);

      Vector3 vX = vAB.mul(aa).add(vAC.mul(bb));

      return pA.add(vX);
   }

   /**
    * Vrací normálový vektor roviny trojúhelníku.
    * 
    * @return Normála trojúhelníku.
    */
   public Vector3 getNormal() {
      return normal;
   }

   /**
    * Počítá normálu k rovině trojúhelníku určeného danými body.
    * 
    * @param vA
    *           Vrchol trojúhelníku.
    * @param vB
    *           Vrchol trojúhelníku.
    * @param vC
    *           Vrchol trojúhelníku.
    * @return Normálový vektor roviny trojúhelníku.
    */
   public static Vector3 computeNormal(Point3D vA, Point3D vB, Point3D vC) {
      Vector3 a = new Vector3(vA, vB);
      Vector3 b = new Vector3(vA, vC);
      return a.cross(b).normalized();
   }

   /**
    * Vrací vrchol trojúhelníku.
    * 
    * @return Vrchol trojúhelníku.
    */
   public Vertex getVertexA() {
      return vertexA;
   }

   /**
    * Mění vrchol trojúhelníku.
    * 
    * @param vertexA
    *           Nový vrchol trojúhelníku.
    */
   public void setVertexA(Vertex vertexA) {
      this.vertexA = vertexA;
   }

   /**
    * Vrací vrchol trojúhelníku.
    * 
    * @return Vrchol trojúhelníku.
    */
   public Vertex getVertexB() {
      return vertexB;
   }

   /**
    * Mění vrchol trojúhelníku.
    * 
    * @param vertexB
    *           Nový vrchol trojúhelníku.
    */
   public void setVertexB(Vertex vertexB) {
      this.vertexB = vertexB;
   }

   /**
    * Vrací vrchol trojúhelníku.
    * 
    * @return Vrchol trojúhelníku.
    */
   public Vertex getVertexC() {
      return vertexC;
   }

   /**
    * Mění vrchol trojúhelníku.
    * 
    * @param vertexC
    *           Nový vrchol trojúhelníku.
    */
   public void setVertexC(Vertex vertexC) {
      this.vertexC = vertexC;
   }

   /**
    * Vrací difúzní barvu povrchu.
    * 
    * @return Difúzní barva povrchu.
    */
   public ColorRGB getDiffueColor() {
      return diffueColor;
   }

   /**
    * Nastavuje difúzní barvu povrchu.
    * 
    * @param diffueColor
    *           Nová difúzní barva povrchu.
    */
   public void setDiffueColor(ColorRGB diffueColor) {
      this.diffueColor = diffueColor;
      vertexA.setDiffuse(diffueColor);
      vertexB.setDiffuse(diffueColor);
      vertexC.setDiffuse(diffueColor);
   }

}
