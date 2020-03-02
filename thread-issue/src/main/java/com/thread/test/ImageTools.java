package com.thread.test;

import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;

import java.io.*;
import java.util.Properties;

public class ImageTools {

    public static void main(String[] args) {
        /*try {
            cutImage1(500, 500, "/Users/hebiao/Downloads/02.JPG", "/Users/hebiao/Downloads/abcd.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            byte[] result=runConvertImageCommand();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据尺寸缩放图片
     *
     * @param width   缩放后的图片宽度
     * @param height  缩放后的图片高度
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     */
    public static void cutImage1(int width, int height, String srcPath, String newPath) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(width, height);
        op.addImage(newPath);
        ConvertCmd convert = new ConvertCmd(true);
        // linux下不要设置此值，不然会报错  
        //convert.setSearchPath(imageMagickPath);
        convert.run(op);

    }

    private static byte[] runConvertImageCommand() throws IOException, IM4JavaException, InterruptedException {
        long startTime = System.currentTimeMillis();
        ByteArrayInputStream ism = null;
        ByteArrayOutputStream osm = null;
        try {
            GMOperation op = new GMOperation();
            op.resize(240, 240);
            ism = new ByteArrayInputStream(readFileFromDiskCache("/Users/hebiao/Downloads/4460705027601194.psd"));
            osm = new ByteArrayOutputStream();
            Pipe pipeIn = new Pipe(ism, null);
            Pipe pipeOut = new Pipe(null, osm);
            ConvertCmd convertCmd = new ConvertCmd();
            convertCmd.setInputProvider(pipeIn);
            convertCmd.setOutputConsumer(pipeOut);
            convertCmd.run(op);
            return osm.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (ism != null) {
                ism.close();
            }
            if (osm != null) {
                osm.close();
            }
        }
    }

    public static byte[] readFileFromDiskCache(String filename) {
        byte[] buffer = null;
        BufferedInputStream bis = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                return null;
            }
            buffer = new byte[(int) (file.length())];
            bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(buffer);
            bis.close();
        } catch (Exception e) {

        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e1) {
                }
            }
        }
        return buffer;
    }

    /**
     * ImageMagick的路径
     */
    public static String imageMagickPath = null;


    /**
     * 根据坐标裁剪图片
     *
     * @param srcPath 要裁剪图片的路径
     * @param newPath 裁剪图片后的路径
     * @param x       起始横坐标
     * @param y       起始纵坐标
     * @param x1      结束横坐标
     * @param y1      结束纵坐标
     */

    public static void cutImage(String srcPath, String newPath, int x, int y, int x1, int y1) throws Exception {
        int width = x1 - x;
        int height = y1 - y;
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        /**
         * width：  裁剪的宽度
         * height： 裁剪的高度
         * x：       裁剪的横坐标
         * y：       裁剪的挫坐标
         */
        op.crop(width, height, x, y);
        op.addImage(newPath);
        ConvertCmd convert = new ConvertCmd();

        // linux下不要设置此值，不然会报错
        convert.setSearchPath(imageMagickPath);

        convert.run(op);
    }

    /**
     * 根据宽度缩放图片
     *
     * @param width   缩放后的图片宽度
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     */
    public static void cutImage(int width, String srcPath, String newPath) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(width, null);
        op.addImage(newPath);
        ConvertCmd convert = new ConvertCmd();
        // linux下不要设置此值，不然会报错  
        convert.setSearchPath(imageMagickPath);
        convert.run(op);
    }

    /**
     * 给图片加水印
     *
     * @param srcPath 源图片路径
     */
    public static void addImgText(String srcPath) throws Exception {
        IMOperation op = new IMOperation();
        op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8")
                .draw("text 5,5 juziku.com");
        op.addImage();
        op.addImage();
        ConvertCmd convert = new ConvertCmd();
        // linux下不要设置此值，不然会报错  
        convert.setSearchPath(imageMagickPath);
        convert.run(op, srcPath, srcPath);
    }


}  