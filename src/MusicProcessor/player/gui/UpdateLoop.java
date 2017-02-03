package MusicProcessor.player.gui;

/**
 * Created by Jerry on 2017/2/1.
 */
abstract class UpdateLoop extends Thread
{
    private long timeInterval = 0;
    public UpdateLoop(){}
    @Override
    public void run()
    {
        System.out.println("loop start");
        long nano = System.nanoTime();

        while(true)
        {
            while(System.nanoTime() - nano < timeInterval);
            nano = System.nanoTime();

            excute();
        }
    }

    public void setTimeInterval(long t)
    {
        timeInterval = t;
    }

    abstract public void excute();
}
