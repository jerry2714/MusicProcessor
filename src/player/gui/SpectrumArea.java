package player.gui;

import java.awt.*;

/**
 * Created by Jerry on 2017/1/30.
 */
public class SpectrumArea extends AppCanvas{

    int[] spectrum = new int[1];
    boolean ready = false;
    public void draw()
    {
        if(!ready) {
            g2d.setColor(Color.cyan);
            g2d.clearRect(0, 0, getWidth(), getHeight());
            ready = true;
        }
        int w = getWidth() / spectrum.length;
        int offset = (getWidth() - w*spectrum.length)/2;
        for(int i = 0; i < spectrum.length; i++)
        {
            g2d.clearRect(offset + i*w, 0, w-1, getHeight()- spectrum[i]);
            g2d.fillRect(offset + i*w, getHeight()- spectrum[i], w-1, spectrum[i]);
        }
    }


    public void setSpectrum(int[] s){if(s != null) spectrum = s;}

}
