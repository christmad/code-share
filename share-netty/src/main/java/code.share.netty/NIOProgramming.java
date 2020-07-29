package code.share.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by christmad on 2019/9/28.
 */
public class NIOProgramming {

    public void NIOserver() throws IOException {
        // throw ex
        // 创建 selector：NIO模型中通常会有两个线程，每个线程绑定一个轮询器 selector
        Selector serverSelector = Selector.open();      // serverSelector 负责轮询是否有新的连接
        Selector clientSelector = Selector.open();      // clientSelector 负责轮询连接是否有数据可读

        // 模拟服务端接收新连接。
        // 在 NIO server 中使用了两条线程，对应了两个 selector：
        // 一条线程专门负责处理新连接，通过 accept函数；对应代码中的 selector 为 serverSelector
        // 另一条线程专门负责处理已有连接上的读事件，该线程持有 clientSelector，并对注册到这个selector的连接上的读事件处理，demo中的处理是通过 println 函数输出
        // 注意：NIO 中的 selector.select 仍是阻塞的, 其优势并不是对于单个连接能处理得更快，而是 select 能返回多个就绪的连接，即能处理更多的连接
        new Thread(() -> {
            try {
                // NIO服务端启动
                ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                // 在 bind 的时候底层已经开始监听了，windows 1.8 JDK 默认实现 backlog=50，即最多允许50条全连接
                listenerChannel.socket().bind(new InetSocketAddress(8000));
                listenerChannel.configureBlocking(false);   // non-blocking

                // ServerSocketChannel#register 函数是将与操作系统交互（I/O）的工作交给持有 serverSelector，
                // 持有 serverSelector 对象的线程在 while 循环中处理新连接：Selector.select() 函数会获得操作系统层面做好三次握手的客户端连接，一次可获得批量
                //      实际开发中可能是多条线程来共同负责处理一大批连接，即 1个serverSelector、多个clientSelector。如果可以对同一个端口多次 bind 的话，有可能多个 serverSelector？
                // 注意：register 方法是来自 ServerSocketChannel，这个方法的作用是让 selector 获得 channel 的引用。
                //      操作系统底层处理的经过三次握手的连接应该是体现在 Channel 上，channel 负责存储网络数据，而 Selector 负责数据操作

                // SelectionKey.OP_ACCEPT 指明 serverSelector 要监听的是新连接
                listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                while (true) {
                    // 整个 while 循环处理的是将 serverSelector#select 获取到的连接事件绑定到 clientSelector 上，由它来监听这些连接的 SelectionKey.OP_READ 事件

                    // 等待新连接，阻塞时间为 1ms
                    if (serverSelector.select(1) > 0) {
                        Set<SelectionKey> acceptIdSet = serverSelector.selectedKeys();
                        Iterator<SelectionKey> acceptIdItor = acceptIdSet.iterator();
                        while (acceptIdItor.hasNext()) {
                            SelectionKey key = acceptIdItor.next();

                            if (key.isAcceptable()) {
                                // 客户端连接经过三次握手进来后，我们需要处理客户端连接的事件：读或写
                                // 本例中模拟的是监听连接的可读事件——请求
                                try {
                                    // 通过 ServerSocketChannel#accept 函数获取 SocketChannel
                                    // 底层原理是客户端请求和上面绑定的 8000 端口连接，三次握手后，操作系统会启用另一个端口来标识这条连接，
                                    // 这条新连接在 NIO API 上反应出来就是一个 SocketChannel 对象，我们将这个对象注册到 clientSelector 以达到统一管理客户端连接读事件的目的
                                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    // 调用 SocketChannel#register 函数，表示 clientSelector 将会关注 channel 的可读事件；一旦对应的 channel 标志位变化，就可以进行下一步操作
                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    // 只是在本轮的 Selector#selectedKeys 中移除了，可能是帮助清空此次 Selector#selectedKeys 产生的临时 Set<SelectionKey> 空间
                                    acceptIdItor.remove();
                                }
                            }

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // 在上面的代码中，我们只是把连接注册到 clientSelector 中并监听它们的读事件，下面则是批量处理读事件的代码
        new Thread(() -> {
            while (true) {
                try {
                    // 批量轮询有哪些连接有数据可读，阻塞时间为 1ms
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> readIdSet = clientSelector.selectedKeys();
                        Iterator<SelectionKey> readIdItor = readIdSet.iterator();
                        while (readIdItor.hasNext()) {
                            SelectionKey key = readIdItor.next();

                            if (key.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer buf = ByteBuffer.allocate(1024); // 申请 1KB 缓冲区
                                    // NIO 读面向 buf, 将 clientChannel 数据缓存到 buf 对象中
                                    clientChannel.read(buf);
                                    buf.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(buf).toString());
                                } finally {
                                    readIdItor.remove();
                                    // 暂时不知道有什么用
                                    key.interestOps(SelectionKey.OP_READ);
                                }

                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
