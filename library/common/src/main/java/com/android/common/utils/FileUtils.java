package com.android.common.utils;

import android.content.Context;
import android.os.Environment;

import com.android.common.app.AppConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * 文件管理工具类
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class FileUtils {

    public static String BASE_DIR = Environment.getExternalStorageDirectory()
            + "/" + AppConfig.APP_TAG;// BASE_DIR
    public static String CACHES_DIR;// 其他缓存目录
    public static String TEMP_DIR;// 文件缓存目录
    public static String DOWNLOAD_DIR;// 下载目录
    public static String AUDIO_DIR;// 音频目录
    public static String LOG_DIR;// 报错日志目录
    public static String IMAGE_DIR;// 图片目录

    private FileUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 创建项目各功能文件
     *
     * @param context
     */
    public static void createDir(Context context) {
        CACHES_DIR = BASE_DIR + "/" + context.getPackageName() + "/caches/";
        createDir(CACHES_DIR);

        TEMP_DIR = BASE_DIR + "/" + context.getPackageName() + "/tmp/";
        createDir(TEMP_DIR);

        DOWNLOAD_DIR = BASE_DIR + "/" + context.getPackageName() + "/downloads/";
        createDir(DOWNLOAD_DIR);

        AUDIO_DIR = BASE_DIR + "/" + context.getPackageName() + "/audio/";
        createDir(AUDIO_DIR);

        LOG_DIR = BASE_DIR + "/" + context.getPackageName() + "/log/";
        createDir(LOG_DIR);

        IMAGE_DIR = BASE_DIR + "/" + context.getPackageName() + "/image/";
        createDir(IMAGE_DIR);
    }

    /**
     * 拷贝文件
     *
     * @param s
     * @param t
     */
    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            //得到对应的文件通道
            FileChannel in = fi.getChannel();
            //得到对应的文件通道
            FileChannel out = fo.getChannel();
            //连接两个通道，并且从in通道读取，然后写入out通道
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fo != null) {
                    fo.close();
                }
                if (fi != null) {
                    fi.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 转换文件大小
     *
     * @param fileLen
     * @return
     */
    public static String formatFileSizeToString(long fileLen) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLen < 1024) {
            fileSizeString = df.format((double) fileLen) + "B";
        } else if (fileLen < 1048576) {
            fileSizeString = df.format((double) fileLen / 1024) + "K";
        } else if (fileLen < 1073741824) {
            fileSizeString = df.format((double) fileLen / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLen / 1073741824) + "G";
        }
        return fileSizeString;
    }


    public static void deleteFile(File file) {
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            deleteFileSafely(file);
        }
    }

    /**
     * 安全删除文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }

    /***
     * 获取文件扩展名
     *
     * @param filename
     * @return 返回文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 读取指定文件的输出
     *
     * @param path
     * @return
     */
    public static String getFileOutputString(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path), 8192);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append("\n").append(line);
            }
            bufferedReader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建文件夹
     *
     * @param dir
     */
    private static void createDir(String dir) {
        if (dir == null || "".equals(dir)) {
            return;
        }
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
    }
}
