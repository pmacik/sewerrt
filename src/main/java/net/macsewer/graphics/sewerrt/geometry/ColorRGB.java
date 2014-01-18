/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * @author Pavel Macík
 * 
 */
public class ColorRGB extends Float3 {

   public static ColorRGB white = new ColorRGB(255, 255, 255);

   public static ColorRGB red = new ColorRGB(255, 0, 0);

   public static ColorRGB green = new ColorRGB(0, 255, 0);

   public static ColorRGB blue = new ColorRGB(0, 0, 255);

   public static ColorRGB black = new ColorRGB(0, 0, 0);

   public static ColorRGB yellow = new ColorRGB(255, 255, 0);

   public static ColorRGB gray = new ColorRGB(127, 127, 127);

   /**
	 * 
	 */
   public ColorRGB() {
      super();
      // TODO Auto-generated constructor stub
   }

   /**
    * @param x
    * @param y
    * @param z
    */
   public ColorRGB(float x, float y, float z) {
      super(x, y, z);
      // TODO Auto-generated constructor stub
   }

   /**
    * @param f
    */
   public ColorRGB(Float3 f) {
      super(f);
      // TODO Auto-generated constructor stub
   }

   public int toInt() {
      int r = (int) x;
      int g = (int) y;
      int b = (int) z;
      return (r << 16) + (g << 8) + b;
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.geometry.Float3#mul(float)
    */
   @Override
   public ColorRGB mul(float m) {
      Float3 f = super.mul(m);
      return new ColorRGB(f.x, f.y, f.z);
   }

   public ColorRGB add(ColorRGB color) {
      return new ColorRGB(x + color.x, y + color.y, z + color.z);
   }

   public static ColorRGB saturateRGB(ColorRGB color) {
      return new ColorRGB(Math.min(255, Math.max(color.x, 0)), Math.min(255, Math.max(color.y, 0)), Math.min(255, Math.max(color.z, 0)));
   }

   /**
    * @return
    */
   public static ColorRGB getRandomColor() {

      return new ColorRGB((float) Math.random() * 255, (float) Math.random() * 255, (float) Math.random() * 255);
   }
}
