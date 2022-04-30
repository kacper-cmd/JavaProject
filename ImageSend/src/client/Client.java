/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JLabel;

/**
 *
 * @author kacper
 */
public class Client {
    
    
    public static void main(String[] args) throws IOException {
        Socket  socket = new Socket("localhost",12345); 
            
        
        System.out.println("COnnected to server");
        
        JFrame jFrame = new JFrame("Client");
        jFrame.setSize(400,400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\kacpe\\3D Objects\\zdj.png");
        
        JLabel jLabelPic = new JLabel(imageIcon);
        JButton jButton = new JButton("Send Image to Server");
        jFrame.add(jLabelPic,BorderLayout.CENTER);
        jFrame.add(jButton,BorderLayout.SOUTH);
        
        jFrame.setVisible(true);
        
        jButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                try{
                    OutputStream outputStream = socket.getOutputStream();
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                    
                    Image image = imageIcon.getImage();
                    
                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),image.getHeight(null), BufferedImage.TYPE_INT_RGB);
                    
                    Graphics graphics = bufferedImage.createGraphics();
                    graphics.drawImage(image, 0, 0, null);
                    graphics.dispose();
                    
                    ImageIO.write(bufferedImage, "png", bufferedOutputStream);
                    bufferedOutputStream.close();
                    socket.close();
                    
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }

            
        });
        
    }
    
}
