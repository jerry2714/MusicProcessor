package MusicProcessor.player.gui;

import java.awt.*;

/**
 * Created by Jerry on 2017/1/30.
 */
public class SpectrumArea extends AppCanvas{

    public SpectrumArea()
    {
        Rectangle r = new Rectangle(100,200);
        init(800, 600);
    }


    class Spectrums extends VisibleObject
    {
        double spectrum[];
    }
}
