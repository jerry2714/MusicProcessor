package MusicProcessor.player.gui;

/**
 * Created by Jerry on 2017/2/1.
 */
class UpdateLoop extends Thread
{
    AppCanvas updateArea;

    public UpdateLoop(){}
    public UpdateLoop(AppCanvas a)
    {
        setUpdateArea(a);
    }
    @Override
    public void run()
    {
        long nano = System.nanoTime();
        while(true)
        {
            if(System.nanoTime() - nano > 1000000000)
            {
                nano = System.nanoTime();
                updateArea.draw();
                updateArea.repaint();
            }
        }
    }


    public void setUpdateArea(AppCanvas a){updateArea = a;}
}
