package net.macsewer.graphics.sewerrt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.macsewer.graphics.sewerrt.camera.AbstractCamera;
import net.macsewer.graphics.sewerrt.camera.FisheyeCamera;
import net.macsewer.graphics.sewerrt.camera.PinholeCamera;
import net.macsewer.graphics.sewerrt.camera.PlainCamera;
import net.macsewer.graphics.sewerrt.geometry.ColorRGB;
import net.macsewer.graphics.sewerrt.geometry.Point3D;
import net.macsewer.graphics.sewerrt.geometry.Sphere;
import net.macsewer.graphics.sewerrt.geometry.TriMesh;
import net.macsewer.graphics.sewerrt.geometry.Vector3;
import net.macsewer.graphics.sewerrt.geometry.scene.Light;
import net.macsewer.graphics.sewerrt.geometry.scene.Scene;
import net.macsewer.graphics.sewerrt.gui.SewerRTFrame;
import net.macsewer.graphics.sewerrt.raytracer.RayTracer;

/**
 * Třída ukázkové Demo aplikace.
 * 
 * @author Pavel Macík
 * 
 */
public class Render {
   /**
    * Scéna s kuličkami na desce.
    */
   private Scene sceneKulickyNadDeskou;

   // /**
   // * Scéna typu město.
   // */
   // private Scene sceneMesto;

   /**
    * Plošná kamera.
    */
   private PlainCamera plainCamera;

   /**
    * Dírková kamera.
    */
   private PinholeCamera pinholeCamera;

   /**
    * Sférická kamera (rybí oko).
    */
   private FisheyeCamera fisheyeCamera;

   /**
    * Typ kamery.
    */
   private CameraType cameraType = CameraType.FISHEYE;

   /**
    * Typ kamery.
    * 
    * @author Pavel Macík
    * 
    */
   private enum CameraType {
      /**
       * Plošná kamera.
       */
      PLAIN,

      /**
       * Dírková kamera.
       */
      PINHOLE,

      /**
       * Sférická kamera - rybí oko.
       */
      FISHEYE
   };

   /**
    * Vytvoří instanci aplikace s danými atributy.
    * 
    * @param width
    *           Šířka rastru průmětny kamery.
    * @param height
    *           Výška rastru průmětny kamery.
    * @param cameraType
    *           Typ kamery.
    */
   public Render(int width, int height, CameraType cameraType) {

      // long pred, po;
      this.cameraType = cameraType;

      initSceneKulicky();
      // initSceneMesto();

      initCameras(width, height);

      render();

   }

