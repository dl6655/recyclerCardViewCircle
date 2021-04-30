package com.hirayclay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

public class ImageUtils {

// Bitmap mSourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.source);


    public static Bitmap createReflectedImage(Context context, int imgId) {
        Bitmap originalImage = BitmapFactory.decodeResource(context.getResources(), imgId);
        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0X70ffffff, 0X00ffffff,
                Shader.TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /**
     * @param imgId 图片资源id
     * @return Bitmap 带倒影的Bitmap
     */
    public static Bitmap createReflectedBitmap(Context context, int imgId){
        Bitmap originalImage = BitmapFactory.decodeResource(context.getResources(), imgId);
        // 反射图片和原始图片中间的间距
        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        //通过矩阵对图像进行变换
        Matrix matrix = new Matrix();
        // 第一个参数为1，表示x方向上以原比例为准保持不变，正数表示方向不变。
        // 第二个参数为-1，表示y方向上以原比例为准保持不变，负数表示方向取反。
        matrix.preScale(1, -1); // 实现图片的反转

        // 创建反转后的图片Bitmap对象，图片高是原图的一半
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);

        // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);

        Paint deafaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
        // 将反转后的图片画到画布中
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        // 创建线性渐变LinearGradient 对象。
        LinearGradient shader = new LinearGradient(0, originalImage
                .getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70000000, 0x00000000, Shader.TileMode.MIRROR);

        paint.setShader(shader);
        // 倒影遮罩效果
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }


    private Bitmap revertBitmap(Bitmap mSourceBitmap,Bitmap mRevertBitmap) {
        //1.倒立图
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);   //以X轴向下翻转
        int width = mSourceBitmap.getWidth();
        int height = mSourceBitmap.getHeight();

        //生成倒立图，宽度和原图一致，高度为原图的一半
        mRevertBitmap = Bitmap.createBitmap(mSourceBitmap, 0, height / 2, width, height / 2, matrix, false);

        //2.要生成原图加上倒立图，先生成一个可变空的Bitmap, 高度为原图高度的1.5倍（包括原图和倒立图的高度）
        int gap = 10; //间隙空白
        Bitmap bitmap = Bitmap.createBitmap(width, height + height / 2, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(mSourceBitmap, 0, 0, paint);  //绘制原图
        canvas.drawBitmap(mRevertBitmap, 0, height + gap, paint);  //绘制倒立图

        //3.画笔使用LinearGradient 线性渐变渲染
        LinearGradient lg = new LinearGradient(0, height + gap, width, bitmap.getHeight(), 0xabff0000, 0x00ffff00, Shader.TileMode.MIRROR);
        paint.setShader(lg);

        //4.指定画笔的Xfermode 即绘制的模式（不同的模式，绘制的区域不同）
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        //5.在倒立图区，绘制矩形渲染图层
        canvas.drawRect(0, height + gap, width, bitmap.getHeight(), paint);
        paint.setXfermode(null);
        return bitmap;
    }

    //缩放图片
    private Bitmap resizeImage(Bitmap bitmap, int width, int height) {
        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        float scaleWidth = width / originWidth;
        float scaleHeight = height / originHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, originWidth, originHeight, matrix, true);
        return resizeBitmap;
    }



}
