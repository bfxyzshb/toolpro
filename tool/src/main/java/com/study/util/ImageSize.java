package com.study.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageSize {
    public static void main(String[] args) {
       // getImageInfoByImageReader(getFileToByte(new File("/Users/hebiao/Downloads/4359179487935106")));
       // System.out.println(getFileExt("/Users/hebiao/Downloads/4359179487935106"));
       // getSize(getFileToByte(new File("/Users/hebiao/Downloads/4359179487935106")));
        pngToJpg();
    }


    public static void getImageInfoByImageReader(byte[] byteOfFile) {
        try {

            final ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(byteOfFile));
            //final Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            //Iterator<ImageReader> iter = ImageIO.getImageReaders(new FileImageInputStream(iis));
            Iterator<ImageReader>iter=ImageIO.getImageReadersByFormatName("png");
            if (!iter.hasNext()) {
                throw new RuntimeException("No readers found!");
            }
            final ImageReader reader = iter.next();
            reader.setInput(iis, true);
            System.out.println("width:" + reader.getWidth(0));
            System.out.println("height:" + reader.getHeight(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pngToJpg() {
        BufferedImage bufferedImage;
        try {
            // read image file
            bufferedImage = ImageIO.read(new File("/Users/hebiao/Downloads/4359179487935106"));
            // create a blank, RGB, same width and height, and a white
            // background
            BufferedImage newBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
                    Color.WHITE, null);
            // write to jpeg file
            System.out.println(newBufferedImage.getWidth());
            //ImageIO.write(newBufferedImage, "jpg", new File("c:\\java.jpg"));
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSize(byte[] byteOfFile) {
        if (null != byteOfFile) {
            try {
                long t1 = System.currentTimeMillis();
                InputStream in = new ByteArrayInputStream(byteOfFile);
                BufferedImage bImageFromConvert = ImageIO.read(in);
                long t2 = System.currentTimeMillis();
                // 若读取不了该图片流，返回-1为标识
                if (null == bImageFromConvert) {
                    return "-1,-1";
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bImageFromConvert, "png", baos);
                byte[] data = baos.toByteArray();
                /*String width = String.valueOf(bImageFromConvert.getWidth());
                String height = String.valueOf(bImageFromConvert.getHeight());
                if (t2 - t1 > 60) {
                }*/
                return 1 + "," + 1;
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin Array 出错",ex);
        }
        return by;
    }

    public static String getFileExt(String filePath) {
        FileInputStream fis = null;
        String extension = "";
        try {
            fis = new FileInputStream(new File(filePath));
            byte[] bs = new byte[1];
            fis.read(bs);
            String type = Integer.toHexString(bs[0]&0xFF);
            if(JPG_HEX.equals(type))
                extension = JPG_EXT;
            if(PNG_HEX.equals(type))
                extension = PNG_EXT;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return extension;
    }
    private static final String JPG_HEX = "ff";
    private static final String PNG_HEX = "89";
    private static final String JPG_EXT = "jpg";
    private static final String PNG_EXT = "png";

}
