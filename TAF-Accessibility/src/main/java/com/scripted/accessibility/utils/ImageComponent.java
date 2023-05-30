package com.scripted.accessibility.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The Class ImageComponent initiates new image and gets the ImagePath.
 */
public class ImageComponent extends Component {

    /**
     * serialVersionUID number.
     */
    private static final long serialVersionUID = -5723969281826162308L;

    private static final String IMAGE_RESOURCES_FOLDER = "/Resources/images/";

    /** The img. */
    private BufferedImage img;

    /*
     * (non-Javadoc)
     *
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    @Override
    public void paint(final Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    /**
     * Instantiates a new image component.
     *
     * @param imageName
     *            the image name
     */
    public ImageComponent(final String imageName) {
        try {
            final String imagePath = getImagePath(imageName);
            img = ImageIO.read(new File(imagePath));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.Component#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(200, 200);
        } else {
            return new Dimension(img.getWidth(), img.getHeight());
        }
    }

    /**
     * Gets the image path.
     *
     * @param imageName
     *            the image name
     * @return the image path
     */
    private String getImagePath(final String imageName) {
        return System.getProperty("user.dir") + IMAGE_RESOURCES_FOLDER + imageName;
    }
}
