package MusicProcessor.player;

import MusicProcessor.decoder.Mp3Decoder;
import MusicProcessor.processor.SimpleFFT;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

/**
 * Created by Jerry on 2017/2/1.
 */
public class Player extends Thread{

    private Mp3Decoder mp3Decoder = null;
    AudioDevice audev = null;
    short pcm[];
    double spectrum[];

    int rateFreq;

    SimpleFFT simpleFFT = new SimpleFFT();

    public void init(String fileName)
    {
        try {
            mp3Decoder = new Mp3Decoder(fileName);
            audev = FactoryRegistry.systemRegistry().createAudioDevice();
            mp3Decoder.bindAudioDevice(audev);
            rateFreq = 0;
        }catch (Exception e){}
    }

    public void play()
    {
        if(audev == null || mp3Decoder == null)
            return;
        try {
            int max = Integer.MAX_VALUE;
            boolean ret = true;
            while (max-- > 0 && ret) {
                pcm = mp3Decoder.decodeFrame();
                if(pcm == null)
                {
                    ret = false;
                    continue;
                }
                spectrum = simpleFFT.getSpectrum(pcm);
                //System.out.print(1);
                audev.write(pcm, 0, pcm.length);
                if(rateFreq == 0)
                {
                    rateFreq = mp3Decoder.getSampleRate();
                    System.out.println("sample rate: "+rateFreq);
                }
            }
        }catch (Exception e){}
    }

    public int[] playOneFrame()
    {
        if(audev == null || mp3Decoder == null)
            return null;
        pcm = mp3Decoder.decodeFrame();
        if(pcm == null)
            return  null;
        try{
            spectrum = simpleFFT.getSpectrum(pcm);
            //System.out.print(1);
            audev.write(pcm, 0, pcm.length);
            if(rateFreq == 0)
            {
                rateFreq = mp3Decoder.getSampleRate();
                System.out.println("sample rate: "+rateFreq);
            }
        }catch (Exception e){}
        return getCurrentSpectrum();
    }

    @Override
    public void run()
    {
        play();
        System.out.println("播放完畢");
    }

    public int[] getCurrentSpectrum()
    {
        if(spectrum == null) return null;
        final int amount = 256;
        int[] s = new int[amount];
        int n = spectrum.length / amount;
        for(int i = 0; i < s.length; i++)
        {
            /*s[i] = 0;
            for(int j = 0; j < n; j++)
                //if(s[i] < spectrum[i * n + j]) s[i] = (int)spectrum[i*n + j];
                s[i] += (int)spectrum[i*n + j];*/
            s[i] = (int) spectrum[i] / spectrum.length;
            //s[i] = s[i] / 32 / 1000;
        }
        return s;
    }

}

