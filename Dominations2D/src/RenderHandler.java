import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
    private BufferedImage view;
    private int[] pixels;

    public RenderHandler(int width, int height) {
        // create bufferedimage to represent our view
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // create an array for our pixels
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

        for(int heightIndex = 0; heightIndex < height; heightIndex++) {

            int randomPixel = (int)(Math.random() * 0xFFFFFF);

            for (int widthIndex = 0; widthIndex < width; widthIndex++) {
                pixels[heightIndex * width + widthIndex] = randomPixel;
            }
        }
    }


    // renders our array of pixels to the screen
    public void render(Graphics graphics){

        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }

    // render our image to our array of pixels
    public void renderImage(BufferedImage image, int xPosition, int yPosition) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                pixels[(x + xPosition) + (y + yPosition) * view.getWidth()] = imagePixels[x + y * image.getWidth()];
            }
        }

    }


}
