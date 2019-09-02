package com.honor.common.base.utils;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * <p>
 * 〈一句话功能简述〉
 * <p>
 *
 * @author yongheng
 * @since 2019/3/25
 */
public class QRCodeDecoderHandler {

    /**
     * 解码二维码
     *
     * @param imgPath
     * @return String
     */
    public String decoderQRCode(String imgPath) {

        // QRCode 二维码图片的文件
        File imageFile = new File(imgPath);
        BufferedImage bufImg = null;
        String decodedData = null;
        /*try {
            bufImg = ImageIO.read(imageFile);
            QRCodeDecoder decoder = new QRCodeDecoder();
            decodedData = new String(decoder.decode(new J2SEImage(bufImg)));
       *//* } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();*//*
        } catch (Exception dfe) {
            System.out.println("Error: " + dfe.getMessage());
            dfe.printStackTrace();
        }*/
        return decodedData;
    }

   /* class J2SEImage implements QRCodeImage {

        BufferedImage bufImg;
        public J2SEImage(BufferedImage bufImg) {
            this.bufImg = bufImg;
        }

        public int getWidth() {
            return bufImg.getWidth();
        }

        public int getHeight() {
            return bufImg.getHeight();
        }

        public int getPixel(int x, int y) {
            return bufImg.getRGB(x, y);
        }
    }*/
}