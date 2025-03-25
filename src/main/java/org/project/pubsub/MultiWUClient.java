package org.project.pubsub;

import java.util.StringTokenizer;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class MultiWUClient {
  public static void main(String[] args) {
    try (ZContext context = new ZContext()) {
      System.out.println("Collecting updates from multiple weather servers...");

      ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
      subscriber.connect("tcp://localhost:5556");
      subscriber.connect("tcp://localhost:5557");
      subscriber.connect("tcp://localhost:5558");

      // Subscribe to a prefix to match server zipcodes (10000-19999)
      subscriber.subscribe("10".getBytes(ZMQ.CHARSET)); // Broad subscription
      subscriber.setReceiveTimeOut(5000); // 5-second timeout

      int update_nbr = 0;
      long total_temp = 0;

      while (update_nbr < 100) {
        String message = subscriber.recvStr(0);
        if (message == null) {
          System.out.println("No message received within timeout, exiting...");
          break;
        }
        message = message.trim();
        StringTokenizer sscanf = new StringTokenizer(message, " ");
        if (sscanf.countTokens() != 3) {
          System.out.println("Malformed message: " + message);
          continue;
        }

        int zipcode = Integer.parseInt(sscanf.nextToken());
        int temperature = Integer.parseInt(sscanf.nextToken());
        int relhumidity = Integer.parseInt(sscanf.nextToken());

        total_temp += temperature;
        update_nbr++;
      }

      System.out.println(
        String.format("Average temperature across all zip codes: %d",
          update_nbr > 0 ? (int)(total_temp / update_nbr) : 0)
      );
    }
  }
}
