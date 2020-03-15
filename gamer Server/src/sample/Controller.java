package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Controller {
    private Thread[] th;
    private DataOutputStream[] out;
    private DataInputStream[] in;
    @FXML
    private Label p1, p2, p3, p4;
    @FXML
    private Label serverStat;
    private int playerTurn;
    private int playersNum = 4;
    private Boolean hasStart = false;
    private Boolean stopServer = false;
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> p1Hand = new ArrayList<>();
    private ArrayList<Card> p2Hand = new ArrayList<>();
    private ArrayList<Card> p3Hand = new ArrayList<>();
    private ArrayList<Card> p4Hand = new ArrayList<>();
    private boolean haveDealtCardsToPlayers = false;


    public Controller() {
        th = new Server[playersNum];
        out = new DataOutputStream[playersNum];
        in = new DataInputStream[playersNum];


    }


    @FXML
    public void startUp() throws IOException {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
        hasStart = true;
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (hasStart) {
                    serverStat.setText("Server Status: ONLINE");
                    serverStat.setStyle("-fx-text-fill: green;");
                    hasStart = false;
                } else if (th[playersNum - 1] == null) {
                    Socket s = null;

                    try {
                        // socket object to receive incoming client requests
                        s = ss.accept();
                        System.out.println("A new client is connected : " + s);

                        // obtaining input and out streams
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        for (int i = 0; i < th.length; i++) {
                            if (th[i] == null) {
                                th[i] = new Server(s, dis, dos);
                                out[i] = dos;
                                in[i] = dis;
                                out[i].writeUTF(String.valueOf(i + 1));
                                if (i == 0) {
                                    p1.setText("P1 :Conected "+in[i].readUTF());
                                } else if (i == 1) {
                                    p2.setText("P2 :Conected "+in[i].readUTF());
                                } else if (i == 2) {
                                    p3.setText("P3 :Conected "+in[i].readUTF());
                                } else if (i == 3) {
                                    p4.setText("P4 :Conected "+in[i].readUTF());
                                }
                                break;
                            }
                        }


                    } catch (Exception e) {
                        try {
                            assert s != null;
                            s.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }else  if (!haveDealtCardsToPlayers) {
                    try {
                        dealCardsToPlayers();
                        haveDealtCardsToPlayers = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else { // the game starts
                    for (int i = 0; i < in.length; i++) {
                        try {
                            if (in[i].available() > 0) {
                                String temp = in[i].readUTF() + i;

                            }
                        } catch (IOException ignored) {

                        }
                    }


                }
                if (stopServer) {
                    stop();
                    stopServer = false;

                }
            }


        }.start();


    }

//deals cards
    private void dealCardsToPlayers() throws IOException {
        deck.clear();
        for (int i = 2; i <= 14; i++) {
            deck.add(new Card("C" + (i)));
            deck.add(new Card("D" + (i)));
            deck.add(new Card("H" + (i)));
            deck.add(new Card("S" + (i)));
        }


        int randInd;
        for (int i = 0; i < 13; i++) {
            randInd = (int) (Math.random() * deck.size());
            p1Hand.add(deck.get(randInd));
            deck.remove(randInd);
            randInd = (int) (Math.random() * deck.size());
            p2Hand.add(deck.get(randInd));
            deck.remove(randInd);
            randInd = (int) (Math.random() * deck.size());
            p3Hand.add(deck.get(randInd));
            deck.remove(randInd);
            randInd = (int) (Math.random() * deck.size());
            p4Hand.add(deck.get(randInd));
            deck.remove(randInd);
        }
        String handMsg = "cardsSend:";
        for (Card x : p1Hand) {
            handMsg += x.getCardPath() + ",";
        }
        out[0].writeUTF(handMsg);
        handMsg = "cardsSend:";
        for (Card x : p2Hand) {
            handMsg += x.getCardPath() + ",";
        }
        out[1].writeUTF(handMsg);
        handMsg = "cardsSend:";
        for (Card x : p3Hand) {
            handMsg += x.getCardPath() + ",";
        }
        out[2].writeUTF(handMsg);
        handMsg = "cardsSend:";
        for (Card x : p4Hand) {
            handMsg += x.getCardPath() + ",";
        }
        out[3].writeUTF(handMsg);



    }

    private void placeCardInCenter(String input) throws IOException {
//        TODO get a proper code to be made so i know what is for what
        switch (input.substring(0, 1)) {
            case "1":
                out[1].writeUTF(input);
                out[2].writeUTF(input);
                out[3].writeUTF(input);
                break;
            case "2":
                out[0].writeUTF(input);
                out[2].writeUTF(input);
                out[3].writeUTF(input);
                break;
            case "3":
                out[0].writeUTF(input);
                out[1].writeUTF(input);
                out[3].writeUTF(input);
                break;
            case "4":
                out[0].writeUTF(input);
                out[1].writeUTF(input);
                out[2].writeUTF(input);
                break;
        }
    }


    @FXML
    private void stops() {
        serverStat.setText("Server Status: OFFLINE");
        serverStat.setStyle("-fx-text-fill: red;");
        p1.setText("P1 :Not Conected");
        p2.setText("P2 :Not Conected");
        p3.setText("P3 :Not Conected");
        p4.setText("P4 :Not Conected");
        stopServer = true;
    }




}




