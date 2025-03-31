package org.project.pair;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class PairClient {
  public static void main(String[] args) {
    try(ZContext context = new ZContext()) {
      ZMQ.Socket client = context.createSocket(ZMQ.PAIR);
      client.connect("tcp://localhost:5555");

      System.out.println("Connected to pair");

      while (!Thread.currentThread().isInterrupted()) {
        String msg = client.recvStr();
        System.out.println("Received: " + msg);

        String reply = "Ack: " + msg;
        client.send(reply);
        System.out.println("Sent: " + reply);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
