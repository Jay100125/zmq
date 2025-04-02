package org.project.TestingLimits;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client2 {
  public static void main(String[] args) {
    try(ZContext zContext = new ZContext()) {
      ZMQ.Socket socket = zContext.createSocket(SocketType.PULL);
      socket.connect("tcp://localhost:4444");


      socket.setReceiveBufferSize(4*1024);
      for(int i = 0; i<4; i++)
      {
        byte[] part = socket.recv();
        System.out.println("received " + part.length);
      }

    }
  }
}
