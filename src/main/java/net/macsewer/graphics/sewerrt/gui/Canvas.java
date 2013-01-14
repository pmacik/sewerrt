/**
 * 
 */
package net.macsewer.graphics.sewerrt.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Třída reprezentující plátno, na které se kreslí promítnutý obraz scény.
 * 
 * @author Pavel Macík
 * 
 */
public class Canvas extends JPanel {
	/**
	 * ID pro serializaci.
	 */
	private static final long serialVersionUID = -7012215337119128070L;

	/**
	 * Vytvoří plátno s daným obrazovým rastrem.
	 * 
	 * @param canvas
	 *            Rastr průmětny.
	 */
	public Canvas(BufferedImage canvas) {
		this.canvas = canvas;
	}

	/**
	 * Rastr průmětny.
	 */
	private BufferedImage canvas;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(canvas, 0, 0, null);
	}

}
