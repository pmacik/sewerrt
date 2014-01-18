/**
 * 
 */
package net.macsewer.graphics.sewerrt.raytracer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import net.macsewer.graphics.sewerrt.camera.AbstractCamera;
import net.macsewer.graphics.sewerrt.geometry.AbstractObject;
import net.macsewer.graphics.sewerrt.geometry.ColorRGB;
import net.macsewer.graphics.sewerrt.geometry.Ray;
import net.macsewer.graphics.sewerrt.geometry.Vector3;
import net.macsewer.graphics.sewerrt.geometry.Vertex;
import net.macsewer.graphics.sewerrt.geometry.scene.Light;
import net.macsewer.graphics.sewerrt.geometry.scene.Scene;

/**
 * Třída reprezentující vykreslovací stroj. Jedná se o implementaci algoritmu
 * sledování paprsku.
 * 
 * @author Pavel Macík
 * 
 */
public class RayTracer {
   /**
    * Vykreslovaná scéna.
    */
   private Scene scene;

   /**
    * Kamera, pomocí které se obraz scény promítá.
    */
   private AbstractCamera camera;

   /**
    * Rastr průměnty kamery.
    */
   private BufferedImage canvas;

   /**
    * Hloubka rekurze pro algoritmus sledování paprsku.
    */
   private int recursionDeep = 0;

   /**
    * Příznak zapnutého stínování.
    */
   public static final int SHADOWS_ON = 0;

   /**
    * Příznak vypnutého stínování.
    */
   public static final int SHADOWS_OFF = 1;

   /**
    * Režim stínování. Může nabývat hodnot {@link RayTracer#SHADOWS_ON} nebo {@link RayTracer#SHADOWS_OFF}. Výchozí hodnota je {@link RayTracer#SHADOWS_OFF}.
    */
   private int shadowsMode = SHADOWS_OFF;

   private int superSamplingRadius = 0;

   /**
    * Vytvoří raytracer s danou kamerou.
    * 
    * @param camera
    *           Kamera, pomocí které se promítá obraz scény na průmětnu.
    */
   public RayTracer(AbstractCamera camera) {
      setCamera(camera);
   }

   /**
    * Vytvoří raytracer bez kamery.
    */
   public RayTracer() {
      this(null);
   }

   /**
    * Nastaví raytraceru kameru.
    * 
    * @param camera
    *           Nová kamera.
    */
   public void setCamera(AbstractCamera camera) {
      this.camera = camera;
      if (camera != null) {
         canvas = new BufferedImage(camera.getScreenWidth(), camera.getScreenHeight(), BufferedImage.TYPE_INT_RGB);
      } else {
         canvas = null;
      }
   }

   /**
    * Vykreslí (promítne) danou scénu do rastru prostřednictvím přiřazené
    * kamery.
    * 
    * @param scene
    *           Promítaná scéna.
    */
   public void renderScene(Scene scene) {
      setScene(scene);
      renderScene();
   }

   /**
    * Vykreslí (promítne) přiřazenou scénu do rastru prostřednictvím přiřazené
    * kamery.
    */
   public void renderScene() {

      int screenWidth = camera.getScreenWidth();
      int screenHeight = camera.getScreenHeight();
      int pixelColor = ColorRGB.black.toInt();
      Ray currentPrimaryRay;
      ColorRGB finalColor;

      int background = scene.getBackgroundColor().toInt();

      for (int j = 0; j < screenHeight; j++) {
         for (int i = 0; i < screenWidth; i++) {
            currentPrimaryRay = camera.getPrimaryRay(i, j);
            pixelColor = background;
            if (currentPrimaryRay != null) {
               finalColor = traceRay(currentPrimaryRay, null, recursionDeep);

               if (finalColor != null) {
                  pixelColor = ColorRGB.saturateRGB(finalColor).toInt();
               }
            }
            canvas.setRGB(i, j, pixelColor);
         }
      }
   }

   public void renderSceneSuperSampled(Scene scene) {
      renderSceneSuperSampled(scene, 0);
   }

   public void renderSceneSuperSampled(Scene scene, int superSamplingRadius) {
      setScene(scene);
      this.superSamplingRadius = superSamplingRadius;
      renderSceneSuperSampled();
   }

