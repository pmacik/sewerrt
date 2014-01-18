/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující transformační matici trojrozměrného prostoru.
 * 
 * @author Pavel Macík
 * 
 */
public class Matrix4 {
   /**
    * Vlastní data matice.
    */
   protected float[][] data;

   /**
    * Vytvoří nulovou matici.
    */
   public Matrix4() {
      data = new float[4][4];
   }

   /**
    * Vytvoří matici, jako kopii dané matice.
    * 
    * @param matrix
    *           Kopírovaná matice.
    */
   public Matrix4(Matrix4 matrix) {
      this();
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            data[i][j] = matrix.data[i][j];
         }
      }
   }

   /**
    * @param i
    * @param j
    * @param value
    */
   public void setValue(int i, int j, float value) {
      if (i >= 0 && i <= 4 && j >= 0 && j <= 4) {
         data[i][j] = value;
      }
   }

   /**
    * @param i
    * @param j
    * @return
    */
   public float getValue(int i, int j) {
      if (i >= 0 && i <= 4 && j >= 0 && j <= 4) {
         return data[i][j];
      } else {
         // throw new GeometryException("Indices out of bounds: ( " + i + ",
         // " + j + ")");
         return 0;
      }
   }

   /**
    * @param m
    * @return
    */
   public Matrix4 add(Matrix4 m) {
      Matrix4 tmp = new Matrix4(this);
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            tmp.data[i][j] += m.data[i][j];
         }
      }
      return tmp;
   }

   /**
    * @param x
    * @return
    */
   public Matrix4 mul(float x) {
      Matrix4 tmp = new Matrix4(this);
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            tmp.data[i][j] *= x;
         }
      }
      return tmp;
   }

   /**
    * @return
    */
   public float[][] getData() {
      return data;
   }

   /**
    * @param newData
    */
   public void setData(float[][] newData) {
      data = newData;
   }
}
