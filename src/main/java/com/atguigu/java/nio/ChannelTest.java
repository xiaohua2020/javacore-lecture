package com.atguigu.java.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 一、通道（Channel）：用于源节点与目标节点的连接，在Java nio中负责缓冲区中数据的传输，
 * Channel本身不存储数据，因此需要配合缓冲区进行传输。
 * <p>
 * 二、通道的主要实现类
 * <p>
 * java.nio.channels.Channel接口：
 * |-FileChannel
 * |-SocketChannel
 * |-ServerSocketChannel
 * |-DatagramChannel
 * <p>
 * 三、获取通道
 * 1. Java针对支持通道的类提供了getChannel()方法
 * 本地IO:
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * <p>
 * <p>
 * 网络IO:
 * Socket
 * ServerSocket
 * DatagramSocket
 * <p>
 * 2. 在JDK1.7中的NIO.2针对各个通道提供了静态方法open()
 * 3. 在JDK1.7中的NIO.2的Files工具类的new ByteChannel()
 * <p>
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * <p>
 * 五、分散（Scatter）与聚集（Gather）：
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writers）：将多个缓冲区中的数据聚集到通道中
 * <p>
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组 -> 字符串
 *
 * @author xiaohua
 * @create 2021-05-11 10:56
 */
public class ChannelTest {

    // 编码解码
    @Test
    public void test6() throws IOException {
        Charset cs = Charset.forName("GBK");
        // 获取编码器
        CharsetEncoder ce = cs.newEncoder();
        // 获取解码器
        CharsetDecoder cd = cs.newDecoder();

        CharBuffer cbuf = CharBuffer.allocate(1024);
        cbuf.put("尚硅谷威武！");
        cbuf.flip();
        // 编码
        ByteBuffer bBuf = ce.encode(cbuf);
        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }
        bBuf.flip();
        // 解码
        CharBuffer cbuf2 = cd.decode(bBuf);
        System.out.println(cbuf2.toString());

        System.out.println("------------------------");

        Charset cs2 = Charset.forName("GBK");
        bBuf.flip();
        CharBuffer cb2 = cs2.decode(bBuf);
        System.out.println(cb2.toString());
    }

    // 字符集
    @Test
    public void test5() {
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    // 分散与聚集
    @Test
    public void test4() throws Exception {

        RandomAccessFile raf1 = new RandomAccessFile("a.java", "rw");
        // 获取通道
        FileChannel channel = raf1.getChannel();
        // 分配指定大小缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        // 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel.read(bufs);

        for (ByteBuffer buffer : bufs) {
            // 切换至读模式
            buffer.flip();
        }
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("---------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        // 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("b.java", "rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);
    }


    // 通道之间的数据传输（直接缓冲区）
    @Test
    public void test3() throws IOException { // 379
        long start = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("1.mp4"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.mp4"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        // inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }


    // 利用直接缓冲区完成文件的复制（直接缓冲区，内存映射文件）
    @Test
    public void test2() throws IOException { // 664 630 626

        long start = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("1.mp4"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.mp4"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 内存映射文件
        MappedByteBuffer inMappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuffer.limit()];
        inMappedBuffer.get(dst);
        outMappedBuffer.put(dst);

        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }

    // 利用通道完成文件的复制（非直接缓冲区）
    @Test
    public void test() { // 5780 5795 5990
        long start = System.currentTimeMillis();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            fis = new FileInputStream("1.mp4");
            fos = new FileOutputStream("2.mp4");
            // 1. 获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            // 2. 分配指定大小的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 3. 将通道中的数据存入缓冲区
            while (inChannel.read(buffer) != -1) {
                // 切换至读模式
                buffer.flip();
                outChannel.write(buffer);
                // 清空缓冲区
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 将缓冲区中的数据写入通道中
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }
}
