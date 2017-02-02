package MusicProcessor.processor;

import MusicProcessor.decoder.Mp3Decoder;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import org.jtransforms.fft.*;

import static org.apache.commons.math3.util.FastMath.sqrt;

/**
 * Created by Jerry on 2017/1/30.
 */
public class SimpleFFT {

    private DoubleFFT_1D fft_1D;

    public static void main(String args[])
    {
        short pcm[] = new short[]{980,988,1160,1080,928,1068,1156,1152,1176,1264};

        SimpleFFT simpleFFT = new SimpleFFT();

        double spectrum[] = simpleFFT.getSpectrum(pcm);
        for(double d : spectrum)
            System.out.print(d + " ");
        System.out.println();
    }


    public double[] getFFTFromPCM(short[] pcm)
    {
        if(fft_1D == null)
        {
            fft_1D = new DoubleFFT_1D(pcm.length);
        }
        //double[] fft = new double[pcm.length*2];
        double[] fft = new double[pcm.length];
        for(int i = 0; i < pcm.length; i++)
            fft[i] = pcm[i];
        //for(int i = pcm.length; i < fft.length; i++)
        //    fft[i] = 0;
        fft_1D.realForward(fft);
        return fft;
    }
    public double[] getMagnitude(double fft[])
    {
        if(fft == null) return null;
        double spectrum[] = new double[fft.length/2];
        double n1, n2;
        if(fft.length % 2 == 0) {
            spectrum[0] = fft[0];
            for (int i = 1; i < fft.length / 2; i++)
            {
                n1 = fft[2*i]/* - fft[0]*/;
                n2 = fft[2*i + 1] /*- fft[0]*/;
                spectrum[i] = sqrt(n1*n1 + n2*n2);
            }
        }
        else {  //some problems here
            spectrum[0] = fft[0];
            for (int i = 1; i < fft.length / 2 - 1; i++)
            {
                n1 = fft[2*i] - fft[0];
                n2 = fft[2*i + 1] - fft[0];
                spectrum[i] = sqrt(n1*n1 + n2*n2);
            }
            spectrum[fft.length/2 -1] = sqrt(fft[fft.length-1]*fft[fft.length-1] + fft[1]*fft[1]);
        }
        return spectrum;
    }

    public double[] getSpectrum(short[] pcm)
    {
        return getMagnitude(getFFTFromPCM(pcm));
    }
}
