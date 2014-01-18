/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

import java.util.Vector;

/**
 * Třída reprezentující objekt typu "Mesh" složený z trojúhelníků.
 * 
 * @author Pavel Macík
 * 
 */
public class TriMesh extends AbstractObject {
   /**
    * Kolekce trojúhelníků, ze kterých je Mesh složen.
    */
   private Vector<Triangle> triangles;

   /**
    * Vytvoří prázdný objekt typu Mesh.
    */
   public TriMesh() {
      triangles = new Vector<Triangle>(10, 5);
   }

   /**
    * Vloží do objektu trojúhelník, jehož vrcholy jsou dány parametry. Barva
    * trojúhelníku je bílá.
    * 
    * @param vp1
    *           Vrchol trojúhelníku.
    * @param vp2
    *           Vrchol trojúhelníku.
    * @param vp3
    *           Vrchol trojúhelníku.
    */
   public void addTriangle(Point3D vp1, Point3D vp2, Point3D vp3) {
      this.addTriangle(vp1, vp2, vp3, ColorRGB.white);
   }

   /**
    * Vloží do objektu trojúhelník, jehož vrcholy jsou dány parametry.
    * 
    * @param vp1
    *           Vrchol trojúhelníku.
    * @param vp2
    *           Vrchol trojúhelníku.
    * @param vp3
    *           Vrchol trojúhelníku.
    * @param color
    *           Barva trojúhelníku.
    */
   public void addTriangle(Point3D vp1, Point3D vp2, Point3D vp3, ColorRGB color) {
      Vector3 normal = Triangle.computeNormal(vp1, vp2, vp3);
      Vertex v1 = new Vertex(vp1, normal, color, new Vector3());
      Vertex v2 = new Vertex(vp2, normal, color, new Vector3());
      Vertex v3 = new Vertex(vp3, normal, color, new Vector3());
      Triangle t = new Triangle(v1, v2, v3);
      t.setDiffueColor(color);
      triangles.add(t);
      updateBoundingSphere();
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.geometry.AbstractObject#_crossRay(net.macsewer.graphics.sewerrt.geometry.Ray)
    */
   @Override
   protected Vertex _crossRay(Ray ray) {
      int count = triangles.size();
      Triangle currentTriangle;
      Vertex vertex = null, currentVertex = null;
      float depth = Float.MAX_VALUE;
      float currentDepth;

      for (int k = 0; k < count; k++) {
         currentTriangle = triangles.get(k);
         currentVertex = currentTriangle.crossRay(ray);

         if (currentVertex != null) {
            currentDepth = new Vector3(ray.getOrigin(), currentVertex.getPosition()).lenght();
            if (currentDepth < depth) {
               depth = currentDepth;
               vertex = currentVertex;
            }
         }
      }
      return vertex;
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.macsewer.graphics.sewerrt.geometry.AbstractObject#updateBoundingSphere()
    */
   @Override
   protected void updateBoundingSphere() {
      float x = 0, y = 0, z = 0;
      int count = triangles.size();
      Point3D currentVertexPosition;
      Point3D[] vertexPositions = new Point3D[count * 3];
      for (int i = 0, j = 0; i < count; i++) {
         currentVertexPosition = triangles.get(i).vertexA.getPosition();
         x += currentVertexPosition.x;
         y += currentVertexPosition.y;
         z += currentVertexPosition.z;
         vertexPositions[j++] = currentVertexPosition;

         currentVertexPosition = triangles.get(i).vertexB.getPosition();
         x += currentVertexPosition.x;
         y += currentVertexPosition.y;
         z += currentVertexPosition.z;
         vertexPositions[j++] = currentVertexPosition;

         currentVertexPosition = triangles.get(i).vertexC.getPosition();
         x += currentVertexPosition.x;
         y += currentVertexPosition.y;
         z += currentVertexPosition.z;
         vertexPositions[j++] = currentVertexPosition;
      }
      count *= 3;
      x /= count;
      y /= count;
      z /= count;
      bSphere.setPivot(new Point3D(x, y, z));
      pivot = bSphere.getPivot();
      float newRadius = 0;
      float currentVertexDistance = 0;
      for (int i = 0; i < count; i++) {
         currentVertexPosition = vertexPositions[i];
         currentVertexDistance = new Vector3(currentVertexPosition, bSphere.getPivot()).lenght();
         if (currentVertexDistance > newRadius) {
            newRadius = currentVertexDistance;
         }
      }
      bSphere.setRadius(newRadius);

   }

   /**
    * Vrací kolekci trojúhelníků, ze kterých je složen povrch objektu.
    * 
    * @return Kolekce trojúhelníků.
    */
   public Vector<Triangle> getTriangles() {
      return triangles;
   }

   /**
    * Mění kolekci trojúhelníků, ze kterých je složen povrch objektu.
    * 
    * @param triangles
    *           Nová kolekce trojúhelníků.
    */
   public void setTriangles(Vector<Triangle> triangles) {
      this.triangles = triangles;
   }
}
