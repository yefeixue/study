伪异步IO模型实现时间服务器：
伪异步IO通信框架采用了线程池实现，因此避免了为每个请求都创建一个独立的线程造成的线程资源消耗问题。
但是底层实际还是采用同步阻塞IO模型，因此无法从根本上解决问题。

BIO源码分析：
InputStream.read(byte b[])
当对Socket的输入流进行读取操作的时候，它会一直阻塞下去，直到发生如下三种情况：
	有数据可读；
	可用数据已经读取完毕；
	发生空指针或者IO异常。
这意味着当对方发送请求或者应答消息比较缓慢，或者网络传输较慢时，
读取输入流一方的通信线程将被长时间阻塞，如果对方要60s才能够将数据发送完成，
读取一方的IO线程也将会被同步阻塞60s，在此期间，其他接入消息只能在消息队列中排队等候。

OutputStream.write(byte b[])
当调用OutputStream的write方法写输出流的时候，它将会被阻塞，
直到所有要发送的字节全部写入完毕，或者发生异常。
当消息的接收方处理缓慢的时候，将不能及时的从TCP缓冲区读取数据，
这将会导致发送方的TCP window size 不断减小，直到为0，
双方处于Keep-Alive状态，消息发送方将不能再向TCP缓冲区写入消息，
这时如果采用的是同步阻塞IO，writer操作讲会被无限期阻塞，
直到TCP window size大于0或者发生IO异常。

通过API文档分析了解到：
	BIO的读和写操作都是同步阻塞的，阻塞的时间取决于对方IO线程的处理速度和网络IO的传输速度。

伪异步IO通讯对方返回应答时间过长会引起的级联故障：
	服务端处理缓慢，返回应答消息耗费60s，平常只需要10ms；
	采用伪异步IO的线程正在读取故障服务节点的响应，由于读取输入流是阻塞的，它将会被同步阻塞60s；
	如果所有的可用线程都被服务器阻塞，后续所有的IO消息都将在队列中排队；
	由于线程池采用阻塞队列实现，当队列积满之后，后续入队列的操作将被阻塞；
	由于前段只有一个Accptor线程接收客户端接入，它被阻塞在线程池的同步阻塞队列之后，新
的客户端请求消息将被拒绝，客户端会发生大量的链接超时。
	由于几乎所有的连接都超时，调用者会认为系统已经奔溃，无法接收新的请求消息。
	















