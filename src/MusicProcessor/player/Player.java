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
                //for(double d : spectrum)
                audev.write(pcm, 0, pcm.length);
                if(rateFreq == 0)
                {
                    rateFreq = mp3Decoder.getSampleRate();
                    System.out.println("sample rate: "+rateFreq);
                }
            }
        }catch (Exception e){}
    }

    @Override
    public void run()
    {
        play();
        System.out.println("播放完畢");
    }

    public double[] getCurrentSpectrum()
    {
        if(spectrum == null) return null;
        double[] s = new double[32];
        int n = spectrum.length / 32;
        for(int i = 0; i < s.length; i++)
        {
            s[i] = 0;
            for(int j = 0; j < n; j++)
                if(s[i] < spectrum[i * n + j]) s[i] = spectrum[i*n + j];

            s[i] = s[i] / 100;
        }
        return s;
    }
}

