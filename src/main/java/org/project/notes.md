## zeromq

- ZeroMQ sockets carry messages, like UDP, rather than a stream of bytes as TCP does.
- ZeroMQ sockets do their I/O in a background thread. This means that messages arrive in local input queues and are sent from local output queues, no matter what your application is busy doing.
- The zmq_send() method does not actually send the message to the socket connection(s). It queues the message so that the I/O thread can send it asynchronously. It does not block except in some exception cases. So the message is not necessarily sent when zmq_send() returns to your application.
- We said that ZeroMQ does I/O in a background thread. One I/O thread (for all sockets) is sufficient for all but the most extreme applications. When you create a new context, it starts with one I/O thread.
- ZeroMQ does for you. It delivers blobs of data (messages) to nodes, quickly and efficiently. You can map nodes to threads, processes, or nodes. ZeroMQ gives your applications a single socket API to work with, no matter what the actual transport (like in-process, inter-process, TCP, or multicast)

The built-in core ZeroMQ patterns are:

- Request-reply, which connects a set of clients to a set of services. This is a remote procedure call and task distribution pattern.

- Pub-sub, which connects a set of publishers to a set of subscribers. This is a data distribution pattern.

- Pipeline, which connects nodes in a fan-out/fan-in pattern that can have multiple steps and loops. This is a parallel task distribution and collection pattern.

- Exclusive pair, which connects two sockets exclusively. This is a pattern for connecting two threads in a process, not to be confused with “normal” pairs of sockets.


## HWM - high water mark
- ZMQ uses the concept of HWM (high-water mark) to define the capacity of it's internal pipes. Each connection out of a socket or into a socket has its own pipe and HWM for sending and/or receiving depending on the socket type. Some sockets ( PUB, PUSH, RADIO ) only have send buffers. Some ( SUB, PULL, DISH ) only have receive buffers. Some ( REQ, REP, DEALER, ROUTER, PAIR ) have both send and receive buffers.

- The available socket options are:

  - ZMQ_SNDHWM: Set high water mark for outbound messages (... on the publisher socket )
   - ZMQ_RCVHWM: Set high water mark for inbound messages (... on the subscriber socket )
- Both ZMQ_PUB and ZMQ_SUB have the ZMQ_HWM option action set to "Drop" therefore when the limits are reached the memory of the subscriber or the publisher should stop growing, by what amount depends on the ZMQ buffers.


## reply envoleop
- The ZeroMQ reply envelope formally consists of zero or more reply addresses, followed by an empty frame (the envelope delimiter), followed by the message body (zero or more frames).


-  let’s say the REQ socket has a 3-byte identity ABC. Internally, this means the ROUTER socket keeps a hash table where it can search for ABC and find the TCP connection for the REQ socket.
