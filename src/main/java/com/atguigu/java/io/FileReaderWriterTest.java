package com.atguigu.java.io;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <pre>
 * 一、流的分类
 * 1. 操作数据单位：字节流，字符流
 * 2. 数据的流向：输入流，输出流
 * 3. 流的角色：节点流，处理流
 *
 * 二、流的体系结构
 * 抽象基类            节点流（或文件流）            缓冲流（处理流的一种）
 * InputStream        FileInputStream           BufferedInputStream
 * OutputStream       FileOutputStream          BufferedOutputStream
 * Reader             FileReader                BufferedReader
 * Writer             FileWriter                BufferedWriter
 * </pre>
 *
 * @author xiaohua
 * @create 2021-05-09 12:41
 */
public class FileReaderWriterTest {

    /**
     * 将hello.txt文件内容读入程序中，并输出至控制台
     * <p>
     * 说明点：
     * 1. read()的理解：返回读入的一个字符串，如果达到文件末尾，返回-1
     * 2. 异常的处理：为了保证流资源一定可以执行关闭操作，需要使用try-catch-finally处理
     * 3. 读入的文件一定要存在，否则就会报FileNotFoundException
     */
    @Test
    public void TestFileReader() {
        FileReader reader = null;
        try {
            // 1. 实例化File对象，指明要操作的文件
            File file = new File("hello.txt");
            // 2. 指定具体的流
            reader = new FileReader(file);
            // 3. 数据的读入
            // read()：返回读入的一个字符串，如果达到文件末尾，返回-1

            // 方式一：
            /*int data = reader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = reader.read();
            }*/

            // 方式二：
            int data;
            while ((data = reader.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭流资源
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从内存中写出数据硬盘文件里
     * <p>
     * 说明：
     * 1. 输出操作，对应的File可以不存在，并不会报异常
     * 2. File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建文件
     * File对应的硬盘中的文件如果存在：
     * 如果流使用的构造器是FileWriter(file, false)/FileWriter(file)，对原有文件的覆盖
     * 如果流使用的构造器是FileWriter(file, true)不会对原有文件的覆盖，而是在原有文件基础
     * 上追加内容
     */
    @Test
    public void TestFileWriter() throws Exception {
        FileWriter writer = null;
        try {
            // 1. 实例化File对象，指明写出到的文件
            File file = new File("world.txt");
            // 2. 提供FileWriter对象，用于数据写出
            writer = new FileWriter(file, true);
            // 3. 写出操作
            writer.write("I have a dream!\n");
            writer.write("you need to have a dream!\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                try {
                    // 4. 关闭流资源
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Test
    public void testFileReaderAndFileWriter() {

        FileReader reader = null;
        FileWriter writer = null;

        try {
            // 1. 创建File对象，指明读入和写出的文件
            /*File srcFile = new File("hello.txt");
            File destFile = new File("world.txt");*/

            // 不能使用字符流处理图片等字节数据
            File srcFile = new File("hello.jfif");
            File destFile = new File("world.jfif");

            // 2. 创建输入流和输出流对象
            reader = new FileReader(srcFile);
            writer = new FileWriter(destFile);

            // 3. 数据读入写出操作
            char[] buffer = new char[5];
            int len; // 记录每次读入到buffer数组中的字符的个数

            while ((len = reader.read(buffer)) != -1) {
                // 每次写出len个字符
                writer.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭流资源
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
