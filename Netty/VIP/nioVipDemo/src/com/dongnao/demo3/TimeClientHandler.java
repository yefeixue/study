package com.dongnao.demo3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����5:23:16
 * 
 */
public class TimeClientHandler implements Runnable {
	
	private String host;
	private int port;
	private SocketChannel socketChannel;
	private Selector selector;
	private volatile boolean stop;
	
	public TimeClientHandler(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			//��SocketChannel
			socketChannel = SocketChannel.open();
			//����Selector�߳�
			selector = Selector.open();
			//����Ϊ������ģʽ
			socketChannel.configureBlocking(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		try {
			doConnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		while(!stop){
			//��ѵͨ����״̬
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				SelectionKey selectionKey = null;
				while(iterator.hasNext()){
					selectionKey = iterator.next();
					iterator.remove();
					try {
						handleInput(selectionKey);
					} catch (Exception e) {
						if(selectionKey!=null){
							selectionKey.cancel();
							if(selectionKey.channel()!=null){
								selectionKey.channel().close();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		if(selector !=null){
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey selectionKey) throws Exception {
		if(selectionKey.isValid()){
			SocketChannel client = (SocketChannel) selectionKey.channel();
			if (selectionKey.isConnectable()){
				if(client.finishConnect()){
					client.register(selector, SelectionKey.OP_READ);
					doWrite(client);
				}else{
					System.exit(1);
				}
			}
			if (selectionKey.isReadable()) {
				ByteBuffer receivebuffer = ByteBuffer.allocate(1024);
				int count = client.read(receivebuffer);
				if (count > 0) {
					receivebuffer.flip();
					byte[] bytes = new byte[receivebuffer.remaining()]; //remaining()����
					receivebuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("Now is "+body);
					this.stop = true;
				}else if(count < 0){
					selectionKey.channel();
					client.close();
				}else{
					
				}
			}
		}
	}

	private void doConnect() throws Exception {
		//���ӷ����
		boolean connect = socketChannel.connect(new InetSocketAddress(host, port));
		//�ж��Ƿ����ӳɹ���������ӳɹ��������Channel�Ķ�״̬��
		if(connect){
			socketChannel.register(selector, SelectionKey.OP_READ);
			//д����  д�������
			doWrite(socketChannel);
		}else{
			//���û�����ӳɹ��������·������ע��Connect״̬
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
		
	}

	private void doWrite(SocketChannel channel) throws IOException {
		ByteBuffer sendbuffer = ByteBuffer.allocate(1024);
		sendbuffer.put("QUERY TIME ORDER".getBytes());
        sendbuffer.flip();
        //��Channel��д��ͻ��˵�����ָ��  д�������
        channel.write(sendbuffer);
        if(!sendbuffer.hasRemaining()){
        	System.out.println("Send order to server succeed.");
        }
	}
}
