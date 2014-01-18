/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry.scene;

import java.util.Vector;

import net.macsewer.graphics.sewerrt.geometry.AbstractObject;
import net.macsewer.graphics.sewerrt.geometry.ColorRGB;

/**
 * Třída reprezentující scénu a její popis. Obsahuje definici objektů (těles) ve
 * scéně, barvu pozadí a ambientního světla, stejně jako definici světel ve
 * scéně.
 * 
 * @author Pavel Macík
 * 
 */
public class Scene {
   /**
    * Kolekce objektů ve scéně.
    */
   private Vector<AbstractObject> objects;

   /**
    * Kolekce světel ve scéně.
    */
   private Light[] lights;

   /**
    * Barva ambientního světla.
    */
   private ColorRGB ambientLight;

   /**
    * Barva pozadí.
    */
   private ColorRGB backgroundColor;

   /**
    * Vytvoří prázdnou scénu. Nastaví barvu ambientního světla i pozadí na
    * černou ({@link ColorRGB#black}).
    */
   public Scene() {
      objects = new Vector<AbstractObject>(10, 5);
      lights = new Light[8];
      ambientLight = ColorRGB.black;
      backgroundColor = ColorRGB.black;
   }

   /**
    * Vrací kolekci objektů, které se nachází ve scéně.
    * 
    * @return Kolekce objektů, které se nachází ve scéně.
    */
   public Vector<AbstractObject> getObjects() {
      return objects;
   }

   /**
    * Změní kolekci objektů ve scéně.
    * 
    * @param objects
    *           Nová kolekce objektů ve scéně.
    */
   public void setObjects(Vector<AbstractObject> objects) {
      this.objects = objects;
   }

   /**
    * Vrátí světlo s daným indexem - ve scéně může být až 8 světel.
    * 
    * @param index
    *           Index světla (0-7).
    * @return Pokud světlo s caným indexem ve scéně existuje, metoda jej vrátí,
    *         jinak vrátí <code>null</code>.
    */
   public Light getLight(int index) {
      if (index >= 0 && index < 8) {
         return lights[index];
      } else {
         return null;
      }
   }

   /**
    * Nastaví světlo s daným indexem ve scéně. Pokud světlo s daným indexem
    * existuje, je nahrazeno novým.
    * 
    * @param index
    *           Index vkládaného světla.
    * @param light
    *           Světlo vkládané do scény
    */
   public void setLight(int index, Light light) {
      lights[index] = light;
   }

   /**
    * Vloží do scény nový objekt.
    * 
    * @param object
    *           Vkládaný objekt.
    */
   public void addObject(AbstractObject object) {
      objects.add(object);
   }

   /**
    * Vrátí barvu ambientního světla.
    * 
    * @return Barva ambientního světla.
    */
   public ColorRGB getAmbientLight() {
      return ambientLight;
   }

   /**
    * Nastaví barvu ambientního světla.
    * 
    * @param ambientLight
    *           Nová barva ambientního světla.
    */
   public void setAmbientLight(ColorRGB ambientLight) {
      this.ambientLight = ambientLight;
   }

   /**
    * Vrací barvu pozadí scény.
    * 
    * @return Barva pozadí scény.
    */
   public ColorRGB getBackgroundColor() {
      return backgroundColor;
   }

   /**
    * Nastaví barvu pozadí scény.
    * 
    * @param backgroundColor
    *           Nová barva pozadí scény.
    */
   public void setBackgroundColor(ColorRGB backgroundColor) {
      this.backgroundColor = backgroundColor;
   }

}
