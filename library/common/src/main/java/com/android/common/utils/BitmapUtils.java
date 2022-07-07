package com.android.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.view.View;
import android.view.View.MeasureSpec;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class BitmapUtils {

    /**
     * 截图
     *
     * @param activity
     * @return
     */
    public static Bitmap takeScreenShotQQ(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        if (b1 != null && !b1.isRecycled()) {
            b1.recycle();
        }
        view.destroyDrawingCache();
        Bitmap bm = scaleBitmap2DefinedSize(b);
        if (b != null && !b.isRecycled()) {
            b.recycle();
        }
        return bm;
    }

    /**
     * 将图片转换到默认的尺寸
     *
     * @param bitmap
     * @return
     */
    private static Bitmap scaleBitmap2DefinedSize(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        int minLen = getMinlen(bitmap.getWidth(), bitmap.getHeight());
        float fScale = (float) SysEnvUtils.SCREEN_WIDTH / (float) minLen;
        matrix.postScale(fScale, fScale);
        final Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return scaledBitmap;
    }

    /**
     * 获取最小尺寸
     *
     * @param width
     * @param height
     * @return
     * @author: bangwei.yang
     */
    private static int getMinlen(int width, int height) {
        if (width < height) {
            return width;
        } else {
            return height;
        }
    }

    /**
     * 截取应用程序界面（去除状态栏）
     *
     * @param activity 界面Activity
     * @return Bitmap对象
     */
    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, statusBarHeight,
                SysEnvUtils.SCREEN_WIDTH, SysEnvUtils.SCREEN_HEIGHT - statusBarHeight);
        view.destroyDrawingCache();
        return bitmap2;
    }

    /**
     * 截取应用程序界面
     *
     * @param activity 界面Activity
     * @return Bitmap对象
     */
    public static Bitmap takeFullScreenShot(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);

        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();

        View view = activity.getWindow().getDecorView();
        Bitmap bmp2 = Bitmap.createBitmap(480, 800, Config.ARGB_8888);
        // view.draw(new Canvas(bmp2));
        // bmp就是截取的图片了，可通过bmp.compress(CompressFormat.PNG, 100, new
        // FileOutputStream(file));把图片保存为文件。

        // 1、得到状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        System.out.println("状态栏高度：" + statusBarHeight);

        // 2、得到标题栏高度
        int wintop = activity.getWindow().findViewById(android.R.id.content)
                .getTop();
        int titleBarHeight = wintop - statusBarHeight;
        System.out.println("标题栏高度:" + titleBarHeight);

        // //把两个bitmap合到一起
        // Bitmap bmpall = Biatmap.createBitmap(width,height,Config.ARGB_8888);
        // Canvas canvas=new Canvas(bmpall);
        // canvas.drawBitmap(bmp1,x,y,paint);
        // canvas.drawBitmap(bmp2,x,y,paint);

        return bmp;
    }

    /**
     * 截取View内容，返回bitmap
     *
     * @param mView 需要截屏的目标View
     * @return
     */
    public static Bitmap takeViewScreenShot(View mView) {
        Bitmap result = null;
        try {
            mView.setDrawingCacheEnabled(true);
            result = mView.getDrawingCache();
            mView.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 截取指定View内容，保存到指定的文件目录
     *
     * @param mView    截屏目标View
     * @param filePath jpg文件路径+文件名
     * @return
     */
    public static File takeViewScreenShot(View mView, String filePath) {
        return takeViewScreenShot(mView, filePath, 80);
    }

    /**
     * 截取指定View内容，保存到指定的文件目录
     *
     * @param mView    截屏目标View
     * @param filePath jpg文件路径+文件名
     * @param quality  0-100压缩率
     * @return
     */
    public static File takeViewScreenShot(View mView, String filePath,
                                          int quality) {
        File myCaptureFile = new File(filePath);
        mView.setDrawingCacheEnabled(true);
        try {

            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(myCaptureFile));
            mView.getDrawingCache().compress(Bitmap.CompressFormat.JPEG,
                    quality, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mView.setDrawingCacheEnabled(false);
        return myCaptureFile;
    }

    /**
     * 获取View内容，转成bitmap
     *
     * @param v 目标View
     * @return
     */
    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return screenshot;
    }

    /**
     * 将View转成Bitmap
     *
     * @param view 目标View
     * @return
     */
    public static Bitmap gainViewBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree 旋转的角度
     * @throws IOException
     */
    public static int gainPictureDegree(String path) throws Exception {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            throw (e);
        }

        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  角度
     * @param bitmap 源bitmap
     * @return Bitmap 旋转角度之后的bitmap
     */
    public static Bitmap rotaingBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        // 重新构建Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * Drawable转成Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                                    : Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 从资源文件中获取图片
     *
     * @param context    上下文
     * @param drawableId 资源文件id
     * @return
     */
    public static Bitmap gainBitmap(Context context, int drawableId) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                drawableId);
        return bmp;
    }

    /**
     * 灰白图片（去色）
     *
     * @param bitmap 需要灰度的图片
     * @return 去色之后的图片
     */
    public static Bitmap toBlack(Bitmap bitmap) {
        Bitmap resultBMP = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.RGB_565);
        Canvas c = new Canvas(resultBMP);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bitmap, 0, 0, paint);
        return resultBMP;
    }

    /**
     * 将bitmap转成 byte数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] toBtyeArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将byte数组转成 bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap bytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 将Bitmap转换成指定大小
     *
     * @param bitmap 需要改变大小的图片
     * @param width  宽
     * @param height 高
     * @return
     */
    public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * 将图片转成指定弧度（角度）的图片
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        // 根据图片创建画布
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 缩放图片
     *
     * @param bmp  需要缩放的图片源
     * @param newW 需要缩放成的图片宽度
     * @param newH 需要缩放成的图片高度
     * @return 缩放后的图片
     */
    public static Bitmap zoom(Bitmap bmp, int newW, int newH) {

        // 获得图片的宽高
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        // 计算缩放比例
        float scaleWidth = ((float) newW) / width;
        float scaleHeight = ((float) newH) / height;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,
                true);

        return newbm;
    }

    /**
     * 获得倒影的图片
     *
     * @param bitmap 原始图片
     * @return 带倒影的图片
     */
    public static Bitmap makeReflectionImage(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Paint deafalutPaint = new Paint();
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
}
