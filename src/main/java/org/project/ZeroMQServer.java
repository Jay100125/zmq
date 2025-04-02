package org.project;
//import org.zeromq.ZMQ;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ZeroMQServer {
//  public static void main(String[] args) {
//    ZMQ.Context context = ZMQ.context(1);
//    ZMQ.Socket socket = context.socket(ZMQ.REP); // REP = Reply pattern
//    socket.bind("tcp://*:5555");
//
//    System.out.println("ZeroMQ Server is waiting for messages...");
//
//    while (true) {
//      List<String> messageParts = new ArrayList<>();
//
//      // Receive all parts dynamically
//      do {
//        byte[] receivedPart = socket.recv(); // Receive byte array
//        System.out.println("Received: " + new String(receivedPart));
//        messageParts.add(new String(receivedPart));
//      } while (socket.hasReceiveMore());
//
//      // Print received message parts
//      System.out.println("Received message parts: " + messageParts);
//
//      // Send a response (Multipart)
//      socket.sendMore("ACK"); // First part
//      socket.send("Message received!"); // Last part
//    }
//  }
//}
//import org.zeromq.ZMQ;
//
//public class ZeroMQServer {
//  public static void main(String[] args) {
//    ZMQ.Context context = ZMQ.context(1);
//    ZMQ.Socket socket = context.socket(ZMQ.REP);
//    socket.bind("tcp://*:5555");
//
//    System.out.println("ZeroMQ Server is waiting for messages...");
//
//    while (true) {
//      do {
//        byte[] receivedPart = socket.recv(); // Receive byte array
//        System.out.println("Received: " + new String(receivedPart));
//      } while (socket.hasReceiveMore());
//
//      // Send response (as bytes)
//      socket.sendMore(new byte[1024]);
//      socket.send(new byte[2*1024]);
//    }
//  }
//}

import org.zeromq.ZMQ;

public class ZeroMQServer {
  public static void main(String[] args) {
    ZMQ.Context context = ZMQ.context(1);
    ZMQ.Socket publisher = context.socket(ZMQ.REP);
    publisher.bind("tcp://*:5556");

    // Set buffer size to 2MB
    int bufferSize = 2 * 1024; // 2MB
    publisher.setSendBufferSize(bufferSize);

    // High-water mark to control message queueing
    publisher.setHWM(10);

    System.out.println("Publisher is ready...");

    // Send first part (2MB)
    byte[] part1 = new byte[1024]; // 2MB
    publisher.sendMore(part1);
    System.out.println("Sent 2MB part1...");

    // Send second part (2MB)
    byte[] part2 = new byte[1024]; // 2MB
    publisher.sendMore(part2);
    System.out.println("Sent 2MB part2...");

    // Send final part (4MB)
    byte[] finalPart = new byte[4]; // 4MB
    publisher.send("finalPart".getBytes());
    System.out.println("Sent 4MB final part...");

    publisher.close();
    context.term();
  }
}
