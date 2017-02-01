package MusicProcessor.player.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jerry on 2017/1/30.
 */
public class SpectrumArea extends AppCanvas{

    int[] spectrum = new int[1];
    public void draw()
    {
        g2d.setColor(Color.cyan);
        int w = getWidth() / spectrum.length;
        int offset = (getWidth() - w*spectrum.length)/2;
        g2d.clearRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < spectrum.length; i++)
        {
            g2d.fillRect(offset + i*w, getHeight()- spectrum[i], w-1, spectrum[i]);
        }
    }

    public void setSpectrum(int[] s){if(s != null) spectrum = s;}

}
