package MusicProcessor.player.gui;

/**
 * Created by Jerry on 2017/2/1.
 */
class UpdateLoop extends Thread
{
    AppCanvas updateArea;
    private long timeInterval = 0;
    public UpdateLoop(){}
    public UpdateLoop(AppCanvas a)
    {
        setUpdateArea(a);
    }
    @Override
    public void run()
    {
        System.out.println("loop start");
        long nano = System.nanoTime();

        while(true)
        {
            if(System.nanoTime() - nano > timeInterval)
            {
                nano = System.nanoTime();
                updateArea.draw();
                updateArea.repaint();
            }
        }
    }

    public void setTimeInterval(long t)
    {
        timeInterval = t;
    }
    public void setUpdateArea(AppCanvas a){updateArea = a;}
}
