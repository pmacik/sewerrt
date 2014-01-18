/**
 * 
 */
package net.macsewer.graphics.sewerrt.camera;

import java.util.ArrayList;
import java.util.List;

import net.macsewer.graphics.sewerrt.geometry.Point3D;
import net.macsewer.graphics.sewerrt.geometry.Ray;
import net.macsewer.graphics.sewerrt.geometry.Vector3;

/**
 * Třída reprezentující abstraktní kameru pro raytracer SewerRT.
 * 
 * @author Pavel Macík
 * 
 */
public abstract class AbstractCamera {

   /**
    * Pozice kamery ve scéně.
    */
   protected Point3D position;

   /**
    * Vektor určující směr pohledu kamery.
    */
   protected Vector3 direction;

   /**
    * Vektor, který definuje směr nahoru.
    */
   protected Vector3 up;

   /**
    * Vektor, který definuje směr vpravo.
    */
   protected Vector3 right;

   /**
    * Skutečná šířka průmětny kamery - velikost v rámci scény.
    */
   protected float realWidth;

   protected float realStepU;

   /**
    * Skutečná výška průmětny kamery - velikost v rámci scény.
    */
   protected float realHeight;

   protected float realStepV;

   /**
    * Šířka rastru průmětny (v pixelech) - rozlišení.
    */
   protected int screenWidth;

   /**
    * Výška rastru průmětny (v pixelech) - rozlišení.
    */
   protected int screenHeight;

   /**
    * Vytvoří kameru s danými atributy.
    * 
    * @param position
    *           Pozice kamery ve scéně.
    * @param target
    *           Bod, na který je kamera zaměřena.
    * @param up
    *           Vektor určující směr nahoru.
    * @param screenWidth
    *           Šířka rastru průmětny.
    * @param screenHeight
    *           Výška rastru průmětny.
    */
   protected AbstractCamera(Point3D position, Point3D target, Vector3 up, int screenWidth, int screenHeight) {
      this(position, new Vector3(position, target), up, screenWidth, screenHeight);
   }

   /**
    * Vytvoří kameru s danými atributy.
    * 
    * @param position
    *           Pozice kamery ve scéně.
    * @param direction
    *           Vektor určující směr pohledu kamery.
    * @param up
    *           Vektor určující směr nahoru.
    * @param screenWidth
    *           Šířka rastru průmětny.
    * @param screenHeight
    *           Výška rastru průmětny.
    */
   public AbstractCamera(Point3D position, Vector3 direction, Vector3 up, int screenWidth, int screenHeight) {
      this.position = position;
      this.direction = direction.normalized();
      this.up = up.normalized();
      right = this.direction.cross(this.up).normalized();
      this.up = right.cross(this.direction).normalized();
      this.screenWidth = screenWidth;
      this.screenHeight = screenHeight;

      if (screenWidth > screenHeight) {
         realHeight = 2.0f;
         realWidth = screenWidth * realHeight / screenHeight;
      } else {
         realWidth = 2.0f;
         realHeight = screenHeight * realWidth / screenWidth;
      }

      computeRealUVSteps();
   }

   /**
    * Implicitní konstruktor. Vytvoří kameru s pozicí v počátku soustavy
    * souřadnic scény - bod (0, 0, 0), vektorem up (0, 0, 1), vektorem pohledu
    * (1, 0, 0), a rozlišením rastru (100 x 100) px.
    */
   protected AbstractCamera() {
      super();
      position = new Point3D();
      direction = new Vector3(1, 0, 0);
      up = new Vector3(0, 0, 1);
      right = direction.cross(up).normalized();
      up = right.cross(direction).normalized();
      screenWidth = 100;
      screenHeight = 100;
      realWidth = 2;
      realHeight = 2;
      computeRealUVSteps();
   }

   private void computeRealUVSteps() {
      realStepU = realWidth / (screenWidth - 1);
      realStepV = realHeight / (screenHeight - 1);
   }

