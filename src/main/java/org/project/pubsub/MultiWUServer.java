package org.project.pubsub;

import java.util.Random;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class MultiWUServer {
  public static void main(String[] args) throws Exception {
    int[] ports = {5556, 5557, 5558};
    for (int port : ports) {
      new Thread(() -> startServer(port)).start();
    }
    // Keep main thread alive to allow servers to run
    Thread.sleep(Long.MAX_VALUE);
  }

  private static void startServer(int port) {
    try (ZContext context = new ZContext()) {
      ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
      publisher.bind("tcp://*:" + port);

      Random srandom = new Random(System.currentTimeMillis());
      System.out.println("Weather server started on port " + port);

      while (!Thread.currentThread().isInterrupted()) {
        int zipcode = 10000 + srandom.nextInt(10000);
        int temperature = srandom.nextInt(215) - 80 + 1;
        int relhumidity = srandom.nextInt(50) + 10 + 1;

        String update = String.format("%05d %d %d", zipcode, temperature, relhumidity);
        Thread.sleep(1000);
        publisher.send(update, 0);
      }
    } catch (Exception e) {
      System.out.println("Server on port " + port + " failed: " + e.getMessage());
    }
  }
}
