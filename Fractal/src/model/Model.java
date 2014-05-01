package model;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Model {
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

	public void calculateNewOrigin(int xClick, int yClick) {
		double xRatio = (double) xClick / xPixel;
		double yRatio = (double) yClick / yPixel;
		System.out.println("yClick: " + yClick);

		double deltaX = xRatio * xLength;
		double deltaY = yRatio * yLength;

		double xClickPt = xOrigin + deltaX;
		double yClickPt = yOrigin - deltaY;
		
		prevXOrigin = xOrigin;
		prevYOrigin = yOrigin;
		
		xOrigin = xClickPt - xLength * 0.5*0.75;
		yOrigin = yClickPt + yLength * 0.5*0.75;
	}
	
	
	public void undo(){
		xOrigin = prevXOrigin;
		yOrigin = prevYOrigin;
		xLength = xLength/0.75;
		yLength = yLength/0.75;
	}

	static double calcIteration(double x, double y, double x0, double y0,
			double xtemp, double max_iteration) {
		double iteration = 0;
		while (((x * x + y * y) < 2 * 2) && (iteration < max_iteration)) {
			xtemp = x * x - y * y + x0;
			y = 2 * x * y + y0;
			x = xtemp;
			iteration = iteration + 1;
		}

		return iteration;
	}

	public void updateMagnification() {
		magnification = magnification * 0.75;
		xLength = xLength * 0.75;
		yLength = yLength * 0.75;

	}

	public double getMagniciation() {
		return magnification;
	}

	public void reRender() {
		Color color;
		double x0 = 0;
		double y0 = 0;
		double x = 0;
		double y = 0;
		double xtemp = 0;
		double iteration = 0.0;

		for (double Px = 0; Px < xPixel; Px++)
			for (double Py = 0; Py < yPixel; Py++) {
				// x0 = scaled x coordinate of pixel (should be scaled to lie
				// somewhere in the Mandelbrot X scale (-2.5, 1))
				// y0 = scaled y coordinate of pixel (should be scaled to lie
				// somewhere in the Mandelbrot Y scale (-1, 1))
				x0 = ((Px / xPixel) * xLength + xOrigin);
				y0 = ((Py / yPixel) * yLength - yOrigin);
				

				// Reset Variables
				x = x0;
				y = y0;
				xtemp = 0;
				iteration = 0;

				// Calculate escape time
				iteration = calcIteration(x, y, x0, y0, xtemp, max_iteration);

				String colorScheme = "hsbTest";
				
				if (colorScheme == "bands"){
				// Assign color to escape time
				double value = (16581375 - ((iteration / max_iteration) *16581375));
				value = (value * multiplier) % 2147483647; //3.151592
				color = new Color((int) value);
				bi.setRGB((int) Px, (int) Py,  color.getRGB());
				}
				else if (colorScheme == "hsbTest"){
					//double value = (16581375 - ((iteration / max_iteration) *16581375));
					//value = (value * multiplier) % 2147483647; //3.151592
					//color = new Color((int) value);
					if (iteration == max_iteration || iteration <15)
						bi.setRGB((int) Px, (int) Py, 0);
					else
						bi.setRGB((int) Px, (int) Py,  Color.HSBtoRGB((float) (1.0f-iteration/256f), 1.0f, 1.0f));
						
				}
				else if(colorScheme == "blueSmooth"){
				      //Z = Z*Z +C; iter_count ++;    // a couple of extra iterations helps
				      //Z = Z*Z +C; iter_count ++;    // decrease the size of the error term.
				     
				      
				      double modulus = Math.sqrt((x * x) + (y * y));;
				      double mu = iteration - (Math.log (Math.log (modulus)))/ Math.log (2.0);
				      double value = (mu%1);
				      value = value*10000000;
				      //value = value%255;
				      color = new Color ((int)mu);
					bi.setRGB((int) Px, (int) Py,  color.getRGB());
				}
				else if(colorScheme == "new1Smooth"){
				      //Z = Z*Z +C; iter_count ++;    // a couple of extra iterations helps
				      //Z = Z*Z +C; iter_count ++;    // decrease the size of the error term.
				     
				      
				      double modulus = Math.sqrt((x * x) + (y * y));;
				      double mu = iteration - (Math.log (Math.log (modulus)))/ Math.log (2.0);
				      double value = (mu%10);
				      value = value*100;
				      int r = Math.abs((int)value%255);
				      value = value%100;
				      value = value*100;
				      int g = Math.abs((int)value%255);
				      value = value%100;
				      value = value*100;
				      int b = Math.abs((int)value%255);
				      
				      color = new Color (r,g,b);
					bi.setRGB((int) Px, (int) Py,  color.getRGB());
				}
	
				else if (colorScheme == "badSmooth") {
					if (iteration < max_iteration) {
						//System.out.println(iteration);
						double zn = Math.sqrt((x * x) + (y * y));
						double nu = ((Math.log(Math.log(zn) / Math.log(1000))/ Math.log(2)));
						iteration = Math.abs(iteration + 1.0 - nu);
						
//						if (iteration > 999999999)
//							iteration = 1000;
							
					}

					// Map colors onto full RGB Scale
				//	double value1 = (iteration / max_iteration) * 255.0;
				//	double value2 = ((iteration+1) / max_iteration) * 255.0;
					//System.out.println(iteration + ": " + value1 + ", iteration + 1: " + value2);
					
					//double value1 = ((16581375 - v1Ratio) * 3.151592) % 16581375;
					//double value2 = ((16581375 - v2Ratio) * 3.151592) % 16581375;

					
					
					
//					double tmp;
//					if (value1 > value2) {
//						tmp = (Math.floor(value1) - Math.floor(value2)) / 2.0;
//						color = new Color((int) (Math.floor(value2) + tmp));
//					} else {
//						tmp = (Math.floor(value2) - Math.floor(value1)) / 2.0;
//						color = new Color((int) (Math.floor(value1) + tmp));
//					}
//					color = new Color((int)value2, (int)value2, (int) value1);
//					
//					bi.setRGB((int) Px, (int) Py,  color.getRGB());
					
					
					bi.setRGB((int)Px, (int)Py, Color.HSBtoRGB(1.0f,1.0f,(float) iteration));
					
				}
				else if (colorScheme == "newSmooth"){
					double nsmooth = iteration + 1 - Math.log(Math.log((iteration/200)%1))/Math.log(2);
					Color.HSBtoRGB((float)( 0.05f + 10 *nsmooth) ,0.6f,1.0f);
					bi.setRGB((int) Px, (int) Py, (int) nsmooth);
				}
				else if (colorScheme == "testSmooth"){
					//new
					double ans = Math.min(Math.abs(Py/Px),2);
					
					float h;
					float s;
					float b;
					
					h = 1.00f;
					s = 1.00f;//(float) (Py/400);
					b = -999.0f;
					
					bi.setRGB((int)Px, (int)Py, Color.HSBtoRGB(h,s,b));
					
						
				}
				// Assign color to pixel in image
				
			}
	}

	public BufferedImage getImage() {
		return bi;
	}
	
	public double getXCenter(){
		return xOrigin + xLength*.5;
	}
	
	public double getYCenter(){
		return yOrigin - yLength*.5;
	}
	
	public double getMagnfication(){
		return magnification;
		
	}
	
	public void reset(){
		xOrigin = -2.5;
		yOrigin = 1.5;
		prevXOrigin = -2.5;
		prevYOrigin = 1.5;
		xLength = 4.0;
		yLength = 3.0;
		magnitude = 1.0;
		magnification = 1.0;

	}

	public Model() {

		// Variables

		bi = new BufferedImage((int) (xPixel), (int) (yPixel),
				BufferedImage.TYPE_INT_RGB);
		xOrigin = -2.5;
		yOrigin = 1.5;
		prevXOrigin = -2.5;
		prevYOrigin = 1.5;
		xLength = 4.0;
		yLength = 3.0;
		magnitude = 1.0;
		magnification = 1.0;

		reRender();

	}
	
	public void newMultiplier(){
		Random r = new Random();
		multiplier = Math.abs(r.nextInt());
	}

	public void processKeyInput(char keyPress){
		switch (keyPress){
		case 'z':
			undo();
			break;
		case 'a':
				xOrigin = xOrigin - xLength*0.25;
			break;
		case 's':
				yOrigin = yOrigin - yLength*0.25;
			break;
		case 'w':
				yOrigin = yOrigin + yLength*0.25;
			break;
		case 'd':
				xOrigin = xOrigin + xLength*0.25;
				break;
		case 'r':
			reset();
			break;
		case 'o':
			saveImg();
			break;
		case 'n':
			newMultiplier();
			break;
		
		default:
				break;
		}
			
	}
	
	public void saveImg(){
			try {
				String path = "FractalImage_";
				String tmp;
				for (int i = 1; i < 500; i++){
					tmp = path + i + ".png";
					File f = new File(tmp);
					if (!f.exists()){
						ImageIO.write(bi,"png", new File(tmp));
						break;
					}
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}

	}
}