   public void renderSceneSuperSampled() {
      int screenWidth = camera.getScreenWidth();
      int screenHeight = camera.getScreenHeight();

      int count = AbstractCamera.getSuperSampledCount(superSamplingRadius);
      List<Ray> currentPrimaryRayList = new ArrayList<>(count);
      Ray currentPrimaryRaySuperSample = null;
      ColorRGB finalColor;
      ColorRGB currentColor;
      float rSum = 0;
      float gSum = 0;
      float bSum = 0;

      int background = scene.getBackgroundColor().toInt();
      int pixelColor = background;

      int innerCount = 0;
      for (int j = 0; j < screenHeight; j++) {
         for (int i = 0; i < screenWidth; i++) {
            currentPrimaryRayList = camera.getPimaryRaySuperSampledList(i, j, superSamplingRadius);

            pixelColor = background;
            rSum = 0;
            gSum = 0;
            bSum = 0;
            innerCount = 0;
            for (int k = 0; k < count; k++) {
               currentPrimaryRaySuperSample = currentPrimaryRayList.get(k);
               if (currentPrimaryRaySuperSample != null) {
                  currentColor = traceRay(currentPrimaryRaySuperSample, null, recursionDeep);
                  rSum += currentColor.getX();
                  gSum += currentColor.getY();
                  bSum += currentColor.getZ();
                  innerCount++;
               }
            }

            finalColor = new ColorRGB(rSum / innerCount, gSum / innerCount, bSum / innerCount);

            if (finalColor != null) {
               pixelColor = ColorRGB.saturateRGB(finalColor).toInt();
            }
            canvas.setRGB(i, j, pixelColor);
         }

      }
   }

   /**
    * Počítá barvu pixelu na základě algoritmu sledování paprsku s danými
    * atributy.
    * 
    * @param ray
    *           Sledovaný paprsek.
    * @param origin
    *           Vrchol, ze kterého paprsek vychází (v případě, že se jedná o
    *           sekundární paprsek). Pokud se jedná o primární paprsek, má
    *           hodnotu <code>null</code>.
    * @param deep
    *           Hloubka rekurze algoritmu.
    * @return Barva pixelu.
    */
   public ColorRGB traceRay(Ray ray, Vertex origin, int deep) {
      Vertex currentVertex = getCrossedVertex(ray, origin);
      if (currentVertex == null) {
         return scene.getBackgroundColor();
      }
      // vypocet osvetleni
      ColorRGB ambient = scene.getAmbientLight(), diffuse, specular, finalColor = scene.getBackgroundColor();
      float diffuseIntensity, specularIntensity, lightDistance, cos;
      float h = 100f;
      Light currentLight;
      Vector3 lightVector, cameraVector, reflectVector, normalVector;
      Ray currentShadowRay;
      Vertex tmpVertex;

      cameraVector = new Vector3(currentVertex.getPosition(), ray.getOrigin()).normalized();
      normalVector = currentVertex.getNormal().normalized();
      if (cameraVector.dot(normalVector) > 0) {
         finalColor = ambient;
         for (int lightIndex = 0; lightIndex < 8; lightIndex++) {
            currentLight = scene.getLight(lightIndex);
            if (currentLight != null) {

               lightVector = new Vector3(currentVertex.getPosition(), currentLight.getPosition());
               lightDistance = lightVector.lenght();

               // zjištění stínu
               if (shadowsMode == SHADOWS_ON) {
                  currentShadowRay = new Ray(currentVertex.getPosition(), lightVector);
                  tmpVertex = getCrossedVertex(currentShadowRay, currentVertex);
                  if (tmpVertex != null) {
                     // || tmpVertex.getOwner() == currentVertex
                     // .getOwner()) {

                     continue;
                  }
               }

               lightVector = lightVector.normalized();

               // D
               cos = normalVector.dot(lightVector);
               diffuseIntensity = ((cos > 0) ? cos : 0);
               diffuse = currentVertex.getDiffuse().mul(diffuseIntensity);
               // S
               // r = 2*(l.n)*n - l
               reflectVector = lightVector.reflected(normalVector);
               cos = (float) Math.pow(Math.min(1, Math.max(0, reflectVector.dot(cameraVector))), h);
               specularIntensity = cos;

               specular = currentLight.getColor().mul(specularIntensity);

               // final
               finalColor = finalColor.add(diffuse.add(specular).mul(currentLight.getIntensity() / (lightDistance * lightDistance)));
            }
         }

         if (deep > 0) {
            Vector3 reflectedVector = cameraVector.reflected(normalVector);
            Ray reflectedRay = new Ray(currentVertex.getPosition(), reflectedVector);

            ColorRGB reflectedColor = traceRay(reflectedRay, currentVertex, deep - 1);

            if (reflectedColor != null) {
               finalColor = finalColor.add(reflectedColor.mul(h / 200));
            }
         }
      }

      return finalColor;
   }

