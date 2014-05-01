package model;

import java.awt.image.BufferedImage;

public class Fractal {

	private static final int BLACK = 0;
	// Constants
	final double xPixel = 800;
	final double yPixel = 600;

	// Variables
	BufferedImage bi;
	double xLength;
	double yLength;
	double xOrigin;
	double yOrigin;
	double magnitude;
	double max_iteration = 1000.0;
	double magnification = 1.0;
	double prevXOrigin;
	double prevYOrigin;
	double multiplier = 1.0;
}
