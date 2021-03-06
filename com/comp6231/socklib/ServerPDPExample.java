
package com.comp6231.socklib;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class ServerPDPExample {
	DatagramSocket dSocket = null;
	private int udpPort = 6231;
	private int tcpPort;
	public void start(int port) {
	new Thread(new Runnable() {
		@Override
		public void run(){
			tcpPort = port;
			runSdu();
			dSocket.close();
			}
		}).start();
	}
	private void runSdu() {
		try {
			byte[] receiveBuff = new byte[1024]; //receiving buffer
			dSocket = new DatagramSocket(udpPort);
			dSocket.setBroadcast(true);
			DatagramPacket dPacket = new DatagramPacket(receiveBuff, receiveBuff.length);
			System.out.println("\nStarted UDP server, listening on Broadcast IP, port 6231\n");
			while (true) {
				System.out.println("> Ready to receive b-cast packets...");
				dSocket.receive(dPacket); //receiving data
				System.out.println("> Received packet from " + dPacket.getAddress().getHostAddress() 
					+ ":" + dPacket.getPort());
				String msg = new String(dPacket.getData(), dPacket.getOffset(), dPacket.getLength());
				if (msg.equals("PEER_REQUEST")) {
					//TODO implement TCP port passing to clients
					
					String srvResponse = "PEER_RESPONSE ";
					System.out.println(srvResponse);
					byte[] sendBuff = srvResponse.getBytes();
					DatagramPacket dPacket2 = new DatagramPacket(sendBuff, sendBuff.length, dPacket.getAddress(), dPacket.getPort());
					dSocket.send(dPacket2); 	//Send a response
					System.out.println(getClass().getName() + "> Sent response to client IP: "
						+ dPacket.getAddress().getHostAddress() + ":" + dPacket.getPort());
				}
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  }
