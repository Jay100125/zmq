package org.project.dealertodealer;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DealerReplica {
  public static void main(String[] args) {
    List<String> combinations = Arrays.asList(
      "A-B-C", "X-Y-Z","1-2-3", "Alpha-Beta-Gamma");

    try(ZContext context = new ZContext()) {

      ZMQ.Socket dealer = context.createSocket(SocketType.DEALER);
      dealer.bind("tcp://*:5555");

      System.out.println("Dealer started");

      for(String combination : combinations) {
        System.out.println("Sending " + combination);

      }
    }

  }
}
