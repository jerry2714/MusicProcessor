package MusicProcessor.player;

import MusicProcessor.decoder.Mp3Decoder;
import MusicProcessor.processor.SimpleSpectrumAnalyzer;
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

    SimpleSpectrumAnalyzer simpleSpectrumAnalyzer = new SimpleSpectrumAnalyzer();

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
                spectrum = simpleSpectrumAnalyzer.getSpectrum(pcm);
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
            audev.write(pcm, 0, pcm.length);
            int sum = 0;
            for(int a : pcm)
                sum += a;
            sum /= pcm.length;
            for(int i = 0; i < pcm.length; i++)
                pcm[i] -= sum;
            spectrum = simpleSpectrumAnalyzer.getSpectrum(pcm);
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
            s[i] = (int) spectrum[i] / s.length;
            //s[i] = s[i] / 32 / 1000;
        }
        /*int s[] = new int[spectrum.length/2];
        for(int i = 0; i < s.length; i++)
            s[i] = (int) (simpleSpectrumAnalyzer.getHannWindow()[i] * 1000);*/
        return s;
    }

}

