package org.project.pubsub;

//import org.zeromq.ZMQ;
//
//public class Sub {
//  public static void main(String[] args) {
//    ZMQ.Context context = ZMQ.context(1);
//    ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
//    subscriber.connect("tcp://localhost:5556");
//
//    // Subscribe to all messages
//    subscriber.subscribe("TopicA".getBytes());
//
//    System.out.println("Subscriber: Connected and listening...");
//
//    while (true) {
//      String message = subscriber.recvStr();
//      System.out.println("Subscriber: Received -> " + message);
//    }
//  }
//}

import org.zeromq.*;

public class Sub {
  public static void main(String[] args) throws InterruptedException {
    ZMQ.Context context = ZMQ.context(1);

    // REQ socket for handshake
    ZMQ.Socket syncClient = context.socket(ZMQ.REQ);
    syncClient.connect("tcp://localhost:5555");

    // PUB-SUB socket
    ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
    subscriber.connect("tcp://localhost:5556");
    subscriber.subscribe("TopicA".getBytes());

    // Send "READY" to publisher
    System.out.println("Subscriber: Sending READY to publisher...");
    syncClient.send("READY");

    // Wait for acknowledgment from publisher
    String ack = syncClient.recvStr();
    System.out.println("Subscriber: Received ACK -> " + ack);

    // Now listen for messages
    System.out.println("Subscriber: Connected and listening...");
    while (true) {
      String message = subscriber.recvStr();
      System.out.println("Subscriber: Received -> " + message);
    }
  }
}
