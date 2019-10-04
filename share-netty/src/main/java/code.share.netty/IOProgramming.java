package code.share.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by christmad on 2019/9/28.
 */
public class IOProgramming {

    public void IOclient() {
        new Thread(() -> {
            try {
                // IO模型-客户端连接服务器
                Socket socket = new Socket("localhost", 8000);
                while (true) {
                    try {
                        // 每隔 2s 向服务器发送一条信息
                        socket.getOutputStream().write((new Date() + " : hello").getBytes());
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (IOException e) {
            }

        }).start();

    }

    public void IOserver() throws IOException {
        // IO模型-服务端监听端口
        ServerSocket server = new ServerSocket(8000);

        new Thread(() -> {
            while (true) {
                try {
                    // (1) 阻塞方式获取新连接
                    Socket socket = server.accept();

                    // (2) 将新连接绑定到一条新线程上去处理
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream input = socket.getInputStream();
                            // (3) 按字节流方式读取数据
                            while ((len = input.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                        }
                    }).start();
                } catch (IOException ex) {
                }
            }
        }).start();
    }

}