   /**
    * Vrátí průsečík (vrchol) daného paprsku s nejbližším objektem ve scéně.
    * 
    * @param ray
    *           Paprsek.
    * @param origin
    *           Výchozí vrchol (v případě, že se jedná o sekundární paprsek).
    *           Pokud se jedná o primární paprsek, má hodnotu <code>null</code>.
    * @return
    */
   private Vertex getCrossedVertex(Ray ray, Vertex origin) {
      Vector<AbstractObject> objects = scene.getObjects();
      int objectCount = objects.size();
      AbstractObject currentObject;
      Vertex vertex = null, currentVertex = null;
      float depth = Float.MAX_VALUE;
      float currentDepth;

      for (int k = 0; k < objectCount; k++) {
         currentObject = objects.get(k);
         currentVertex = currentObject.crossRay(ray);

         if (currentVertex != null) {
            if (origin != null && (currentVertex.getOwner() == origin.getOwner())) {
               continue;
            }
            currentDepth = new Vector3(ray.getOrigin(), currentVertex.getPosition()).lenght();
            if (currentDepth < depth) {
               depth = currentDepth;
               vertex = currentVertex;
            }
         }
      }
      return vertex;
   }

   /**
    * Vrací rastr průmětny.
    * 
    * @return Rastr průmětny.
    */
   public BufferedImage getCanvas() {
      return canvas;
   }

   /**
    * Nastavuje novou scénu.
    * 
    * @param scene
    *           Nová scéna.
    */
   public void setScene(Scene scene) {
      this.scene = scene;
   }

   /**
    * Vrací režim stínů.
    * 
    * @return Režim stínů. Pokud jsou stíny zapnuty, vrátí {@link RayTracer#SHADOWS_ON}, jinak vrátí {@link RayTracer#SHADOWS_OFF}.
    */
   public int getShadowMode() {
      return shadowsMode;
   }

   /**
    * Nastavuje režim stínů.
    * 
    * @param shadowMode
    *           Režim stínování.
    * @see RayTracer#shadowsMode.
    */
   public void setShadowMode(int shadowMode) {
      shadowsMode = shadowMode;
   }

   /**
    * Vrací hloubku rekurze.
    * 
    * @return Hloubka rekurze.
    */
   public int getRecursionDeep() {
      return recursionDeep;
   }

   /**
    * Nastavuje hloubku rekurze.
    * 
    * @param recursionDeep
    *           Nová hloubka rekurze.
    */
   public void setRecursionDeep(int recursionDeep) {
      this.recursionDeep = recursionDeep;
   }

   /**
    * Vrací scénu.
    * 
    * @return Scéna.
    */
   public Scene getScene() {
      return scene;
   }

   /**
    * Vrací přiřazenou kameru.
    * 
    * @return Přiřazená kamera.
    */
   public AbstractCamera getCamera() {
      return camera;
   }

   /**
    * Vrací režim stínování.
    * 
    * @return Režim stínování. Může nabývat hodnot {@link RayTracer#SHADOWS_ON},
    *         pokud jsou stíny zapnuty, jinak vrací {@link RayTracer#SHADOWS_OFF}.
    */
   public int getShadowsMode() {
      return shadowsMode;
   }

   public int getSuperSamplingRadius() {
      return superSamplingRadius;
   }

   public void setSuperSamplingRadius(int superSamplingRadius) {
      this.superSamplingRadius = superSamplingRadius;
   }

}
