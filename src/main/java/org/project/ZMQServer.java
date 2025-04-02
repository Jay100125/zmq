package org.project;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZMQServer {
  private static final int HWM = 5;
  private static final String ADDRESS = "tcp://*:5555";

  public static void main(String[] args) {
    try (ZContext context = new ZContext()) {
      ZMQ.Socket server = context.createSocket(SocketType.REP);
      server.setHWM(HWM);
      server.setLinger(0);
      server.bind(ADDRESS);
      System.out.println("Server started at " + ADDRESS);

      while (!Thread.currentThread().isInterrupted()) {
        String message = server.recvStr();
        if (message != null) {
          System.out.println("Server received: " + message);
          Thread.sleep(50000); // Simulating processing delay
          server.send("ACK: " + message);
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
