package MusicProcessor.player.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Jerry on 2017/1/30.
 */
public class GPlayer{

    private Frame frm;
    private SpectrumArea spectrumArea;
    public static void main(String args[])
    {
        GPlayer gPlayer = new GPlayer();
        gPlayer.init();
    }

    public void init()
    {
        frm = new Frame("GPlayer");
        frm.setSize(800,600);
        frm.addWindowListener(GeneralWinListener.getInstance());

        spectrumArea = new SpectrumArea();
        spectrumArea.init(frm.getWidth(), frm.getHeight());

        frm.add(spectrumArea);
        frm.setVisible(true);
    }


}
class GeneralWinListener extends WindowAdapter {

    static  GeneralWinListener gwl = new GeneralWinListener();

    static public GeneralWinListener getInstance(){return gwl;}
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
