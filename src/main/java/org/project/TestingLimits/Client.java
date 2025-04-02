//package org.project.TestingLimits;
//
//import org.zeromq.SocketType;
//import org.zeromq.ZContext;
//import org.zeromq.ZMQ;
//
//public class Client {
//  public static void main(String[] args) {
//    try(ZContext zContext = new ZContext()) {
//      ZMQ.Socket socket = zContext.createSocket(SocketType.REQ);
//      socket.connect("tcp://localhost:4444");
//
//
//      int gotPart = 0;
//      socket.send(new byte[1024]);
//      do {
//        byte[] part = socket.recv();
//        System.out.println("received " + ++gotPart);
//      } while (socket.hasReceiveMore());
//
//    }
//  }
//}
package org.project.TestingLimits;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client {
  public static void main(String[] args) {
    try (ZContext zContext = new ZContext()) {
      ZMQ.Socket socket = zContext.createSocket(SocketType.REQ);
      socket.connect("tcp://localhost:4444");

      int gotPart = 0;
      socket.send("Hello Server!");  // Send a string message

      do {
        String part = socket.recvStr();
        System.out.println("Received Part " + (++gotPart) + ": " + part.substring(0, Math.min(50, part.length())) + "...");
      } while (socket.hasReceiveMore());
    }
  }
}
