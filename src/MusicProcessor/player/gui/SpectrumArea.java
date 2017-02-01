package MusicProcessor.player.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jerry on 2017/1/30.
 */
public class SpectrumArea extends AppCanvas{

    Spectrums spectrums;
    UpdateLoop loop = new UpdateLoop(this);

    public SpectrumArea()
    {
        spectrums = new Spectrums(800, 600);
        this.add(spectrums);
        loop.setTimeInterval(100000);
    }
    public void draw()
    {
        spectrums.draw();
    }

    public void setSpectrum(double[] d)
    {
        spectrums.spectrum = d;
    }

    class Spectrums extends VisibleObject
    {
        double spectrum[] = new double[]{0};

        public Spectrums(int w, int h)
        {
            img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            x = y = 0;
        }
        public void draw()
        {

            g2d.setColor(Color.cyan);
            if(spectrum == null) return;
            int w = img.getWidth() / spectrum.length;
            for(int i = 0; i < spectrum.length; i++)
            {
                g2d.clearRect(i*w,0, w, img.getHeight());
                g2d.fillRect(i*w, (int)(img.getHeight() - spectrum[i]), w, (int)spectrum[i]);
            }
            /*g2d.draw(new Rectangle(0, 0, 100, 100));
            x++; y++;*/
            canvasGraph.drawImage(img, x, y, null);
        }
    }
}