   /**
    * Vrací pozici (v rámci scény) pixelu rastru průmětny na souřadnicích <code>(i, j)</code>.
    * 
    * @param i
    *           Vertikální souřadnice pixelu v rastru průmětny.
    * @param j
    *           Horizontální souřadnice pixelu v rastru průmětny.
    * @return Vrací bod, který popisuje polohu daného pixelu ve scéně.
    */
   protected Point3D getPixelPosition(int i, int j) {
      Point3D pixelPosition = new Point3D(position);

      float u = ((float) (i) / (screenWidth - 1) - 0.5f) * realWidth;
      float v = ((float) (screenHeight - 1 - j) / (screenHeight - 1) - 0.5f) * realHeight;
      pixelPosition = pixelPosition.move(right.mul(u)).move(up.mul(v));
      return pixelPosition;
   }

   protected List<Point3D> getPixelPositionSuperSampledList(int i, int j, int r) {
      final int count = getSuperSampledCount(r);
      final List<Point3D> pixelPositionList = new ArrayList<>(count);
      Point3D currentPixelPosition = null;

      final float du = getSuperSampledDU(r);
      final float dv = getSuperSampledDV(r);
      for (int k = -r; k <= r; k++) {
         for (int l = -r; l <= r; l++) {
            currentPixelPosition = new Point3D(position);
            float u = ((float) (i) / (screenWidth - 1) - 0.5f) * realWidth + k * du;
            float v = ((float) (screenHeight - 1 - j) / (screenHeight - 1) - 0.5f) * realHeight + l * dv;
            pixelPositionList.add(currentPixelPosition.move(right.mul(u)).move(up.mul(v)));
         }
      }

      return pixelPositionList;
   }

   /**
    * Vrací primární paprsek, který vychází z pixelu průmětny na souřadnicích <code>(i, j)</code>.
    * 
    * @param i
    *           Vertikální souřadnice pixelu v rastru průmětny.
    * @param j
    *           Horizontální souřadnice pixelu v rastru průmětny.
    * @return Primární paprsek příslušející danému pixelu.
    */
   public Ray getPrimaryRay(int i, int j) {
      Vector3 direction = getRayDirection(i, j);
      if (direction != null) {
         return new Ray(getRayOrigin(i, j), direction);
      } else {
         return null;
      }
   }

   public List<Ray> getPimaryRaySuperSampledList(int i, int j, int r) {
      final int count = AbstractCamera.getSuperSampledCount(r);
      final List<Point3D> rayOriginList = getRayOriginSuperSampledList(i, j, r);
      final List<Vector3> rayDirectionList = getRayDirectionSuperSampledList(i, j, r);
      if (rayDirectionList != null) {
         final List<Ray> primaryRayList = new ArrayList<>(count);
         Vector3 currentDirection = null;
         for (int k = 0; k < count; k++) {
            currentDirection = rayDirectionList.get(k);
            if (currentDirection != null) {
               primaryRayList.add(new Ray(rayOriginList.get(k), currentDirection));
            } else {
               primaryRayList.add(null);
            }
         }
         return primaryRayList;
      } else {
         return null;
      }
   }

   /**
    * Určuje směrový vertor primárního paprsku, který vychází z pixelu rastru
    * průmětny na souřadnicích<code>(i, j)</code>.
    * 
    * @param i
    *           Vertikální souřadnice pixelu v rastru průmětny.
    * @param j
    *           Horizontální souřadnice pixelu v rastru průmětny.
    * @return Směrový vektor paprsku příslušejícího danému pixelu.
    */
   protected abstract Vector3 getRayDirection(int i, int j);

   protected abstract List<Vector3> getRayDirectionSuperSampledList(int i, int j, int r);

   /**
    * Určuje výchozí bod primárního paprsku, který vychází z pixelu rastru
    * průmětny na souřadnicích<code>(i, j)</code>.
    * 
    * @param i
    *           Vertikální souřadnice pixelu v rastru průmětny.
    * @param j
    *           Horizontální souřadnice pixelu v rastru průmětny.
    * @return Výchozí bod paprsku příslušejícího danému pixelu.
    */
   protected abstract Point3D getRayOrigin(int i, int j);

