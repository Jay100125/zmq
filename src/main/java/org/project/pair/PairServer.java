package org.project.pair;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class PairServer {
  public static void main(String[] args) {
    try(ZContext context = new ZContext()) {
      ZMQ.Socket server = context.createSocket(ZMQ.PAIR);
      server.bind("tcp://*:5555");

      System.out.println("Listening on port 5555");
      int count = 0;

      while(!Thread.currentThread().isInterrupted()) {
        String msg = "Message " + count;

        server.send(msg);
        System.out.println("Sent " + msg);

        String reply = server.recvStr();
        System.out.println("Recv " + reply);

        Thread.sleep(1000);
        count++;

      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
