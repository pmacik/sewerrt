/**
 * 
 */
package net.macsewer.graphics.sewerrt.camera;

import java.util.ArrayList;
import java.util.List;

import net.macsewer.graphics.sewerrt.geometry.Point3D;
import net.macsewer.graphics.sewerrt.geometry.Vector3;

/**
 * Třída reprezentující dírkovou kameru.
 * 
 * @author Pavel Macík
 * 
 */
public class PinholeCamera extends AbstractCamera {

   /**
    * Zorný úhel kamery. (v radiánech)
    */
   private float fieldOfView;

   /**
    * Vzdálenost štěrbiny od průmětny.
    */
   private float pinholeDistance;

   /**
    * Bod popisující polohu štěrbiny.
    */
   private Point3D pinhole;

   /**
    * Vytvoří kameru s danými atributy.
    * 
    * @param position
    *           Pozice kamery ve scéně.
    * @param target
    *           Bod, na který je kamera zaměřena.
    * @param up
    *           Vektor určující směr nahoru.
    * @param pinholeDistance
    *           Vzdálenost štěrbiny od průmětny.
    * @param screenWidth
    *           Šířka rastru průmětny.
    * @param screenHeight
    *           Výška rastru průmětny.
    */
   public PinholeCamera(Point3D position, Point3D target, Vector3 up, float pinholeDistance, int screenWidth, int screenHeight) {
      this(position, new Vector3(position, target), up, pinholeDistance, screenWidth, screenHeight);
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
    * @param pinholeDistance
    *           Vzdálenost štěrbiny od průmětny.
    * @param screenWidth
    *           Šířka rastru průmětny.
    * @param screenHeight
    *           Výška rastru průmětny.
    */
   public PinholeCamera(Point3D position, Vector3 direction, Vector3 up, float pinholeDistance, int screenWidth, int screenHeight) {
      super(position, direction, up, screenWidth, screenHeight);
      this.pinholeDistance = pinholeDistance;
      computePinhole();
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
    * @param fieldOfView
    *           Zorný úhel kamery.
    */
   public PinholeCamera(Point3D position, Vector3 direction, Vector3 up, int screenWidth, int screenHeight, float fieldOfView) {
      this(position, direction, up, 1f, screenWidth, screenHeight);
      this.fieldOfView = fieldOfView;
      pinholeDistance = (float) (Math.sqrt(realWidth * realWidth + realHeight * realHeight) / (2 * Math.tan(this.fieldOfView / 2)));
      computePinhole();
   }

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
    * @param fieldOfView
    *           Zorný úhel kamery.
    */
   public PinholeCamera(Point3D position, Point3D target, Vector3 up, int screenWidth, int screenHeight, float fieldOfView) {
      this(position, target, up, 1f, screenWidth, screenHeight);
      this.fieldOfView = fieldOfView;
      pinholeDistance = (float) (Math.sqrt(realWidth * realWidth + realHeight * realHeight) / (2 * Math.tan(fieldOfView / 2)));
      computePinhole();
   }

   /**
    * Přepočítá pozici štěrbiny na základě pozice kamery, směru pohledu a
    * vzdálenosti štěrbiny od průmětny.
    */
   private void computePinhole() {
      pinhole = position.move(direction.normalized().mul(pinholeDistance));
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#getRayDirection(int,
    * int)
    */
   @Override
   protected Vector3 getRayDirection(int i, int j) {
      Vector3 tmp = new Vector3(getRayOrigin(screenWidth - 1 - i, screenHeight - 1 - j), pinhole).normalized();
      return tmp;
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#setPosition(net.macsewer.graphics.sewerrt.geometry.Point3D)
    */
   @Override
   public void setPosition(Point3D position) {
      // TODO Auto-generated method stub
      super.setPosition(position);
      computePinhole();
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#setRealWidth(float)
    */
   @Override
   public void setRealWidth(float realWidth) {
      // TODO Auto-generated method stub
      super.setRealWidth(realWidth);
      setFieldOfView(fieldOfView);
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#setRealWidth(float)
    */
   @Override
   public void setRealHeight(float realHeight) {
      // TODO Auto-generated method stub
      super.setRealHeight(realHeight);
      setFieldOfView(fieldOfView);
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#getRayOrigin(int,
    * int)
    */
   @Override
   protected Point3D getRayOrigin(int i, int j) {
      return getPixelPosition(i, j);
   }

   /**
    * Nastaví zorný úhel kamery.
    * 
    * @param fieldOfView
    *           Nový zorný úhel kamery.
    */
   public void setFieldOfView(float fieldOfView) {
      this.fieldOfView = fieldOfView;
      pinholeDistance = (float) (Math.sqrt(realWidth * realWidth + realHeight * realHeight) / (2 * Math.tan(fieldOfView / 2)));
      computePinhole();
   }

   /**
    * Vrátí zorný úhel kamey.
    * 
    * @return Zorný úhel kamery.
    */
   public float getFieldOfView() {
      return fieldOfView;
   }

   @Override
   protected List<Vector3> getRayDirectionSuperSampledList(int i, int j, int r) {
      final int count = AbstractCamera.getSuperSampledCount(r);
      final List<Vector3> directionList = new ArrayList<>(count);
      for (int k = 0; k < count; k++) {
         directionList.add(new Vector3(getRayOrigin(screenWidth - 1 - i, screenHeight - 1 - j), pinhole).normalized());
      }
      return directionList;
   }

   @Override
   protected List<Point3D> getRayOriginSuperSampledList(int i, int j, int r) {
      return getPixelPositionSuperSampledList(i, j, r);
   }

}
