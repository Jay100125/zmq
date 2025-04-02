package org.project;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZMQClient {
//  private static final int HWM = 5;
  private static final String ADDRESS = "tcp://localhost:5555";

  public static void main(String[] args) {
    try (ZContext context = new ZContext()) {
      ZMQ.Socket client = context.createSocket(SocketType.REQ);
//      client.setHWM(HWM);
      client.connect(ADDRESS);
      System.out.println("Client connected to " + ADDRESS);

      for (int i = 1; i <= 10; i++) {
        String msg = "Message " + i;
        client.send(msg);
        System.out.println("Client sent: " + msg);

        String reply = client.recvStr();
        System.out.println("Client received: " + reply);
      }
    }
  }
}
