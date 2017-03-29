package player.gui;

import player.Player;

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
    private Loop loop = new Loop();
    class Loop extends ThreadLoop
    {
        boolean draw = true;
        @Override
        public void excute() {
            spectrumArea.setSpectrum(player.playOneFrame());
            //System.out.println(2);
            if(draw) {
                spectrumArea.draw();
                spectrumArea.repaint();
            }
          //  draw = !draw;
        }
    }

    public static void main(String args[])
    {
        GPlayer gPlayer = new GPlayer();
        gPlayer.init(args[0]);

        gPlayer.start();
    }

    public void init(String fileName)
    {
        frm = new Frame("GPlayer");
        frm.setSize(1920,800);
        frm.setLayout(new BorderLayout());
        frm.addWindowListener(GeneralWinListener.getInstance());

        spectrumArea = new SpectrumArea();
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        frm.add(p);
        p.add(spectrumArea);
        frm.setBackground(Color.blue);
        frm.setVisible(true);
        spectrumArea.init(p.getWidth(), p.getHeight());

        player = new Player();
        player.init(fileName);
    }

    public void run()
    {
        loop.setTimeInterval(26000000);
        loop.start();
    }

}
class GeneralWinListener extends WindowAdapter {

    static  GeneralWinListener gwl = new GeneralWinListener();

    static public GeneralWinListener getInstance(){return gwl;}
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
