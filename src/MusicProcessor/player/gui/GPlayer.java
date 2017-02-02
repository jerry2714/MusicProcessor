package MusicProcessor.player.gui;

import MusicProcessor.player.Player;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Jerry on 2017/1/30.
 */
public class GPlayer extends Thread{

    private Frame frm;
    private SpectrumArea spectrumArea;
    private Player player;
    public static void main(String args[])
    {
        GPlayer gPlayer = new GPlayer();
        gPlayer.init(args[0]);

        gPlayer.start();
    }

    public void init(String fileName)
    {
        frm = new Frame("GPlayer");
        frm.setSize(1000,800);
        frm.setLayout(null);
        frm.addWindowListener(GeneralWinListener.getInstance());

        spectrumArea = new SpectrumArea();
        spectrumArea.init(800, 600);
        //spectrumArea.setLocation(100, 100);
        Panel p = new Panel();
        p.setLayout(null);
        p.setSize(800, 600);
        p.setLocation(100, 100);
        p.add(spectrumArea);
        frm.add(p);
        frm.setBackground(Color.blue);
        frm.setVisible(true);

        player = new Player();
        player.init(fileName);
    }

    public void run()
    {
        long timeInterval = 26000000;
        long nano;
        nano = System.nanoTime();
        while(true)
        {

            while(System.nanoTime() - nano < timeInterval);
            nano = System.nanoTime();
            spectrumArea.setSpectrum(player.playOneFrame());
            //System.out.println(2);
            spectrumArea.draw();
            spectrumArea.repaint();
        }
    }

}
class GeneralWinListener extends WindowAdapter {

    static  GeneralWinListener gwl = new GeneralWinListener();

    static public GeneralWinListener getInstance(){return gwl;}
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
