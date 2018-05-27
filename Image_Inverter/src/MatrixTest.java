import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class MatrixTest extends ImageInverter{

//Creating the matrix
static int[][] pixels;
static BufferedImage bimg ;
int w = 0;
int h = 0;

	public MatrixTest(BufferedImage bim){
		ColorModel cm = bim.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bim.copyData(null);
	this.bimg = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	w = bimg.getWidth();
	h = bimg.getHeight();
	pixels = new int[w][h];
	for( int i = 0; i < w; i++ )
		{for( int j = 0; j < h; j++ )
			{
				pixels[i][j] = bimg.getRGB(i, j);
			}
		}
	}

	public MatrixTest() {
	}

	public BufferedImage Invert(){


		try{
				//Object of object Class
				RowInverter object = new RowInverter(w,h);

				//Threads
				ThreadInverter thread1 = new ThreadInverter(object);
				ThreadInverter thread2 = new ThreadInverter(object);
				ThreadInverter thread3 = new ThreadInverter(object);

			for(int x=0;x<(bimg.getHeight());x++){
				//Implementing threads
				Thread th1 = new Thread(thread1);
				Thread th2 = new Thread(thread2);
				Thread th3 = new Thread(thread3);

				//Starting threads
				th1.start();
				th2.start();
				th3.start();
			}


		}catch (Exception e) {
				e.printStackTrace();
		}


		return bimg;
	}

}//End Class

//RowInverter Class
class RowInverter extends MatrixTest {

	private int i;
	private int j;
	private int chance;

	public RowInverter(int i, int j){
		this.i=i;
		this.j=j;
		chance=0;
	}

	//Matrix Multiplication Function
	public synchronized void objectMatrix(){
		System.out.println(chance);

			for( int b = 0; b < i; b++ )
			{
				java.awt.Color c = new java.awt.Color(super.bimg.getRGB(b, chance));
				int alpha = c.getAlpha();
				int red = 255 - c.getRed();
				int green = 255 - c.getGreen();
				int blue = 255 - c.getBlue();
				pixels[b][chance] = (alpha<<24) | (red<<16) | (green<< 8) | blue;
				super.bimg.setRGB(b, chance, pixels[b][chance]);
			}
		chance++;
	}
}//End object class

//Thread Class
class ThreadInverter implements Runnable {

	private final RowInverter inv;

	public ThreadInverter(RowInverter inv){
		this.inv=inv;
	}

	@Override
	public void run() {
		inv.objectMatrix();
}
}