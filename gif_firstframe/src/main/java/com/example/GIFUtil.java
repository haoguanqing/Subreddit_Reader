package com.example;

import com.icafe4j.image.gif.FrameReader;
import com.icafe4j.image.gif.GIFFrame;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GIFUtil {

    public static byte[] getFirstFrameOfGif(String imageUrl) throws Exception{
        URL url = new URL(imageUrl);
        URLConnection ucon = url.openConnection();
        InputStream is = ucon.getInputStream();

        FrameReader frameReader = new FrameReader();
        GIFFrame frame = frameReader.getGIFFrame(is);
        BufferedImage thumbnail = frame.getFrame();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(thumbnail, "bmp", os);

        os.flush();
        is.close();
        byte[] data = os.toByteArray();
        os.close();

        return data;
    }
}
