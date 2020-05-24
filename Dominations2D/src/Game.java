import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import java.io.IOException;
import java.io.File;
import java.lang.Runnable;
import java.lang.Thread;

import javax.swing.JFrame;

import javax.imageio.ImageIO;

import java.io.IOException;

public class Game extends JFrame implements Runnable{

    private Canvas canvas = new Canvas();
    private RenderHandler renderer;
    private BufferedImage testImage;


    public Game() {

        // exits program function
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set position and size of our frame
        setBounds(0,0, 1000, 800);

        // put frame in center of screen
        setLocationRelativeTo(null);

        // add canvas (graphics component)
        add(canvas);

        // make visible
        setVisible(true);

        // create object for buffer strategy
        canvas.createBufferStrategy(3);

        renderer = new RenderHandler(getWidth(), getHeight());

        testImage = loadImage("tiles/GrassTile.png");
    }

    public void update() {

    }

    private BufferedImage loadImage(String path) {
        try {
            // gets resource of path as input string and stores as loaded image
            BufferedImage loadedImage = ImageIO.read(new File(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            // drawing our loaded image to our formatted image (to get rgb type)
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return formattedImage;

        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }


    }

    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        renderer.renderImage(testImage, 0, 0);
        renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public void run() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        int i = 0;
        int x = 0;

        long lastTime = System.nanoTime(); //long 2 ^ 63
        double nanoSecondConversion = 1000000000.0 / 60; //60 fps
        double changeInSeconds = 0;

        while (true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            while(changeInSeconds >= 1) {
                update();
                changeInSeconds = 0;
            }

            render();
            lastTime = now;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();

    }




}
