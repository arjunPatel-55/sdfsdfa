package sample;


import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;



// ClientHandler class
public class Server extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    public Server(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        while (true)
        {
            try {
                // receive the answer from client
                received = dis.readUTF();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public String getMsg(){
        try {
            return dis.readUTF();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        }








}