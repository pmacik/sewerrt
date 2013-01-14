/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující bod v trojrozmětném prostoru.
 * 
 * @author Pavel Macík
 * 
 */
public class Point3D extends Float3 {

	/**
	 * Homogenní souřadnice bodu.
	 */
	protected float w;

	/**
	 * Vytvoří bod v počátku soustavy souřadnic (0, 0, 0)
	 */
	public Point3D() {
		super();
		w = 1f;
	}

	/**
	 * Vytvoří bod na daných souřadnicích.
	 * 
	 * @param x
	 *            Souřadnice X.
	 * @param y
	 *            Souřadnice Y.
	 * @param z
	 *            Souřadnice Z.
	 */
	public Point3D(float x, float y, float z) {
		super(x, y, z);
		w = 1f;
	}

	/**
	 * Vytvoří bod na daných souřadnicích.
	 * 
	 * @param x
	 *            Souřadnice X.
	 * @param y
	 *            Souřadnice Y.
	 * @param z
	 *            Souřadnice Z.
	 * @param w
	 *            Homogenní souřadnice.
	 */
	public Point3D(float x, float y, float z, float w) {
		super(x, y, z);
		this.w = w;
	}

	/**
	 * Vytvoří bod na souřadnicích určených daným třísložkovým vektorem.
	 * 
	 * @param f
	 *            Obecný třísložkový vektor.
	 */
	public Point3D(Float3 f) {
		super(f);
		w = 1f;
	}

	/**
	 * Vrací hodnotu homogenní souřadnice W.
	 * 
	 * @return Souřadnice W.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Nastavuje hodnotu homogenní souřadnice W.
	 * 
	 * @param w
	 *            Nová homogenní souřadnice W.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Vrací bod, který je dán součinem daného bodu a transformační matice.
	 * 
	 * @param matrix
	 *            Transformační matice.
	 * @return Bod transformovaný transformační maticí.
	 */
	public Point3D mul(Matrix4 matrix) {
		Point3D tmp = new Point3D();
		float[][] md = matrix.getData();
		tmp.x = x * md[0][0] + y * md[1][0] + z * md[2][0] + w * md[3][0];
		tmp.y = x * md[0][1] + y * md[1][1] + z * md[2][1] + w * md[3][1];
		tmp.z = x * md[0][2] + y * md[1][2] + z * md[2][2] + w * md[3][2];
		tmp.w = x * md[0][3] + y * md[1][3] + z * md[2][3] + w * md[3][3];
		return tmp;
	}

	/**
	 * Vrací bod, který vznikne posunem daného bodu o daný vektor.
	 * 
	 * @param vector
	 *            Vektor posunu.
	 * @return Posunutý bod.
	 */
	public Point3D move(Vector3 vector) {
		Point3D tmp = new Point3D(this);
		tmp.x += vector.x;
		tmp.y += vector.y;
		tmp.z += vector.z;
		return tmp;
	}
}
