/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author PC
 */
public class PhotoPanel extends JPanel{
      @Override
   public void paint(Graphics g) {
       
          try {
              InputStream is = new ByteArrayInputStream(photoBytes);
              BufferedImage newBi = ImageIO.read(is);
                    Graphics2D graphic2d = (Graphics2D) g;
        var resizedImage = resizeImage(newBi, 500, 500);
              graphic2d.drawImage(resizedImage, null, 200, 200);

          
          } catch (IOException ex) {
             ex.printStackTrace();
          }
   }
   byte [] photoBytes;
   public PhotoPanel(byte [] bytes){
       photoBytes =bytes;
   }
   BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = resizedImage.createGraphics();
    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
    graphics2D.dispose();
    return resizedImage;
}
}
