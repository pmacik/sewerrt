/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující vrchol povrchu tělesa.
 * 
 * @author Pavel Macík
 * 
 */
public class Vertex {
   /**
    * Index pozice vrcholu.
    */
   private static final int INDEX_POSITION = 0;

   /**
    * Index pozice normály k povrchu.
    */
   private static final int INDEX_NORMAL = 1;

   /**
    * Index pozice difúzní barvy povrchu.
    */
   private static final int INDEX_DIFFUSE = 2;

   /**
    * Index pozice součadnic do textury.
    */
   private static final int INDEX_TEXTURE_COORD = 3;

   /**
    * Pole obsahující vlastnosti vrcholu (pozice, normála, barva, souřadnice do
    * textury).
    */
   private Float3[] values;

   /**
    * Reference na vlastníka vrcholu - objekt scény, kterémuvrchol patří.
    */
   private AbstractObject owner;

   /**
    * Vytvoří nový vrchol. Pozice je v počátku soustavy souřadnic scény
    * (0,&nbsp;0,&nbsp;0), normálový vektor je nulový (0,&nbsp;0,&nbsp;0),
    * barva je bílá {@link ColorRGB#white} a texturovací souřadnice jsou nulové
    * (0,&nbsp;0,&nbsp;0).
    */
   public Vertex() {
      values = new Float3[4];
      values[INDEX_POSITION] = new Point3D();
      values[INDEX_NORMAL] = new Vector3();
      values[INDEX_DIFFUSE] = ColorRGB.white;
      values[INDEX_TEXTURE_COORD] = new Float3();
   }

   /**
    * Vytvoří nový vrchol s danými atributy.
    * 
    * @param position
    *           Pozice vrcholu ve scéně.
    * @param normal
    *           Normála k povrchu objektu.
    * @param diffuseColor
    *           Difúzní barva povrchu.
    * @param textureCoordinates
    *           Souřadnice do textury.
    */
   public Vertex(Point3D position, Vector3 normal, ColorRGB diffuseColor, Float3 textureCoordinates) {
      values = new Float3[4];
      values[INDEX_POSITION] = position;
      values[INDEX_NORMAL] = normal;
      values[INDEX_DIFFUSE] = diffuseColor;
      values[INDEX_TEXTURE_COORD] = textureCoordinates;
   }

   /**
    * Vytvoří vrchol jako kopii daného vrcholu.
    * 
    * @param vertex
    *           Kopírovaný vrchol.
    */
   public Vertex(Vertex vertex) {
      owner = vertex.owner;
      values = new Float3[vertex.values.length];
      for (int i = 0; i < vertex.values.length; i++) {
         values[i] = vertex.values[i];
      }
   }

   /**
    * Vrací vlastníka (objekt, kterému vrchol patří) vrcholu.
    * 
    * @return Vlastník vrcholu.
    */
   public AbstractObject getOwner() {
      return owner;
   }

   /**
    * Nastaví vlastníka (objekt, kterému vrchol patří) vrcholu.
    * 
    * @param owner
    *           Nový vlastník vrcholu.
    */
   public void setOwner(AbstractObject owner) {
      this.owner = owner;
   }

   /**
    * Nastaví pozici vrcholu
    * 
    * @param newPosition
    *           Nová pozice vrcholu.
    */
   public void setPosition(Point3D newPosition) {
      values[INDEX_POSITION] = newPosition;
   }

   /**
    * Vrací pozici vrcholu.
    * 
    * @return Pozice vrcholu.
    */
   public Point3D getPosition() {
      return (Point3D) values[INDEX_POSITION];
   }

   /**
    * Nastavuje normálu k povrchu objektu.
    * 
    * @param newNormal
    *           Nová normála k povrchu objektu.
    */
   public void setNormal(Vector3 newNormal) {
      values[INDEX_NORMAL] = newNormal;
   }

   /**
    * Vrací normálu k povrchu objektu.
    * 
    * @return Normála k povrchu objektu.
    */
   public Vector3 getNormal() {
      return (Vector3) values[INDEX_NORMAL];
   }

   /**
    * Nastavuje difúzní barvu povrchu.
    * 
    * @param newDiffuse
    *           Nová barva povrchu.
    */
   public void setDiffuse(ColorRGB newDiffuse) {
      values[INDEX_DIFFUSE] = newDiffuse;
   }

   /**
    * Vrací difúzní barvu povrchu.
    * 
    * @return Difúzní barva povrchu.
    */
   public ColorRGB getDiffuse() {
      return (ColorRGB) values[INDEX_DIFFUSE];
   }

   /**
    * Nastavuje souřadnice do textury.
    * 
    * @param newTextureCoord
    *           Nové souřadnice do textury.
    */
   public void setTextureCoord(Float3 newTextureCoord) {
      values[INDEX_TEXTURE_COORD] = newTextureCoord;
   }

   /**
    * Vrací souřadnice do textury.
    * 
    * @return Souřadnice do textury.
    */
   public Float3 getTextureCoord() {
      return values[INDEX_TEXTURE_COORD];
   }

   /**
    * Posune vrchol o daný vektoru.
    * 
    * @param vector
    *           Vektor posunu.
    */
   public void move(Vector3 vector) {
      values[INDEX_POSITION].x += vector.x;
      values[INDEX_POSITION].y += vector.y;
      values[INDEX_POSITION].z += vector.z;
   }
}
