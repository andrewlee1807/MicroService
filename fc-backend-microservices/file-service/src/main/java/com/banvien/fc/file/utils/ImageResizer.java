package com.banvien.fc.file.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageResizer {

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage resized = new BufferedImage(width, height, img.getType());
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public static void resize(String inputImagePath) throws IOException {
        File inputFile = new File(inputImagePath);
        String formatName = inputImagePath.substring(inputImagePath
                .lastIndexOf(".") + 1);
        BufferedImage inputImage = ImageIO.read(inputFile);
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        for (int i = 0; i < 10; i++) {
            double percent = 1024 / Math.sqrt(tmp.size() == 0 ? inputFile.length() : tmp.size());
            int scaledWidth = (int) (inputImage.getWidth() * percent);
            int scaledHeight = (int) (inputImage.getHeight() * percent);
            inputImage = resize(inputImage, scaledHeight, scaledWidth);
            tmp = new ByteArrayOutputStream();
            ImageIO.write(inputImage, formatName, tmp);
            if (tmp.size() - 1024 * 1024 <= 0) break;
        }
        ImageIO.write(inputImage, formatName, new File(inputImagePath));
    }

//    public static void main(String[] args) {
//        String inputImagePath = "C:/Users/khoi.doan-viet/Desktop/321.png";
//
//        try {
//            ImageResizer.resize(inputImagePath);
//        } catch (IOException ex) {
//            System.out.println("Error resizing the image.");
//            ex.printStackTrace();
//        }
//    }

}
