//package org.project.TestingLimits;
//
//import org.zeromq.SocketType;
//import org.zeromq.ZContext;
//import org.zeromq.ZMQ;
//
//public class Server {
//  public static void main(String[] args) {
//    try(ZContext zContext = new ZContext()) {
//      ZMQ.Socket socket = zContext.createSocket(SocketType.REP);
//      socket.bind("tcp://*:4444");
//
//      socket.setLinger(50000);
//      socket.setSendBufferSize(30*1024);
//
//      socket.recv(0);
//
//      int totalSend = 0;
//      socket.sendMore(new byte[30*1024]);
//      for(int i = 0; i<10; i++)
//      {
//        socket.sendMore(new byte[25*1024]);
//      }
//
//      socket.send(new byte[35*1024]);
//      totalSend++;
//      System.out.println("sent " + totalSend);
//    }
//  }
//}

package org.project.TestingLimits;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Server {
  public static void main(String[] args) {
    try (ZContext zContext = new ZContext()) {
      ZMQ.Socket socket = zContext.createSocket(SocketType.REP);
      socket.bind("tcp://*:4444");

      socket.setLinger(50000);
      socket.setSendBufferSize(20 * 1024);

      String request = socket.recvStr();  // Receive string from client
      System.out.println("Received: " + request);

      int totalSend = 0;

      socket.sendMore("Part-1: " + "A".repeat(30 * 1024));  // 30 KB
      for (int i = 0; i < 10; i++) {
        socket.sendMore("Part-" + (i + 2) + ": " + "B".repeat(30 * 1024));  // 10 x 25 KB
        Thread.sleep(1);
      }

      socket.sendMore("Final Part: " + "C".repeat(35 * 1024));  // 35 KB
      socket.send("Jay");
      totalSend++;
      System.out.println("Sent " + totalSend + " messages");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
