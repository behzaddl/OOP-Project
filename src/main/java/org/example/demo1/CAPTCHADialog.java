package org.example.demo1;


import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CAPTCHADialog extends Dialog<String> {
    public CAPTCHADialog(BufferedImage captchaImage) {
        setTitle("CAPTCHA Verification");
        setHeaderText("Please enter the characters displayed:");

        // Convert BufferedImage to JavaFX Image
        ImageView captchaImageView = new ImageView();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(captchaImage, "png", os);
            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            Image image = new Image(is);
            captchaImageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextField captchaInput = new TextField();
        captchaInput.setPromptText("Enter CAPTCHA");

        VBox vbox = new VBox(10, captchaImageView, captchaInput);
        getDialogPane().setContent(vbox);

        getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == javafx.scene.control.ButtonType.OK) {
                return captchaInput.getText();
            }
            return null;
        });

        // Custom style
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getScene().setFill(Color.TRANSPARENT);
    }
}
