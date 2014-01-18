/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * @author Pavel Macík
 * 
 */
public class Matrix3 {
   private float[][] data;

   public Matrix3() {
      data = new float[3][3];
   }

   public float getValue(int i, int j) {
      if (i >= 0 && i <= 3 && j >= 0 && j <= 3) {
         return data[i][j];
      } else {
         // throw new GeometryException("Indices out of bounds: ( " + i + ",
         // " + j + ")");
         return 0;
      }
   }

   public float[][] getData() {
      return data;
   }

}
