package decoder;

import javazoom.jl.decoder.*;
import javazoom.jl.player.AudioDevice;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Jerry on 2017/1/25.
 * create github repo on 2017/1/27.
 */

public class Mp3Decoder implements MusicDecoder {
    private Bitstream bitstream;
    private Decoder decoder;
    private Boolean ready = false;


    public Mp3Decoder(String fileName)
    {
            if (fileName != null)
               init(fileName);
            decoder = new Decoder();
    }

    /**
     * 初始化，包括將檔案開啟
     * @param fileName  欲打開的音樂檔名(路徑)
     */
    public void init(String fileName)
    {
        try {
            if (fileName != null) {
                FileInputStream fin = new FileInputStream(fileName);
                //BufferedInputStream bin = new BufferedInputStream(fin);
                bitstream = new Bitstream(fin);
            }
            ready = true;
        }catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    /**
     * 取得下一個解碼後的frame
     * @return   PCM data
     *
     */
    public short[] decodeFrame()
    {
        if(!ready) return null;
        short pcm[] = null;
        try {
            Header h = bitstream.readFrame();
            if(h == null) return null;
            pcm =  ((SampleBuffer)decoder.decodeFrame(h, bitstream)).getBuffer();
            bitstream.closeFrame();
        }catch (Exception e){System.out.println("Exception decoding audio frame");}
        return pcm;
    }

    /**
     * 將音訊裝置和decoder連結在一起
     * @param audev  欲連結的音訊裝置
     * @throws JavaLayerException
     */
    public void bindAudioDevice(AudioDevice audev) throws JavaLayerException
    {
        if(audev != null)
            audev.open(decoder);
    }


}
