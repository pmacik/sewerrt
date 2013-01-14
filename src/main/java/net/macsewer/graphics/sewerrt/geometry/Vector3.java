/**
 * 
 */
package net.macsewer.graphics.sewerrt.geometry;

/**
 * Třída reprezentující trojrozměrný vektor afinního prostoru.
 * 
 * @author Pavel Macík
 * 
 */
public class Vector3 extends Float3 {

	/**
	 * Vytvoří vektor jako kopii daného vektoru.
	 * 
	 * @param vector
	 *            Kopírovaný vektor.
	 */
	public Vector3(Vector3 vector) {
		super(vector);
	}

	/**
	 * Vytvoří vektor se souřadnicemi daného obecného třísložkového vektoru.
	 * 
	 * @param f
	 *            Obecný třísložkový vektor.
	 */
	public Vector3(Float3 f) {
		super(f);
	}

	/**
	 * Vytvoří nulový vektor.
	 */
	public Vector3() {
		super();
	}

	/**
	 * Vytvoří vektor z dvou třísložkových vekorů (jako bodů).
	 * 
	 * @param a
	 *            Výchozí bod.
	 * @param b
	 *            Koncový bod.
	 */
	public Vector3(Float3 a, Float3 b) {
		super(b.x - a.x, b.y - a.y, b.z - a.z);
	}

	/**
	 * Vytvoří vektor s danými souřadnicemi.
	 * 
	 * @param x
	 *            Souřadnice X.
	 * @param y
	 *            Souřadnice Y.
	 * @param z
	 *            Souřadnice Z.
	 */
	public Vector3(float x, float y, float z) {
		super(x, y, z);
	}

	/**
	 * Vrátí vektor, který je dán součtem vektorů.
	 * 
	 * @param vector
	 *            Sčítaný vektoru.
	 * @return Vektor, který je dán součtem.
	 */
	public Vector3 add(Vector3 vector) {
		Vector3 tmp = new Vector3(this);
		tmp.x += vector.x;
		tmp.y += vector.y;
		tmp.z += vector.z;
		return tmp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.macsewer.graphics.sewerrt.geometry.Float3#mul(float)
	 */
	@Override
	public Vector3 mul(float m) {
		Float3 f = super.mul(m);
		return new Vector3(f.x, f.y, f.z);
	}

	/**
	 * Vrací součin daného vektoru a transformační <code>m</code>.
	 * 
	 * @param matrix
	 *            Transformační matice.
	 * 
	 * @return Obecný vektor, který je dán součinem vektoru a matice.
	 */
	public Vector3 mul(Matrix4 matrix) {
		Vector3 tmp = new Vector3();
		float[][] md = matrix.getData();
		tmp.x = x * md[0][0] + y * md[1][0] + z * md[2][0];
		tmp.y = x * md[0][1] + y * md[1][1] + z * md[2][1];
		tmp.z = x * md[0][2] + y * md[1][2] + z * md[2][2];
		return tmp;
	}

	/**
	 * Vrací součin daného vektoru a transformační <code>m</code>.
	 * 
	 * @param matrix
	 *            Transformační matice.
	 * 
	 * @return Obecný vektor, který je dán součinem vektoru a matice.
	 */
	public Vector3 mul(Matrix3 matrix) {
		Vector3 tmp = new Vector3();
		float[][] md = matrix.getData();
		tmp.x = x * md[0][0] + y * md[1][0] + z * md[2][0];
		tmp.y = x * md[0][1] + y * md[1][1] + z * md[2][1];
		tmp.z = x * md[0][2] + y * md[1][2] + z * md[2][2];
		return tmp;
	}

	/**
	 * Vrací skalární součin vektorů.
	 * 
	 * @param vector
	 *            Druhý vektor pro výpočet.
	 * @return Skalární součin vektorů.
	 */
	public float dot(Vector3 vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	/**
	 * Vrací vektor, který je dán vektorovým součinem vektorů.
	 * 
	 * @param vector
	 *            Druhý vektor pro výpočet.
	 * @return Vektor, který je dán vektorovým součinem vektorů.
	 */
	public Vector3 cross(Vector3 vector) {
		return new Vector3(y * vector.z - z * vector.y, z * vector.x - x
				* vector.z, x * vector.y - y * vector.x);
	}

	/**
	 * Vrací velikost (normu) vektoru.
	 * 
	 * @return Velikost vektoru.
	 */
	public float lenght() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Vrací normalizovaný vektor.
	 * 
	 * @return Normalzovaný vektor.
	 */
	public Vector3 normalized() {
		float len = lenght();
		return new Vector3(x / len, y / len, z / len);
	}

	/**
	 * Vrací vektor, který je zrcadlově otočen vůči danému vektoru, který
	 * představuje osu zrcadlení.
	 * 
	 * @param axis
	 *            Osa zrcadlení.
	 * @return Zrcadlený vektor.
	 */
	public Vector3 reflected(Vector3 axis) {
		return axis.mul(2 * dot(axis)).add(this.mul(-1));
	}
}