   /**
    * Vykreslí (promítne) scénu do rastru průmětny pomocí raytraceru, a
    * definovaného typu kamery.
    */
   private void render() {
      long pred;
      long po;

      // String sceneName = "kulicky";
      Scene scene = sceneKulickyNadDeskou;

      RayTracer rt = new RayTracer();

      rt.setShadowMode(RayTracer.SHADOWS_ON);

      AbstractCamera camera;
      switch (cameraType) {
         case PLAIN:
            camera = plainCamera;
            break;
         case PINHOLE:
            camera = pinholeCamera;
            break;
         case FISHEYE:
         default:
            camera = fisheyeCamera;
      }

      // camera.setRealWidth(0.5f);
      // camera.setRealHeight(0.5f);

      // posun dozadu o 1
      // camera.setPosition(camera.getPosition().move(
      // camera.getDirection()
      // .normalized()
      // .mul(-1f)));

      // posun vlevo o 1
      // camera.setPosition(camera.getPosition().move(
      // camera.getRight().normalized().mul(-1f)));

      // zvetseni prumetny
      // camera.setRealWidth(4);
      // camera.setRealHeight(4);

      rt.setCamera(camera);
      rt.setRecursionDeep(2);

      System.out.println("Zacinam kreslit...");

      System.out.print(camera.getClass().getSimpleName());
      pred = System.currentTimeMillis();

      // rt.renderScene(scene);
      rt.renderSceneSuperSampled(scene, 1);

      po = System.currentTimeMillis();
      System.out.println(" " + (po - pred) + " ms");

      SewerRTFrame df = new SewerRTFrame(rt.getCanvas());
      df.setVisible(true);

      // saveImageToFile(rt.getCanvas(), "pinhole_45_big.bmp");

      // plosna
      // rt.setCamera(plainCamera);
      // pred = System.currentTimeMillis();
      // System.out.print("Plain camera... ");
      // rt.renderScene(scene);
      // po = System.currentTimeMillis();
      // System.out.println((po - pred) + " ms");
      // saveImageToFile(rt.getCanvas(), sceneName + "_plain.bmp");

      // dirkova
      // rt.setCamera(pinholeCamera);
      // pred = System.currentTimeMillis();
      // System.out.print("Pinhole camera... ");
      // rt.renderScene(scene);
      // po = System.currentTimeMillis();
      // System.out.println((po - pred) + " ms");
      // saveImageToFile(rt.getCanvas(), sceneName + "_pinhole.bmp");

      // fish-eye
      // rt.setCamera(pinholeCamera);
      // pred = System.currentTimeMillis();
      // System.out.print("Pinhole camera... ");
      // rt.renderScene(scene);
      // po = System.currentTimeMillis();
      // System.out.println((po - pred) + " ms");
      // saveImageToFile(rt.getCanvas(), sceneName + "_pinhole.bmp");

      // rt.setCamera(fisheyeCamera);
      //
      // System.out.println("Fish-eye camera... ");
      //
      // int max = 1000;
      // Point3D pos = new Point3D(0.2f, 2f, 0.5f);
      // fisheyeCamera.setPosition(pos);
      // DrzkaFrame df = new DrzkaFrame(rt.getCanvas());
      // df.setVisible(true);
      // float step = (float) 4 / max;
      // for (int i = 0; i < max; i++) {
      // // pos.setX(pos.getX());
      // pos.setY(pos.getY() - step);
      // // pos.setZ(pos.getZ());
      // System.out.print("Snímek " + (i + 1) + "/" + max + " ... ");
      // pred = System.currentTimeMillis();
      // rt.renderScene(scene);
      // po = System.currentTimeMillis();
      // System.out.println((po - pred) + " ms");
      // saveImageToFile(rt.getCanvas(), sceneName + "_fisheye_"
      // + ((i < 10) ? ("00") : (i < 100) ? "0" : "") + i + ".bmp");
      // df.repaint();
      // }

      // saveImageToFile(rt.getCanvas(), sceneName + "_fisheye.bmp");
      //
      // DrzkaFrame df = new DrzkaFrame(rt.getCanvas());
      // df.setVisible(true);

      // try {
      // ImageIO.write(rt.getCanvas(), "bmp", new File("fisheye.bmp"));
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
   }

   /**
    * Inicializuje jednotlivé kamery s danými rozměry rastru průmětny.
    * 
    * @param width
    *           Šířka rastru průmětny kamery.
    * @param height
    *           Výška rastru průmětny kamery.
    */
   private void initCameras(int width, int height) {
      Point3D cameraEyePosition = new Point3D(0.2f, 0.2f, 0.5f);
      Point3D cameraTarget = new Point3D(1, 0, 0);
      plainCamera = new PlainCamera(cameraEyePosition, cameraTarget, new Vector3(0, 0, 1), width, height);

      float pi_4 = 0.785398163f;

      pinholeCamera = new PinholeCamera(cameraEyePosition, cameraTarget, new Vector3(0, 0, 1f), width, height, pi_4);

      fisheyeCamera = new FisheyeCamera(cameraEyePosition, cameraTarget, new Vector3(0, 0, 1), width, height);

      // definice typu sférické kamery na 360˚
      // fisheyeCamera.setCameraType(FisheyeCamera.CAMERA_TYPE_360);
   }

