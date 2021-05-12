package com.atguigu.java.io;

import org.junit.Test;

import java.io.*;

/**
 * 缓冲流的使用
 * <p>
 * 1. 缓冲流：
 * BufferedInputStream
 * BufferedOutputStream
 * BufferedReader
 * BufferedWriter
 * <p>
 * 2. 作用：提供流的读取，写入的速度
 * 提高读写速度的原因，内部提供了一个缓冲区
 * <p>
 * 3. 处理流，就是“套接”在已有的流基础上
 *
 * @author xiaohua
 * @create 2021-05-10 9:17
 */
public class BufferedStreamTest {

    // 实现非文本文件的复制
    @Test
    public void testBufferedStream() {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // 1. 实例化File对象，指明要操作的文件
            File srcFile = new File("hello.jfif");
            File destFile = new File("hello2.jfif");

            // 2. 指定具体字节流
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            // 指定具体处理流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            // 3. 数据的读入与写出
            byte[] buf = new byte[1024];
            int len;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
                bos.flush(); // 刷新缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭流资源
            // 要求：先关闭外层的流，再关闭内层的流
            // 说明：关闭外层流的同时，内层流也会自动关闭，关于内层流的关闭，我们可以省略
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
           /* if (fos != null) { // 可以省略
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
            }*/
        }
    }

    // 实现文件复制的方法
    public void copyFileWithBuffered(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // 1. 实例化File对象，指明要操作的文件
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);

            // 2. 指定具体字节流
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            // 指定具体处理流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            // 3. 数据的读入与写出
            byte[] buf = new byte[1024];
            int len;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭流资源
            // 要求：先关闭外层的流，再关闭内层的流
            // 说明：关闭外层流的同时，内层流也会自动关闭，关于内层流的关闭，我们可以省略
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testCopyFileWithBuffered() {
        long start = System.currentTimeMillis();
        copyFileWithBuffered("C:\\Users\\xiaohua\\Desktop\\01.avi", "C:\\Users\\xiaohua\\Desktop\\02.avi");
        long end = System.currentTimeMillis();
        System.out.println("复制文件耗时：" + (end - start));
    }

    // 使用BufferedReader与BufferedWriter实现文本文件的复制
    @Test
    public void testBufferedReaderBufferedWriter() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader("a.java"));
            bw = new BufferedWriter(new FileWriter("c.java"));
            // 方式一：使用char[]数组
            /*int len;
            char[] cbuf = new char[100];
            while ((len = br.read(cbuf)) != -1) {
                bw.write(cbuf, 0, len);
                bw.flush();
            }*/

            // 方式二：使用String
            String data;
            while ((data = br.readLine()) != null) {
                // 方法一：
                // bw.write(data + "\n"); // data中不包括换行符
                // 方法二：
                bw.write(data);
                bw.newLine(); // 提供换行的操作
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
