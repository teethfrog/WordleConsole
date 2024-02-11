import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageHandling {

    static String fileName;
    public static void setFileName(StringBuilder str){
        fileName = "WordleConsole-" + WordUtils.getWord();
    }
    public static RenderedImage generateImage(ArrayList<String> guessSheet) {
        BufferedImage bufferedImage = new BufferedImage(250, 300, BufferedImage.TYPE_INT_RGB);
        Graphics2D img = bufferedImage.createGraphics();
        img.setBackground(Color.white);

        int index = 0;
        int length = 30;
        if (guessSheet.size() < length) {
            while(guessSheet.size()<length){
                guessSheet.add("W");
            }
        }
        for (int y = 0; y < 300; y+=50) {
            for (int x = 0; x < 250; x+=50) {
                if (index >= 30) {
                    break;
                }
                String character = guessSheet.get(index);
                switch (character) {
                    case "G" -> img.setColor(Color.green);
                    case "Y" -> img.setColor(Color.yellow);
                    case "W" -> img.setColor(Color.lightGray);
                    default -> img.setColor(Color.red);
                }
                img.fillRect(x, y, 50, 50);

                index++;
            }

            // Check if index is above or equal to 25 after the inner loop, if yes, break out of the outer loop
            if (index >= 30) {
                break;
            }
        }
        return bufferedImage;
    }


    public static void writeImage(RenderedImage imageData) throws IOException {

        ImageIO.write(imageData , "png", new File(fileName  + ".png"));
    }

}
