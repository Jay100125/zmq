package org.project.pubsub;

//import org.zeromq.ZMQ;
//
//public class Pub {
//  public static void main(String[] args) throws InterruptedException {
//    ZMQ.Context context = ZMQ.context(1);
//    ZMQ.Socket publisher = context.socket(ZMQ.PUB);
//    publisher.bind("tcp://*:5556");
//
//    // Send first message before subscriber connects (this will be lost)
//    System.out.println("Publisher: Sending first message (lost)");
//    publisher.send("TopicA FirstMessage");
//
//    Thread.sleep(2000);
//    // Wait to give subscriber time to connect
//
//    // Now send more messages
//    for (int i = 1; i <= 5; i++) {
//      String message = "TopicA Message " + i;
//      System.out.println("Publisher: Sending -> " + message);
//      publisher.send(message);
//      Thread.sleep(1000);
//    }
//
//    publisher.close();
//    context.term();
//  }
//}
import org.zeromq.*;

public class Pub {
  public static void main(String[] args) throws InterruptedException {
    ZMQ.Context context = ZMQ.context(1);

    // REP socket to synchronize with subscriber
    ZMQ.Socket syncServer = context.socket(ZMQ.REP);
    syncServer.bind("tcp://*:5555");

    // PUB socket for messaging
    ZMQ.Socket publisher = context.socket(ZMQ.PUB);
    publisher.bind("tcp://*:5556");

    // Wait for subscriber to send "READY"
    System.out.println("Publisher: Waiting for subscriber to be ready...");
    String readyMsg = syncServer.recvStr();
    System.out.println("Publisher: Received -> " + readyMsg);
    syncServer.send("ACK");  // Send acknowledgment

    // Now send messages
    System.out.println("Publisher: Sending first message...");
    publisher.send("TopicA FirstMessage");

    for (int i = 1; i <= 5; i++) {
      String message = "TopicA Message " + i;
      System.out.println("Publisher: Sending -> " + message);
      publisher.send(message);
      Thread.sleep(1000);
    }

    publisher.close();
    syncServer.close();
    context.term();
  }
}
