package com.seckill.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
/**
 * 文本转图片类
 * @author YY2924 2014/11/18
 * @version 1.0
 */
public class TextToImage {
    /** 文本文件  */
    private File textFile;
    /** 图片文件 */
    private File imageFile;
     
    /** 图片 */
    private BufferedImage image;
    /** 图片宽度  */
    private final int IMAGE_WIDTH = 400;
    /** 图片高度 */
    private final int IMAGE_HEIGHT = 600;
    /** 图片类型  */
    private final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;
     
    /**
     * 构造函数
     * @param textFile 文本文件
     * @param imageFile 图片文件
     */
    public TextToImage(File textFile,File imageFile){
        this.textFile = textFile;
        this.imageFile = imageFile;
        this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_TYPE);
    }
     
    /**
     * 将文本文件里文字，写入到图片中保存
     * @return boolean  true，写入成功；false，写入失败
     */
    @SuppressWarnings("resource")
	public boolean convert() {
         
        //读取文本文件
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(textFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
         
        //获取图像上下文
        Graphics g = createGraphics(image);
        String line;
        //图片中文本行高
        final int Y_LINEHEIGHT = 15;
        int lineNum = 1;
        try {
            while((line = reader.readLine()) != null){
                g.drawString(line, 0, lineNum * Y_LINEHEIGHT);
                lineNum++;
            }
            g.dispose();
             
            //保存为jpg图片
            FileOutputStream fos = new FileOutputStream(imageFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
            encoder.encode(image);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
     
    /**
     * 获取到图像上下文
     * @param image 图片
     * @return Graphics
     */
    private Graphics createGraphics(BufferedImage image){
        Graphics g = image.createGraphics();
        g.setColor(Color.WHITE); //设置背景色
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);//绘制背景
        g.setColor(Color.BLACK); //设置前景色
        g.setFont(new Font("宋体", Font.PLAIN, 12)); //设置字体
        return g;
    }
     
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("输入TXT文本名称 (例如: D:/java.txt ):");
        String textFileName = in.nextLine();
        System.out.print("输入保存的图片名称 (例如： D:/java.jpg):");
        String imageFileName = in.nextLine();
         
        TextToImage convert = new TextToImage(new File(textFileName), new File(imageFileName));
        boolean success = convert.convert();
        System.out.println("文本转图片：" + (success ? "成功" : "失败"));
    }
 
}