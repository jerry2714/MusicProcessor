package MusicProcessor.player.gui;

import MusicProcessor.player.Player;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Jerry on 2017/1/30.
 */
public class GPlayer extends Player{

    private Frame frm;
    private SpectrumArea spectrumArea;
   // private Player player;
    public static void main(String args[])
    {
        GPlayer gPlayer = new GPlayer();
        gPlayer.init(args[0]);
        gPlayer.start();

        while(true)
        {
            gPlayer.spectrumArea.setSpectrum(gPlayer.getCurrentSpectrum());
            //System.out.println(gPlayer.getCurrentSpectrum()[0]);
        }
    }

    public void init(String fileName)
    {
        frm = new Frame("GPlayer");
        frm.setSize(800,600);
        frm.addWindowListener(GeneralWinListener.getInstance());

        spectrumArea = new SpectrumArea();
        spectrumArea.init(frm.getWidth(), frm.getHeight());

        frm.add(spectrumArea);
        frm.setVisible(true);

        super.init(fileName);
    }

    public void run()
    {
        spectrumArea.loop.start();
        play();
    }

}
class GeneralWinListener extends WindowAdapter {

    static  GeneralWinListener gwl = new GeneralWinListener();

    static public GeneralWinListener getInstance(){return gwl;}
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
