
/*
 *  GreaterServerProtocol.java
 *
 *  A server side protocol implementation using socklib.ServerListener
 *  See also: GreeterServerExample.java
 *
 *  (C) 2022 Ali Jannatpour <ali.jannatpour@concordia.ca>
 *
 *  This code is licensed under GPL.
 *
 */

package com.comp6231.lab3.server;

import com.comp6231.socklib.ListenerInfo;
import com.comp6231.socklib.SimpleSocketProtocol;

import java.io.IOException;
import java.net.Socket;

public class GreeterServerProtocol extends SimpleSocketProtocol {
    public GreeterServerProtocol(Socket s, ListenerInfo info) {
        super(s, info);
    }

    public void run() throws IOException {
        // TODO modify this
        sendln("OK Greeter server ready to greet you.");
        while (isRunning() && isConnected()) {
            String data = recvln();
            switch (data.toUpperCase()) {
                case "HELO":
                    sendln(String.format("HELO %s; pleased to meet you!", getSocket().getRemoteSocketAddress().toString()));
                    break;
                case "BYE":
                case "EXIT":
                case "QUIT":
                    sendln("BYE, it was nice seeing you.");
                    close();
                    return;
                default:
                    sendln("ERR Sorry did not understand. Say BYE if you wish to exit.");
                    break;
            }
        }
        close();
    }
}
