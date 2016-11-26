/*
 * This work is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 */
package com.github.errantlinguist.awt;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.github.errantlinguist.FilePath;

/**
 * <p>
 * Convenience class to create and optionally save to a file a
 * {@link BufferedImage} of an area on the screen. Generally there are four
 * different scenarios. Create an image of:
 * </p>
 *
 * <ol>
 * <li>an entire component</li>
 * <li>a region of the component</li>
 * <li>the entire desktop</li>
 * <li>a region of the desktop</li>
 * </ol>
 *
 * <p>
 * The first two use the Swing {@link Component#paint(java.awt.Graphics)} method
 * to draw the component image to the {@code BufferedImage}. The latter two use
 * the AWT {@link Robot} to create the {@code BufferedImage}.
 * </p>
 *
 * <p>
 * The created image can then be saved to a file by using the
 * {@link #writeImage(BufferedImage, String)} method. The type of file must be
 * supported by the
 * {@link ImageIO#write(java.awt.image.RenderedImage, String, File)} write
 * method.
 * </p>
 *
 * <p>
 * Although this class was originally designed to create an image of a component
 * on the screen, it can be used to create an image of components not displayed
 * on a GUI: Behind the scenes the component will be given a size and the
 * component will be laid out. The default size will be the preferred size of
 * the component, although you can invoke the {@code Component.setSize(...)}
 * methods on the component before invoking a {@code createImage(...)} method.
 * The default functionality should work in most cases. However the only
 * foolproof way to get a image to is make sure the component has been added to
 * a realized window with code something like the following:
 * </p>
 *
 * <code>
 * 	JFrame frame = new JFrame();
 * 	frame.setContentPane(someComponent);
 * 	frame.pack();
 * 	createImage(someComponent);
 * </code>
 *
 */
public final class ScreenImage {

	/**
	 * A {@link Set} of {@link ImageIO#getWriterFileSuffixes() all possible
	 * output files suffixes}.
	 */
	private static final Set<String> WRITER_FILE_SUFFIXES = new HashSet<>(
			Arrays.asList(ImageIO.getWriterFileSuffixes()));

	/**
	 * Convenience method to create a BufferedImage of the desktop
	 *
	 * @return image the image for the desktop
	 * @exception AWTException
	 *                see Robot class constructors
	 * @exception IOException
	 *                if an error occurs during writing
	 */
	public static final BufferedImage createDesktopImage() throws AWTException, IOException {
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		final Rectangle region = new Rectangle(0, 0, d.width, d.height);
		return createImage(region);
	}

	/**
	 * Create a BufferedImage for AWT components.
	 *
	 * @param component
	 *            AWT component to create image from
	 *
	 * @return image the image for the given region
	 *
	 * @exception AWTException
	 *                see Robot class constructors
	 */
	public static final BufferedImage createImage(final Component component) throws AWTException {
		assert component != null;
		final Point p = new Point(0, 0);
		SwingUtilities.convertPointToScreen(p, component);
		final Rectangle region = component.getBounds();
		region.x = p.x;
		region.y = p.y;
		return createImage(region);
	}

	/**
	 * Create a BufferedImage for Swing components. The entire component will be
	 * captured to an image.
	 *
	 * @param component
	 *            Swing component to create image from
	 *
	 * @return image the image for the given region
	 */
	public static final BufferedImage createImage(final JComponent component) {
		assert component != null;
		Dimension d = component.getSize();

		if (d.width == 0 || d.height == 0) {
			d = component.getPreferredSize();
			component.setSize(d);
		}

		final Rectangle region = new Rectangle(0, 0, d.width, d.height);
		return createImage(component, region);
	}

	/**
	 * Create a BufferedImage for Swing components. All or part of the component
	 * can be captured to an image.
	 *
	 * @param component
	 *            Swing component to create image from
	 *
	 * @param region
	 *            The region of the component to be captured to an image
	 *
	 * @return image the image for the given region
	 */
	public static final BufferedImage createImage(final JComponent component, final Rectangle region) {
		assert component != null;
		assert region != null;
		// Make sure the component has a size and has been laid out.
		// (necessary check for components not added to a realized frame)

		if (!component.isDisplayable()) {
			Dimension d = component.getSize();

			if (d.width == 0 || d.height == 0) {
				d = component.getPreferredSize();
				component.setSize(d);
			}

			layoutComponent(component);
		}

		final BufferedImage result = new BufferedImage(region.width, region.height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = result.createGraphics();

		// Paint a background for non-opaque components,
		// otherwise the background will be black
		if (!component.isOpaque()) {
			g2d.setColor(component.getBackground());
			g2d.fillRect(region.x, region.y, region.width, region.height);
		}

		g2d.translate(-region.x, -region.y);
		component.paint(g2d);
		g2d.dispose();

		return result;
	}

	/**
	 * Create a BufferedImage from a rectangular region on the screen. This will
	 * include Swing components JFrame, JDialog and JWindow which all extend
	 * from Component, not JComponent.
	 *
	 * @param region
	 *            region on the screen to create image from
	 * @return image the image for the given region
	 * @exception AWTException
	 *                see Robot class constructors
	 */
	public static final BufferedImage createImage(final Rectangle region) throws AWTException {
		return new Robot().createScreenCapture(region);
	}

	/**
	 * Write a BufferedImage to a File.
	 *
	 * @param image
	 *            image to be written
	 * @param filename
	 *            name of file to be created
	 * @exception IOException
	 *                if an error occurs during writing
	 */
	public static final void writeImage(final BufferedImage image, final String filename) throws IOException {
		final String filenameExtension = FilePath.getFilenameExtension(filename);

		if (WRITER_FILE_SUFFIXES.contains(filenameExtension)) {
			ImageIO.write(image, filenameExtension, new File(filename));
		} else {
			final String message = "Unknown writer file suffix (" + filenameExtension + ")";
			throw new IOException(message);
		}
	}

	/**
	 * Recursively lays out {@link Container#getComponents() the child
	 * components} of a given {@link Component} object.
	 *
	 * @param component
	 *            The {@code Component} object to lay out the child components
	 *            of.
	 */
	private static final void layoutComponent(final Component component) {
		assert component != null;
		synchronized (component.getTreeLock()) {
			component.doLayout();

			if (component instanceof Container) {
				for (final Component child : ((Container) component).getComponents()) {
					layoutComponent(child);
				}
			}
		}
	}

	private ScreenImage() {
		// Avoid instantiation
	}

}
