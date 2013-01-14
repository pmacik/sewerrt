/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

import java.util.Vector;

/**
 * Třída reprezentující kvádr, jako objekt typu Mesh.
 * 
 * @author Pavel Macík
 * 
 */
public class BoxTriMesh extends TriMesh {

	/**
	 * Šířka kvádru (ve směru osy Y).
	 */
	private float width;

	/**
	 * Výška kvádru (ve směru osy Z).
	 */
	private float height;

	/**
	 * Hloubka kvádru (ve směru osy X).
	 */
	private float depth;

	/**
	 * Vytvoří kvádr s danými atributy.
	 * 
	 * @param width
	 *            Šířka kvádru (ve směru osy Y).
	 * @param height
	 *            Výška kvádru (ve směru osy Z).
	 * @param depth
	 *            Hloubka kvádru (ve směru osy X).
	 */
	public BoxTriMesh(float width, float height, float depth) {
		super();
		this.width = width;
		this.height = height;
		this.depth = depth;
		compute();
	}

	/**
	 * Vytvoří kvádr s danými atributy.
	 * 
	 * @param pivot
	 *            Střed základny kvádru.
	 * @param width
	 *            Šířka kvádru (ve směru osy Y).
	 * @param height
	 *            Výška kvádru (ve směru osy Z).
	 * @param depth
	 *            Hloubka kvádru (ve směru osy X).
	 */
	public BoxTriMesh(Point3D pivot, float width, float height, float depth) {
		super();
		this.pivot = pivot;
		this.width = width;
		this.height = height;
		this.depth = depth;
		compute();
	}

	/**
	 * Přepočítá vrcholy trojúhelníků na základě rozměrů kvádru.
	 */
	private void compute() {
		Vector<Triangle> triangles = getTriangles();

		Vertex vA = new Vertex(new Point3D(pivot.x + depth / 2, pivot.y - width
				/ 2, pivot.z), null, ColorRGB.white, new Float3());
		vA.setOwner(this);

		Vertex vB = new Vertex(new Point3D(pivot.x + depth / 2, pivot.y + width
				/ 2, pivot.z), null, ColorRGB.white, new Float3());
		vB.setOwner(this);

		Vertex vC = new Vertex(new Point3D(pivot.x - depth / 2, pivot.y + width
				/ 2, pivot.z), null, ColorRGB.white, new Float3());
		vC.setOwner(this);

		Vertex vD = new Vertex(new Point3D(pivot.x - depth / 2, pivot.y - width
				/ 2, pivot.z), null, ColorRGB.white, new Float3());
		vD.setOwner(this);

		Vertex vE = new Vertex(new Point3D(pivot.x + depth / 2, pivot.y - width
				/ 2, pivot.z + height), null, ColorRGB.white, new Float3());
		vE.setOwner(this);

		Vertex vF = new Vertex(new Point3D(pivot.x + depth / 2, pivot.y + width
				/ 2, pivot.z + height), null, ColorRGB.white, new Float3());
		vF.setOwner(this);

		Vertex vG = new Vertex(new Point3D(pivot.x - depth / 2, pivot.y + width
				/ 2, pivot.z + height), null, ColorRGB.white, new Float3());
		vG.setOwner(this);

		Vertex vH = new Vertex(new Point3D(pivot.x - depth / 2, pivot.y - width
				/ 2, pivot.z + height), null, ColorRGB.white, new Float3());
		vH.setOwner(this);

		triangles.clear();

		triangles.add(new Triangle(vA, vB, vE));
		triangles.add(new Triangle(vE, vB, vF));

		triangles.add(new Triangle(vD, vA, vH));
		triangles.add(new Triangle(vH, vA, vE));

		triangles.add(new Triangle(vB, vC, vF));
		triangles.add(new Triangle(vF, vC, vG));

		triangles.add(new Triangle(vC, vD, vG));
		triangles.add(new Triangle(vG, vD, vH));

		triangles.add(new Triangle(vA, vD, vB));
		triangles.add(new Triangle(vB, vD, vC));

		triangles.add(new Triangle(vH, vE, vF));
		triangles.add(new Triangle(vH, vF, vG));

		updateBoundingSphere();
	}
}
