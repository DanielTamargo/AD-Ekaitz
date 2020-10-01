package com.tamargo.ventanas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {

    private BufferedImage image;
    private String ruta = "assets\\";

    public ImagePanel() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("assets\\Square_200x200.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(String nombreFoto) {
        try {
            this.image = ImageIO.read(new File(ruta + nombreFoto));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }

}
