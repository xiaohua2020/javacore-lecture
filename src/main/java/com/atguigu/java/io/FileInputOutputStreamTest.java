package com.atguigu.java.io;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试FileInputStream与OutputStream使用
 * <pre>
 * 结论：
 * 1. 对于文本文件（.txt, .java, .c, .cpp...），使用字符流处理
 * 2. 对于非文本文件（.JPG, .mp4, .doc, .ppt...）使用字节流处理
 * </pre>
 *
 * @author xiaohua
 * @create 2021-05-09 15:40
 */
public class FileInputOutputStreamTest {

    // 使用字节流FileInputStream处理文本文件，可能出现乱码
    @Test
    public void testFileInputStream() {
        FileInputStream fis = null;
        try {
            // 1. 创建File对象，指明读入的文件
            File file = new File("hello.txt");
            // 2. 创建输入流对象
            fis = new FileInputStream(file);
            // 3. 数据读入操作
            byte[] buffer = new byte[5];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                String string = new String(buffer, 0, len);
                System.out.println(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                // 4. 关闭流资源
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 实现对图片的复制操作
    @Test
    public void testFileInputOutputStream() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            // 1. 创建File对象，指明读入的文件
            File srcFile = new File("hello.jfif");
            File destFile = new File("world.jfif");

            // 2. 创建输入输出流对象
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            // 3. 数据读入操作
            byte[] buffer = new byte[100];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭流资源
            if (fis != null) {
                try {
                    fis.close();
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
        }
    }

    /**
     * 实现文件复制
     *
     * @param srcPath
     * @param destPath
     */
    public void copyFile(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            // 1. 创建File对象，指明读入的文件
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);

            // 2. 创建输入输出流对象
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            // 3. 数据读入操作
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭流资源
            if (fis != null) {
                try {
                    fis.close();
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
        }
    }

    @Test
    public void testCopyFile() {
        long start = System.currentTimeMillis();
        copyFile("C:\\Users\\xiaohua\\Desktop\\01.avi", "C:\\Users\\xiaohua\\Desktop\\03.avi");
        long end = System.currentTimeMillis();
        System.out.println("复制文件花费时间为：" + (end - start));
    }
}
