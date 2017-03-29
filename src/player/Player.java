package player;

import ledcubeproject.models.musicprocessor.decoder.Mp3Decoder;
import ledcubeproject.models.musicprocessor.processor.SimpleSpectrumAnalyzer;
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

    /**
     * 一個簡單的播放功能，呼叫後會把一首音樂播完，播完後才會return，會占用執行緒
     */
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
                audev.write(pcm, 0, pcm.length);
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

        final int amount = 100;

        int[] s = new int[amount];
        int n = spectrum.length / amount;
        //int s[] = SpectrumStrategy.excute(spectrum, mp3Decoder.getSampleRate());
        for(int i = 0; i < s.length; i++)
        {
            s[i] = (int) spectrum[i] /1000;
        }
        return s;
    }

    static class SpectrumStrategy
    {
        static final int ranges[][] = {{20, 60}, {60, 250}, {250, 500}, {500, 2000}, {2000, 4000}, {4000, 6000}};
        static final int amount = ranges.length;
        static int[] excute(double[] spectrum, int sampleRate)
        {
            int count = 0;
            int max = 0;
            int freq;
            int band[] = new int[2];
            int[] result = new int[amount];
            for(int i = 0; i < ranges.length; i++)
            {
                max = 0;
                band[0] = ranges[i][0] * spectrum.length / sampleRate;
                band[1] = ranges[i][1] * spectrum.length / sampleRate;
                result[i] = (int)spectrum[band[0]];
            }
            return result;
        }

       /* static double [] getBigest(double[] array, int index1, int index2)
        {
            if(index1 - index2 <= 0) return null;
            double arr[] = new double[index1-index2+1];
            for()
        }*/
    }

}

