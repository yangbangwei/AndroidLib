package com.android.common.utils;

import android.os.Environment;
import android.text.format.DateFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.Date;

/**
 * 异常捕获
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class CrashUtils implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler exceptionHandler;

    public CrashUtils() {
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        writeToFile(
                writer.toString() + "\n" + thread.getName() + "\n"
                        + thread.toString());
        exceptionHandler.uncaughtException(thread, ex);
    }

    private void writeToFile(String stacktrace) {
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_UNMOUNTED)) {
                return;
            }
            String name=TimeUtils.formatDateTime(System.currentTimeMillis()
                    , TimeUtils.DF_YYYY_MM_DD_HH_MM_SS) + ".txt";

            File picFileDir = new File(FileUtils.LOG_DIR);
            if(!picFileDir.exists()){
                //如果路径不存在就先创建路径
                picFileDir.mkdir();
            }
            //然后再创建路径和文件的File对象
            File targetFile = new File(picFileDir,name);
            FileWriter fileWriter = new FileWriter(targetFile);
            BufferedWriter bos = new BufferedWriter(fileWriter);
            bos.write(android.os.Build.VERSION.SDK_INT + "\n");
            bos.write(android.os.Build.MODEL + "\n");
            bos.write("time :"
                    + DateFormat.format("yyyy-MM-dd kk:mm:ss",
                    new Date(System.currentTimeMillis())) + "\n");
            bos.write(stacktrace);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
