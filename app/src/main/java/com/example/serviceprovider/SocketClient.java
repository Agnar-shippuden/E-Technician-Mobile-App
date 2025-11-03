package com.example.serviceprovider;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketClient {

    private static Socket socket;
    private static SocketClient instance;

    private SocketClient()
    {
        if(socket == null)
        {
            try {
                socket = IO.socket("http://10.0.2.2:3000/");
                socket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static SocketClient getInstance() {

        if(instance == null)
        {
            instance = new SocketClient();
        }
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void SendMessage(String user_name, String message)
    {
        socket.emit("message", user_name, message);
    }

}