   /**
    * Uloží daný rastr do daného souboru jako obrázek typu BMP.
    * 
    * @param img
    *           Rastr průmětny.
    * @param filePath
    *           Cesta k souboru obrázku, do kterého se má rastr uložit.
    */
   private void saveImageToFile(BufferedImage img, String filePath) {
      try {
         ImageIO.write(img, "bmp", new File(filePath));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // /**
   // *
   // */
   // private void initSceneMesto() {
   // sceneMesto = new Scene();
   //
   // SecureRandom srg = new SecureRandom();
   // srg.nextFloat();
   // // objekty
   //
   // // podlaha
   // TriMesh mesh = new TriMesh();
   // float p = 3;
   // float m = -3;
   // Point3D vp1, vp2, vp3;
   // vp1 = new Point3D(0, m, m);
   // vp3 = new Point3D(0, m, p);
   // vp2 = new Point3D(0, p, p);
   // mesh.addTriangle(vp1, vp2, vp3);
   // vp1 = new Point3D(0, m, m);
   // vp2 = new Point3D(0, p, m);
   // vp3 = new Point3D(0, p, p);
   // mesh.addTriangle(vp1, vp2, vp3);
   //
   // sceneMesto.addObject(mesh);
   //
   // // domy
   // // for (int i = 0; i < 1e2; i++) {
   // // sceneMesto.addObject(new BoxTriMesh(new Point3D(4 * (srg
   // // .nextFloat() - 0.5f), 4 * (srg.nextFloat() - 0.5f), 0),
   // // 0.1f, (float) i / 120 + 0.1f, 0.1f));
   // // }
   //
   // for (float i = -2f; i <= 2; i += 0.4f) {
   // for (float j = -2; j <= 2; j += 0.4f) {
   // float d = Math.abs(i * j) * 0.4f + 0.2f;
   // sceneMesto.addObject(new BoxTriMesh(
   // new Point3D(d / 2, (i /* * Math.random() */),
   // (j /* * Math.random() */)), 0.07f, 0.07f, d));
   // }
   // }
   //
   // // svetlo
   // sceneMesto.setAmbientLight(new ColorRGB(30, 30, 30));
   // sceneMesto.setLight(0, new Light(new Point3D(4, -2, 1.5f), 5f,
   // ColorRGB.white));
   // }

   /**
    * Vytvoří a inicializuje scénu s kuličkami. (Čtvercová plocha, na které je
    * rozmístěno 9 koulí (v matici 3x3). Nad deskou i kuličkami jsou umístěna 4
    * všesměrová světla (červené, zelené, modré a bílé). Ambientní světlo je v
    * nastaveno na hodnotu RGB = (20,&nbsp;20,&nbsp;20) a barva pozadí je černá
    * RGB = (0,&nbsp;0,&nbsp;0)).
    */
   private void initSceneKulicky() {
      sceneKulickyNadDeskou = new Scene();

      TriMesh mesh = new TriMesh();
      float p = 1;
      float m = -1;
      Point3D vp1, vp2, vp3;
      vp1 = new Point3D(m + 1, m, 0);
      vp3 = new Point3D(m + 1, p, 0);
      vp2 = new Point3D(p + 1, p, 0);
      mesh.addTriangle(vp1, vp2, vp3);
      vp1 = new Point3D(m + 1, m, 0);
      vp2 = new Point3D(p + 1, m, 0);
      vp3 = new Point3D(p + 1, p, 0);
      mesh.addTriangle(vp1, vp2, vp3);

      // vp1 = new Point3D(1, m, p);
      // vp2 = new Point3D(1, p, p);
      // vp3 = new Point3D(1, p, m);
      // mesh.addTriangle(vp1, vp2, vp3);
      // vp1 = new Point3D(1, p, m);
      // vp2 = new Point3D(1, m, m);
      // vp3 = new Point3D(1, m, p);
      // mesh.addTriangle(vp1, vp2, vp3);

      sceneKulickyNadDeskou.addObject(mesh);

      // scene.addObject(new Sphere(new Point3D(1.5f, 0, 0), 0.2f));
      float x, y, z, r;
      int a = 3;
      Sphere ss;
      // ss = new Sphere(new Point3D(0.5f, 0, 0), 0.2f);
      // ss.setDiffuseColor(ColorRGB.getRandomColor());
      // scene.addObject(ss);
      // ss = new Sphere(new Point3D(2, 1.5f, 0), 1.499999f);
      // ss.setDiffuseColor(ColorRGB.getRandomColor());
      // scene.addObject(ss);

      // float step = 1;
      for (int i = 0; i < a; i++) {
         for (int j = 0; j < a; j++) {
            // for (int k = 0; k < a; k++) {
            x = i * 0.5f + 0.5f;
            y = j * 0.5f - 0.5f;
            z = 0.1f;
            // if (i == 2 && j == 2)
            // continue;
            // x = 0.7f;
            // z = i * 0.5f - step;
            // y = j * 0.5f - step;

            r = 0.1f;
            ss = new Sphere(new Point3D(x, y, z), r);
            ss.setDiffuseColor(new ColorRGB(i * 70, j * 70, i * j * 20));
            // ss.setDiffuseColor(ColorRGB.getRandomColor());
            sceneKulickyNadDeskou.addObject(ss);
            // }
         }
      }
      ss = new Sphere(new Point3D(0.5f, 0, 0), 0.03f);
      ss.setDiffuseColor(ColorRGB.blue);
      sceneKulickyNadDeskou.addObject(ss);

      // Random gen = new Random(System.currentTimeMillis());
      // for (int i = 0; i < 100; i++) {
      // x = gen.nextInt(500) / 100f + 1f;
      // y = gen.nextInt(200) / 100f - 1f;
      // z = gen.nextInt(200) / 100f - 1f;
      // r = gen.nextInt(10) / 100f + 0.03f;
      // scene.addObject(new Sphere(new Point3D(x, y, z), r));
      // }

      // scene
      // .setLight(1, new Light(new Point3D(0, 0, 0), 0.5f,
      // ColorRGB.white));
      // scene.setLight(1, new Light(new Point3D(1, -1, 1), 0.5f, new
      // ColorRGB(
      // 255, 255, 255)));

      sceneKulickyNadDeskou.setAmbientLight(new ColorRGB(20, 20, 20));
      // scene.setAmbientLight(new ColorRGB(100, 100, 100));
      sceneKulickyNadDeskou.setBackgroundColor(ColorRGB.black);
      // sceneKulickyNadDeskou.setLight(0, new Light(new Point3D(0, 1, -1),
      // 0.4f, new ColorRGB(ColorRGB.red)));
      // sceneKulickyNadDeskou.setLight(1, new Light(new Point3D(0, 1, 1),
      // 0.4f,
      // new ColorRGB(ColorRGB.green)));
      // sceneKulickyNadDeskou.setLight(2, new Light(new Point3D(0, -1, 1),
      // 0.4f, new ColorRGB(ColorRGB.blue)));
      // sceneKulickyNadDeskou.setLight(3, new Light(new Point3D(0, -1, -1),
      // 0.4f, new ColorRGB(ColorRGB.white)));
      sceneKulickyNadDeskou.setLight(0, new Light(new Point3D(1, -1, 1), 0.4f, new ColorRGB(ColorRGB.red)));
      sceneKulickyNadDeskou.setLight(1, new Light(new Point3D(1, 1, 1), 0.4f, new ColorRGB(ColorRGB.green)));
      sceneKulickyNadDeskou.setLight(2, new Light(new Point3D(0, 1, 1), 0.4f, new ColorRGB(ColorRGB.blue)));
      sceneKulickyNadDeskou.setLight(3, new Light(new Point3D(0, -1, 1), 0.4f, new ColorRGB(ColorRGB.white)));

   }

   /**
    * Spouštěcí metoda programu - vstupní bod programu. Zpracuje parametry z
    * příkazové řádky a na základě nich vytvoří instanci Demo aplikace. Program
    * se pouští následujícím příkazem: <br >
    * <code>Render &lt;screen width&gt; &lt;screen height&gt; &lt;plain|pinhole|fisheye&gt;</code><br />
    * ,
    * kde <br />
    * &lt;screen width&gt; .... šířka rastru průmětny <br />
    * &lt;screen height&gt; .... výška rastru průmětny <br />
    * &lt;plain|pinhole|fisheye&gt; .... jedna z hodnot definující typ kamery <br />
    * .
    * 
    * @param args
    *           Parametry příkazové řádky.
    */
   public static void main(String[] args) {
      if (args.length == 3) {
         int w, h;
         CameraType cameraType = CameraType.FISHEYE;
         w = Integer.valueOf(args[0]);
         h = Integer.valueOf(args[1]);

         if (args[2].equals("plain")) {
            cameraType = CameraType.PLAIN;
         } else if (args[2].equals("pinhole")) {
            cameraType = CameraType.PINHOLE;
         } else if (args[2].equals("fisheye")) {
            cameraType = CameraType.FISHEYE;
         }
         new Render(w, h, cameraType);
         return;
      }
      System.out.println("Usage: Render <screen width> <screen height> <plain|pinhole|fisheye>");

   }
}
