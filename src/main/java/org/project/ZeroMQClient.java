package org.project; ////package org.project;
////
////import org.zeromq.ZMQ;
////
////import java.util.Random;
////
////public class ZeroMQClient {
////  public static void main(String[] args) {
////    ZMQ.Context context = ZMQ.context(1);
////    ZMQ.Socket socket = context.socket(ZMQ.REQ); // REQ = Request pattern
////    socket.connect("tcp://localhost:5555");
////
////    // Sending multipart message
////    String[] messages = {"Hello", "from", "ZeroMQ", "Client"};
////    Random rand = new Random();
////    int partsToSend = rand.nextInt(messages.length) + 1; // Send 1 to 4 parts
////
////    System.out.println("Sending " + partsToSend + " message parts...");
////
////    for (int i = 0; i < partsToSend - 1; i++) {
////      socket.sendMore(messages[i].getBytes()); // Send with "more" flag
////    }
////    socket.send(messages[partsToSend - 1].getBytes()); // Final part
////
////    System.out.println("Multipart message sent!");
////
////    // Receiving response
////    String response1 = socket.recvStr();
////    System.out.println("Response Part 1: " + response1);
////
////    if (socket.hasReceiveMore()) {
////      String response2 = socket.recvStr();
////      System.out.println("Response Part 2: " + response2);
////    }
////  }
////}
//////import org.zeromq.ZMQ;
//////
//////public class ZeroMQClient {
//////  public static void main(String[] args) {
//////    ZMQ.Context context = ZMQ.context(1);
//////    ZMQ.Socket socket = context.socket(ZMQ.REQ);
//////    socket.connect("tcp://localhost:5555");
//////
//////    // Convert String to byte[]
//////    byte[] part1 = new byte[1024];
//////    byte[] part2 = new byte[1024*2];
//////    byte[] part3 = new byte[1024*3];
//////
//////    // Sending multipart message (in bytes)
//////    socket.sendMore(part1);  // First part
//////    socket.sendMore(part2);  // Second part
//////    socket.send(part3);      // Last part
//////
//////    System.out.println("Sent multipart message in bytes!");
//////
//////    // Receiving response
//////    byte[] response1 = socket.recv();
//////    System.out.println("Response Part 1: " + new String(response1));
//////
//////    if (socket.hasReceiveMore()) {
//////      byte[] response2 = socket.recv();
//////      System.out.println("Response Part 2: " + new String(response2));
//////    }
//////  }
//////}
//
//import org.zeromq.ZMQ;
//
//public class ZeroMQClient {
//  public static void main(String[] args) {
//    ZMQ.Context context = ZMQ.context(1);
//    ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
//    subscriber.connect("tcp://localhost:5556");
//
//    // Subscribe to all messages
//    subscriber.subscribe("".getBytes());
//
//    System.out.println("Subscriber ready...");
//
//    while (true) {
//      // Receive parts of the message
//      byte[] part1 = subscriber.recv();
//      System.out.println("Received part1 of size: " + part1.length / 1024 + " KB");
//
//      byte[] part2 = subscriber.recv();
//      System.out.println("Received part2 of size: " + part2.length / 1024 + " KB");
//
//      byte[] finalPart = subscriber.recv();
//      System.out.println("Received final part of size: " + finalPart.length / 1024 + " KB");
//    }
//  }
//}

import org.zeromq.ZMQ;

public class ZeroMQClient {
  public static void main(String[] args) {
    ZMQ.Context context = ZMQ.context(1);
    ZMQ.Socket subscriber = context.socket(ZMQ.REQ);
    subscriber.connect("tcp://localhost:5556");

    // Subscribe to all messages
//    subscriber.subscribe("".getBytes());

    System.out.println("Subscriber ready...");

    while (true) {
      // Receive parts of the message
      byte[] part1 = subscriber.recv();
      System.out.println("Received part1 of size: " + part1.length / 1024 + " KB");

      byte[] part2 = subscriber.recv();
      System.out.println("Received part2 of size: " + part2.length / 1024 + " KB");

      byte[] finalPart = subscriber.recv();
      System.out.println("Received final part of size: " + finalPart.length / 1024 + " KB");
    }
  }
}
