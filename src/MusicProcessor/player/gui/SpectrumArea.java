package MusicProcessor.player.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jerry on 2017/1/30.
 */
public class SpectrumArea extends AppCanvas{

    Spectrums spectrums = new Spectrums();
    UpdateLoop loop = new UpdateLoop(this);

    public SpectrumArea()
    {
        this.add(spectrums);
    }
    public void draw(){spectrums.draw();}

    class Spectrums extends VisibleObject
    {
        double spectrum[];

        public Spectrums()
        {
            img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) img.getGraphics();
            g2d.draw(new Rectangle(0,0, 100, 100));
        }

        public void draw()
        {
            x+=10;
            y = x;
            canvasGraph.drawImage(img, x, y, null);
        }
    }
}
