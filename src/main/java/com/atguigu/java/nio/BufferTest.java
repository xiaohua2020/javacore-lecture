package com.atguigu.java.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 一、缓冲区（Buffer）：在Java NIO中负责数据的存取，缓冲区就是数组，用于存储不同数据类型的数据
 * <p>
 * 根据数据类型不同（boolean除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * <p>
 * 上述缓冲区的管理方式几乎一致，通过allocate()获取缓冲区
 * <p>
 * 二、缓冲区存取数据的两个核心方法
 * <p>
 * put()：存入数据到缓冲区中
 * get()：获取缓冲区 中的数据
 * <p>
 * 三、缓冲区中的四个核心属性：
 * <p>
 * mark <= position <= limit <= capacity
 * <p>
 * private int mark = -1; // 标记：表示记录当前position的位置，可以通过reset()恢复至mark的位置
 * private int position = 0; // 位置：表示缓冲区中正在操作数据的位置
 * private int limit; // 界限：表示缓冲区中可以操作数据的大小（limit后面的数据不能进行读写）
 * private int capacity; // 容量：表示缓冲区中最大存储数据的容量，一旦声明不能改变
 * <p>
 * 四、直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过allocate()方法分配缓冲区，将缓冲区建立在JVM内存中
 * 直接缓冲区：通过allocateDirect()方法分配直接缓冲区，将缓冲区建立在物理内存中，可以提高效率
 *
 * @author xiaohua
 * @create 2021-05-10 20:58
 */
public class BufferTest {

    @Test
    public void test2() {
        // 分配直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer.isDirect());
    }

    @Test
    public void test() {

        java.lang.String str = "hello";

        // 1. 分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println("-----------------------allocate()-----------------------");
        System.out.println("position：" + buffer.position());
        System.out.println("limit：" + buffer.limit());
        System.out.println("capacity：" + buffer.capacity());

        // 2. 利用put()方法存入数据至缓冲区
        buffer.put(str.getBytes());

        System.out.println("-----------------------put()-----------------------");
        System.out.println("position：" + buffer.position());
        System.out.println("limit：" + buffer.limit());
        System.out.println("capacity：" + buffer.capacity());

        // 3. 切换至读取数据模式
        buffer.flip();

        System.out.println("-----------------------flip()-----------------------");
        System.out.println("position：" + buffer.position());
        System.out.println("limit：" + buffer.limit());
        System.out.println("capacity：" + buffer.capacity());

        // 4. 利用get()方法读取缓冲区数据
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println(new String(dst, 0, dst.length));

        System.out.println("-----------------------get()-----------------------");
        System.out.println("position：" + buffer.position());
        System.out.println("limit：" + buffer.limit());
        System.out.println("capacity：" + buffer.capacity());

        // 5. rewind()：可重复读数据
        buffer.rewind();

        System.out.println("-----------------------rewind()-----------------------");
        System.out.println("position：" + buffer.position());
        System.out.println("limit：" + buffer.limit());
        System.out.println("capacity：" + buffer.capacity());

        // 6. clear()：清空缓冲区，但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buffer.clear();

        System.out.println("-----------------------clear()-----------------------");
        System.out.println("position：" + buffer.position());
        System.out.println("limit：" + buffer.limit());
        System.out.println("capacity：" + buffer.capacity());

        System.out.println((char) buffer.get());

        // 6. mark()：表示记录当前position的位置，可以通过reset()恢复至mark的位置
        buffer.mark();

        System.out.println("-----------------------mark()-----------------------");
        System.out.println("position：" + buffer.position());
        System.out.println("limit：" + buffer.limit());
        System.out.println("capacity：" + buffer.capacity());

        // 判断缓冲区是否还有剩余数据
        System.out.println("-----------------------hasRemaining()-----------------------");
        System.out.println(buffer.hasRemaining());

        // 获取缓冲区中可以操作的数量
        System.out.println("-----------------------remaining()-----------------------");
        System.out.println(buffer.remaining());
    }
}
