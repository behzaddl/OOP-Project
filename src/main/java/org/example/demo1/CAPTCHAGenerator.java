package org.example.demo1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CAPTCHAGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CAPTCHA_LENGTH = 6;
    private static final int IMAGE_WIDTH = 160;
    private static final int IMAGE_HEIGHT = 60;
    private static final Random random = new Random();
    private static String lastGeneratedText;

    public static BufferedImage generateCaptcha() {
        BufferedImage bufferedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Draw background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        // Draw noise
        drawNoise(g);

        // Generate CAPTCHA text
        String captchaText = generateRandomText();
        lastGeneratedText = captchaText;

        // Draw CAPTCHA text
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fontMetrics = g.getFontMetrics();
        int x = (IMAGE_WIDTH - fontMetrics.stringWidth(captchaText)) / 2;
        int y = ((IMAGE_HEIGHT - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();
        g.setColor(Color.BLACK);
        g.drawString(captchaText, x, y);

        g.dispose();
        return bufferedImage;
    }

    private static String generateRandomText() {
        StringBuilder captchaText = new StringBuilder(CAPTCHA_LENGTH);
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            captchaText.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return captchaText.toString();
    }

    private static void drawNoise(Graphics2D g) {
        int noiseAmount = 50;
        for (int i = 0; i < noiseAmount; i++) {
            int x = random.nextInt(IMAGE_WIDTH);
            int y = random.nextInt(IMAGE_HEIGHT);
            int x2 = random.nextInt(IMAGE_WIDTH);
            int y2 = random.nextInt(IMAGE_HEIGHT);
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawLine(x, y, x2, y2);
        }
    }

    public static String getLastGeneratedText() {
        return lastGeneratedText;
    }
}
