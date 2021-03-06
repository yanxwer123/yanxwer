package com.kld.app.util;

import com.sun.imageio.plugins.gif.GIFImageMetadata;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Qcsea on 15-12-29.
 */
public class GifComponent extends JComponent {
    private static final long serialVersionUID = 1L;
    private GifBean[] gifBeans;
    private Map<Integer, Integer[]> gifBeanMap = new HashMap<Integer, Integer[]>();
    private int index = 0;
    private int delayFactor;
    private Timer timer;

    /**
     *
     * @param gifFile
     * @param delayFactor
     *            显示gif每帧图片的时间因子
     */
    public GifComponent(File gifFile, int delayFactor) {
        setDelayFactor(delayFactor);
        setGifFile(gifFile);
    }

    /**
     * 设置Gif文件
     *
     * @param gifFile
     */
    public void setGifFile(File gifFile) {
        ImageReader reader = null;
        try {
            ImageInputStream imageIn = ImageIO.createImageInputStream(gifFile);
            Iterator<ImageReader> iter = ImageIO
                    .getImageReadersByFormatName("gif");
            if (iter.hasNext()) {
                reader = iter.next();
            }
            reader.setInput(imageIn, false);
            gifBeanMap.clear();
            gifBeans = new GifBean[reader.getNumImages(true)];
            GIFImageMetadata meta = null;
            for (int i = 0; i < gifBeans.length; i++) {
                meta = (GIFImageMetadata) reader.getImageMetadata(i);
                gifBeans[i] = new GifBean();
                gifBeans[i].image = reader.read(i);
                gifBeans[i].x = meta.imageLeftPosition;
                gifBeans[i].y = meta.imageTopPosition;
                gifBeans[i].width = meta.imageWidth;
                gifBeans[i].height = meta.imageHeight;
                gifBeans[i].disposalMethod = meta.disposalMethod;
                gifBeans[i].delayTime = meta.delayTime == 0 ? 1
                        : meta.delayTime;
            }
            for (int i = 1; i < gifBeans.length; i++) {
                if (gifBeans[i].disposalMethod == 2) {
                    gifBeanMap.put(new Integer(i), new Integer[] { i });
                    continue;
                }
                int firstIndex = getFirstIndex(i);
                List<Integer> list = new ArrayList<Integer>();
                for (int j = firstIndex; j <= i; j++) {
                    list.add(j);
                }
                gifBeanMap.put(new Integer(i), list.toArray(new Integer[] {}));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTimer();
    }

    private synchronized void setTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer("show gif");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
                try {
                    Thread.sleep(gifBeans[index].delayTime * delayFactor);
                } catch (InterruptedException e) {
                }
                index++;
                if (index >= gifBeans.length) {
                    index = 0;
                }
            }

        }, 0, 1);

    }

    /**
     * 设置时间因子
     *
     * @param delayFactor
     */
    public void setDelayFactor(int delayFactor) {
        this.delayFactor = delayFactor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(gifBeans[0].image, gifBeans[0].x, gifBeans[0].y, this);
        if (index > 0) {
            Integer[] array = gifBeanMap.get(index);
            for (Integer i : array) {
                g.drawImage(gifBeans[i].image, gifBeans[i].x, gifBeans[i].y,
                        this);
            }
        }
    }

    private int getFirstIndex(int index) {
        int tempIndex = index;
        while (tempIndex > 1) {
            if (tempIndex - 1 > 0
                    && gifBeans[tempIndex - 1].disposalMethod == 2) {
                return index;
            }
            tempIndex--;
        }
        return tempIndex;
    }

    /**
     * 用于保持gif每帧图片的信息
     */
    public class GifBean {
        public BufferedImage image;
        public int x;
        public int y;
        public int width;
        public int height;
        public int disposalMethod;
        public int delayTime;
    }

}