   protected abstract List<Point3D> getRayOriginSuperSampledList(int i, int j, int r);

   /**
    * r=0 => 1x1<br/>
    * r=1 => 3x3<br/>
    * r=2 => 5x5<br/>
    * ...
    **/
   public static int getSuperSampledCount(int r) {
      return (2 * r + 1) * (2 * r + 1);
   }

   public float getSuperSampledDU(int r) {
      return realStepU / (3 * r + 1);
   }

   public float getSuperSampledDV(int r) {
      return realStepV / (3 * r + 1);
   }

   /**
    * Vrací pozici kamery.
    * 
    * @return Pozice kamery.
    */
   public Point3D getPosition() {
      return position;
   }

   /**
    * Nastavuje pozici kamery.
    * 
    * @param position
    *           Nová pozice kamery.
    */
   public void setPosition(Point3D position) {
      this.position = position;
   }

   /**
    * 
    * Vrací vektor pohledu kamery.
    * 
    * @return Vektro pohledu kamery.
    */
   public Vector3 getDirection() {
      return direction;
   }

   /**
    * Nastavuje vektor pohledu kamery.
    * 
    * @param direction
    *           Nový vektor pohledu kamery.
    */
   public void setDirection(Vector3 direction) {
      this.direction = direction;
   }

   /**
    * Vrací vektor, který definuje směr nahoru.
    * 
    * @return Vektor udávající směr nahoru.
    */
   public Vector3 getUp() {
      return up;
   }

   /**
    * Nastavuje vektor, který definuje směr nahoru.
    * 
    * @param up
    *           Nový vektor, který definuje směr nahoru.
    */
   public void setUp(Vector3 up) {
      this.up = up;
   }

   /**
    * Vrací vektor, který definuje směr vpravo.
    * 
    * @return Vektor, který definuje směr vpravo.
    */
   public Vector3 getRight() {
      return right;
   }

   /**
    * Nastavuje vektor, který definuje směr vpravo.
    * 
    * @param right
    *           Nový vektor, který definuje směr vpravo.
    */
   public void setRight(Vector3 right) {
      this.right = right;
   }

   /**
    * Vrací reálnou šířku průmětny ve scéně.
    * 
    * @return Reálná šířka průmětny ve scéně.
    */
   public float getRealWidth() {
      return realWidth;
   }

   /**
    * Nastavuje reálnou šířku průmětny ve scéně.
    * 
    * @param realWidth
    *           Nová reálná šířka průmětny ve scéně.
    */
   public void setRealWidth(float realWidth) {
      this.realWidth = realWidth;
   }

   /**
    * Vrací reálnou výšku průmětny ve scéně.
    * 
    * @return Reálná výška průmětny ve scéně.
    */
   public float getRealHeight() {
      return realHeight;
   }

   /**
    * Nastavuje reálnou výšku průmětny ve scéně.
    * 
    * @param realHeight
    *           Nová reálná výšku průmětny ve scéně.
    */
   public void setRealHeight(float realHeight) {
      this.realHeight = realHeight;
   }

   /**
    * Vrací šířku rastru průmětny (v pixelech).
    * 
    * @return Šířka rastru průmětny.
    */
   public int getScreenWidth() {
      return screenWidth;
   }

   /**
    * Nastavuje šířku rastru průmětny (v pixelech).
    * 
    * @param screenWidth
    *           Nová šířka rastru průmětny (v pixelech).
    */
   public void setScreenWidth(int screenWidth) {
      this.screenWidth = screenWidth;
   }

   /**
    * Vrací výšku rastru průmětny (v pixelech).
    * 
    * @return Výška rastru průmětny (v pixelech).
    */
   public int getScreenHeight() {
      return screenHeight;
   }

   /**
    * Nastavuje výšku rastru průmětny (v pixelech).
    * 
    * @param screenHeight
    *           Nová výška rastru průmětny (v pixelech).
    */
   public void setScreenHeight(int screenHeight) {
      this.screenHeight = screenHeight;
   }
}
