/**
 * 
 */
package net.macsewer.graphics.sewerrt.camera;

import java.util.ArrayList;
import java.util.List;

import net.macsewer.graphics.sewerrt.geometry.Point3D;
import net.macsewer.graphics.sewerrt.geometry.Vector3;

/**
 * Třída reprezentující sférickou kameru - rybí oko.
 * 
 * @author Pavel Macík
 * 
 */
public class FisheyeCamera extends AbstractCamera {

   /**
    * Příznak 180° kamery.
    */
   public static final int CAMERA_TYPE_180 = 0;

   /**
    * Příznak 360° kamery.
    */
   public static final int CAMERA_TYPE_360 = 1;

   /**
    * Typ kamery. Může nabývat hodnot:<br />
    * {@link FisheyeCamera#CAMERA_TYPE_180},<br />
    * {@link FisheyeCamera#CAMERA_TYPE_360}.
    */
   private int cameraType;

   /**
    * Vytvoří 180° sférickou kameru s danými atributy.
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
   public FisheyeCamera(Point3D position, Point3D target, Vector3 up, int screenWidth, int screenHeight) {
      super(position, target, up, screenWidth, screenHeight);
      cameraType = CAMERA_TYPE_180;
   }

   /**
    * Vytvoří 180° sférickou kameru s danými atributy.
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
   public FisheyeCamera(Point3D position, Vector3 direction, Vector3 up, int screenWidth, int screenHeight) {
      super(position, direction, up, screenWidth, screenHeight);
      cameraType = CAMERA_TYPE_180;
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#getPixelPosition(int,
    * int)
    */
   @Override
   protected Point3D getRayOrigin(int i, int j) {
      return position;
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#getRayDirection(int,
    * int)
    */
   @Override
   protected Vector3 getRayDirection(final int i, final int j) {

      float u = ((float) (i) / (screenWidth - 1) - 0.5f) * 2;
      float v = ((float) (screenHeight - 1 - j) / (screenHeight - 1) - 0.5f) * 2;
      float vzd_2 = (u * u + v * v);

      if (vzd_2 > 1) {
         return null;
      } else {
         float vel;
         int pi = i, pj = j;

         Point3D pixelPosition;
         switch (cameraType) {
            case CAMERA_TYPE_360:
               if (vzd_2 <= 0.25f) {
                  vel = (float) Math.sqrt(1 - 4 * vzd_2);
               } else {
                  float vzd = (float) Math.sqrt(vzd_2);
                  float vzd_m_1 = vzd - 1;
                  vel = (float) (-Math.sqrt(1 - 4 * (vzd_m_1 * vzd_m_1)));
                  u = (u >= -0.5 && u <= 0.5) ? (2 * u) : (Math.signum(u) * 2 * (1 - Math.abs(u)));
                  v = (v >= -0.5 && v <= 0.5) ? (2 * v) : (Math.signum(v) * 2 * (1 - Math.abs(v)));

               }

               pi = (int) (((u + 1) * (screenWidth - 1)) / 2);
               int h_m_1 = screenHeight - 1;
               pj = (int) (h_m_1 - ((v + 1) * h_m_1) / 2);
               break;
            case CAMERA_TYPE_180:
            default:
               vel = (float) ((Math.sqrt(1 - vzd_2)));
         }
         pixelPosition = getPixelPosition(pi, pj);

         Point3D p = pixelPosition.move(direction.mul(vel));
         return new Vector3(position, p);
      }
   }

   @Override
   protected List<Vector3> getRayDirectionSuperSampledList(final int i, final int j, final int r) {
      final int count = AbstractCamera.getSuperSampledCount(r);
      final List<Vector3> directionList = new ArrayList<>(count);
      final List<Point3D> pixelPositionList = getPixelPositionSuperSampledList(i, j, r);
      final float du = getSuperSampledDU(r);
      final float dv = getSuperSampledDV(r);
      int pi, pj;
      int ind = 0;
      for (int k = -r; k <= r; k++) {
         for (int l = -r; l <= r; l++, ind++) {
            float u = ((float) (i) / (screenWidth - 1) - 0.5f) * realWidth + k * du;
            float v = ((float) (screenHeight - 1 - j) / (screenHeight - 1) - 0.5f) * realHeight + l * dv;
            float vzd_2 = (u * u + v * v);

            if (vzd_2 > 1) {
               directionList.add(null);
            } else {
               float vel;

               Point3D pixelPosition;
               pi = i;
               pj = j;
               switch (cameraType) {
                  case CAMERA_TYPE_360:
                     if (vzd_2 <= 0.25f) {
                        vel = (float) Math.sqrt(1 - 4 * vzd_2);
                     } else {
                        float vzd = (float) Math.sqrt(vzd_2);
                        float vzd_m_1 = vzd - 1;
                        vel = (float) (-Math.sqrt(1 - 4 * (vzd_m_1 * vzd_m_1)));
                        u = (u >= -0.5 && u <= 0.5) ? (2 * u) : (Math.signum(u) * 2 * (1 - Math.abs(u)));
                        v = (v >= -0.5 && v <= 0.5) ? (2 * v) : (Math.signum(v) * 2 * (1 - Math.abs(v)));

                     }

                     pi = (int) (((u + 1) * (screenWidth - 1)) / 2);
                     int h_m_1 = screenHeight - 1;
                     pj = (int) (h_m_1 - ((v + 1) * h_m_1) / 2);
                     break;
                  case CAMERA_TYPE_180:
                  default:
                     vel = (float) ((Math.sqrt(1 - vzd_2)));
               }
               pixelPosition = pixelPositionList.get(ind);

               Point3D p = pixelPosition.move(direction.mul(vel));
               directionList.add(new Vector3(position, p));
            }
         }
      }
      return directionList;
   }

   @Override
   protected List<Point3D> getRayOriginSuperSampledList(int i, int j, int r) {
      final int count = AbstractCamera.getSuperSampledCount(r);
      final List<Point3D> originList = new ArrayList<>(count);
      for (int k = 0; k < count; k++) {
         originList.add(position);
      }
      return originList;
   }

   /**
    * Vrátí typ kamery (180/360)°.
    * 
    * @return {@link FisheyeCamera#CAMERA_TYPE_180}, pokud se jedná o 180°
    *         kameru, nebo {@link FisheyeCamera#CAMERA_TYPE_360}, pokud je
    *         kamera 360°.
    */
   public int getCameraType() {
      return cameraType;
   }

   /**
    * Nastaví typ kamery (180˚, nebo 360˚)
    * 
    * @param cameraType
    *           Typ kamery. Může nabývat hodnot:<br />
    *           {@link FisheyeCamera#CAMERA_TYPE_180},<br />
    *           {@link FisheyeCamera#CAMERA_TYPE_360}.
    */
   public void setCameraType(int cameraType) {
      if (cameraType == CAMERA_TYPE_180 || cameraType == CAMERA_TYPE_360) {
         this.cameraType = cameraType;
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#setRealHeight(float)
    */
   @Override
   public void setRealHeight(float realHeight) {
      // nop
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#setRealWidth(float)
    */
   @Override
   public void setRealWidth(float realWidth) {
      // nop
   }

}
