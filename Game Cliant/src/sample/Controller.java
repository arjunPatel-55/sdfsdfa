package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Controller {
    private int playerNum;
    private DataInputStream dis;
    private DataOutputStream dos;
    @FXML
    private ImageView n, e, s, w;
    @FXML
    private ImageView img1p0, img1p1, img1p2, img1p3, img1p4, img1p5, img1p6, img1p7, img1p8, img1p9, img1p10, img1p11, img1p12;
    private ImageView[] p1;

    @FXML
    private ImageView img2p0, img2p1, img2p2, img2p3, img2p4, img2p5, img2p6, img2p7, img2p8, img2p9, img2p10, img2p11, img2p12;
    private ImageView[] p2;

    @FXML
    private ImageView img3p0, img3p1, img3p2, img3p3, img3p4, img3p5, img3p6, img3p7, img3p8, img3p9, img3p10, img3p11, img3p12; // your other team mate
    private ImageView[] p3;

    @FXML
    private ImageView img4p0, img4p1, img4p2, img4p3, img4p4, img4p5, img4p6, img4p7, img4p8, img4p9, img4p10, img4p11, img4p12;
    private ImageView[] p4;


    @FXML
    public void join() {
        String name = JOptionPane.showInputDialog("what would you like to be called");
        p1= new ImageView[]{img1p0, img1p1, img1p2, img1p3, img1p4, img1p5, img1p6, img1p7, img1p8, img1p9, img1p10, img1p11, img1p12};
        p2= new ImageView[]{img2p0, img2p1, img2p2, img2p3, img2p4, img2p5, img2p6, img2p7, img2p8, img2p9, img2p10, img2p11, img2p12};
        p3= new ImageView[]{img3p0, img3p1, img3p2, img3p3, img3p4, img3p5, img3p6, img3p7, img3p8, img3p9, img3p10, img3p11, img3p12};
        p4= new ImageView[]{img4p0, img4p1, img4p2, img4p3, img4p4, img4p5, img4p6, img4p7, img4p8, img4p9, img4p10, img4p11, img4p12};

        try {
            Scanner scn = new Scanner(System.in);
            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket s = new Socket(ip, 5056);
            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(name);
            playerNum = Integer.parseInt(dis.readUTF());

            // the following loop performs the exchange of
            // information between client and client handler
//           TODO romove this and use buttone the get the info insted
            playGame();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void send() throws IOException {
//        String temp = hi.getText();
        dos.writeUTF(JOptionPane.showInputDialog("sadjgh"));
    }


//TODO use example for sixe of each element for all the grid pains ad have card populate

    @FXML
    private void chooseCard() {

    }

    public void playGame() throws IOException {
        // server is listening on port 5056
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    if (dis.available() >0){
                        String temp = dis.readUTF();
                        if("cardsSend:".equals(temp.substring(0,temp.indexOf(":")+1))) {
                            ArrayList<String> cards = new ArrayList<>(Arrays.asList(temp.substring(temp.indexOf(":") + 1).split(",")));
                            for (int i =0;i<p1.length;i++) {
                                p1[i].setImage(new Image(cards.get(i)));
                                p2[i].setImage(new Image("resources/BACK-1.jpg"));
                                p3[i].setImage(new Image("resources/BACK-1.jpg"));
                                p4[i].setImage(new Image("resources/BACK-1.jpg"));
                            }

                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }


        }.start();

    }


    private void fillCards(){




    }













}