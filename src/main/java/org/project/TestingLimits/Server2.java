package org.project.TestingLimits;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Server2 {
  public static void main(String[] args) throws InterruptedException {
    try(ZContext zContext = new ZContext()) {
      ZMQ.Socket socket = zContext.createSocket(SocketType.PUSH);
      socket.bind("tcp://*:4444");

      socket.setLinger(-1);
      socket.setSendBufferSize(8*1024);

      int totalSend = 0;

//      byte [] toSend = new byte[7*1024];

      for(int i = 0; i<1; i++)
      {
//        socket.sendMore(toSend);
        socket.send("Part-" + (i + 2) + ": " + "B".repeat(8 * 1024));
//                Thread.sleep(1000);
//        System.out.println("sent " + toSend.length);
      }

//      socket.send("JAY");
    }
  }
}
