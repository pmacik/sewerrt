/**
 * 
 */
package net.macsewer.graphics.sewerrt.camera;

import net.macsewer.graphics.sewerrt.geometry.Point3D;
import net.macsewer.graphics.sewerrt.geometry.Vector3;

/**
 * @author Pavel Macík
 * 
 */
public class PlainCamera extends AbstractCamera {

	/**
	 * Vytvoří plošnou kameru s danými atributy.
	 * 
	 * @param position
	 *            Pozice kamery ve scéně.
	 * @param target
	 *            Bod, na který je kamera zaměřena.
	 * @param up
	 *            Vektor určující směr nahoru.
	 * @param screenWidth
	 *            Šířka rastru průmětny.
	 * @param screenHeight
	 *            Výška rastru průmětny.
	 */
	public PlainCamera(Point3D position, Point3D target, Vector3 up,
			int screenWidth, int screenHeight) {
		super(position, target, up, screenWidth, screenHeight);
	}

	/**
	 * Vytvoří plošnou kameru s danými atributy.
	 * 
	 * @param position
	 *            Pozice kamery ve scéně.
	 * @param direction
	 *            Vektor určující směr pohledu kamery.
	 * @param up
	 *            Vektor určující směr nahoru.
	 * @param screenWidth
	 *            Šířka rastru průmětny.
	 * @param screenHeight
	 *            Výška rastru průmětny.
	 */
	public PlainCamera(Point3D position, Vector3 direction, Vector3 up,
			int screenWidth, int screenHeight) {
		super(position, direction, up, screenWidth, screenHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#getRayDirection(int,
	 *      int)
	 */
	@Override
	protected Vector3 getRayDirection(int i, int j) {
		return direction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.macsewer.graphics.sewerrt.camera.AbstractCamera#getRayOrigin(int,
	 *      int)
	 */
	@Override
	protected Point3D getRayOrigin(int i, int j) {
		return getPixelPosition(i, j);
	}

}
