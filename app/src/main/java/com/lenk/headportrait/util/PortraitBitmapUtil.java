package com.lenk.headportrait.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.ThumbnailUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.lenk.headportrait.R;

import java.util.ArrayList;
import java.util.List;

public class PortraitBitmapUtil {
    private static final int portraitBitmapWidth = 500;//生成bitmap长度
    private static final int portraitSpacing = 18;//微信默认间距
    private static final int[] backgroundRGBColor = new int[]{225, 225, 225};//生成bitmap背景色


    /**
     * 根据url获取bitmap
     *
     * @param context
     * @param portraitUrlList 所选择的头像url集合
     * @param selfPortraitUrl 自身的头像url
     * @return
     */
    public static List<Bitmap> generateBitmapList(Context context, List<String> portraitUrlList, String selfPortraitUrl) {
        List<Bitmap> mBitmapList = new ArrayList<>();
        for (int i = 0; i < portraitUrlList.size() + 1; i++) {
            String headPicUrl = "";
            if (i == portraitUrlList.size()) {//每次创建 最后一个为自己的头像
                headPicUrl = selfPortraitUrl;
            } else {
                headPicUrl = portraitUrlList.get(i);
            }
            try {
                Bitmap myBitmap = Glide.with(context).load(headPicUrl).asBitmap() //必须
                        .centerCrop().into(
                                PortraitBitmapUtil.getPortraitWidth(portraitUrlList.size() + 1, portraitBitmapWidth),
                                PortraitBitmapUtil.getPortraitWidth(portraitUrlList.size() + 1, portraitBitmapWidth))
                        .get();

                mBitmapList.add(myBitmap);
            } catch (Exception e) {
                mBitmapList.add(ThumbnailUtils.extractThumbnail(getScaleBitmap(
                        context.getResources(), R.mipmap.ic_launcher),
                        PortraitBitmapUtil.getPortraitWidth(portraitUrlList.size() + 1, portraitBitmapWidth),
                        PortraitBitmapUtil.getPortraitWidth(portraitUrlList.size() + 1, portraitBitmapWidth)));
            }
        }
        return mBitmapList;
    }

    /**
     * 生成微信风格群头像
     *
     * @param bitmaps
     * @return
     */
    public static Bitmap getCombineBitmaps(List<Bitmap> bitmaps) {
        Bitmap newBitmap = Bitmap.createBitmap(portraitBitmapWidth, portraitBitmapWidth, Config.ARGB_8888);
        //----------------
        Canvas cv = new Canvas(newBitmap);
        cv.drawRGB(backgroundRGBColor[0], backgroundRGBColor[1], backgroundRGBColor[2]);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        //----------------
        for (int i = 0; i < bitmaps.size(); i++) {
            newBitmap = mixtureBitmap(newBitmap, bitmaps.get(i), i, bitmaps.size());
        }
        return newBitmap;
    }

    //根据数量及position计算坐标
    private static Bitmap mixtureBitmap(Bitmap first, Bitmap second, int position, int count) {
        if (first == null || second == null) {
            return null;
        }
        float pointLeft = 1000;
        float pointTop = 1000;
        Bitmap newBitmap = Bitmap.createBitmap(first.getWidth(),
                first.getHeight(), Config.ARGB_8888);
        switch (count) {
            case 2:
                switch (position) {
                    case 0:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() / 4;
                        break;
                    case 1:
                        pointLeft = first.getWidth() / 2 + portraitSpacing / 2;
                        pointTop = first.getWidth() / 4;
                        break;
                }
                break;
            case 3:
                switch (position) {
                    case 0:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 1:
                        pointLeft = first.getWidth() / 2 + portraitSpacing / 2;
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                    case 2:
                        pointLeft = first.getWidth() / 2 - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) / 2;
                        pointTop = portraitSpacing;
                        break;
                }
                break;
            case 4:
                switch (position) {
                    case 0:
                        pointLeft = portraitSpacing;
                        pointTop = portraitSpacing;
                        break;
                    case 1:
                        pointLeft = first.getWidth() / 2 + portraitSpacing / 2;
                        pointTop = portraitSpacing;
                        break;
                    case 2:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                    case 3:
                        pointLeft = first.getWidth() / 2 + portraitSpacing / 2;
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                }
                break;
            case 5:
                switch (position) {
                    case 0:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                    case 1:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                    case 2:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                    case 3:
                        pointLeft = first.getWidth() / 2 - portraitSpacing / 2 - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = first.getWidth() / 2 - portraitSpacing / 2 - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 4:
                        pointLeft = first.getWidth() / 2 + portraitSpacing / 2;
                        pointTop = first.getWidth() / 2 - portraitSpacing / 2 - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                }
                break;
            case 6:
                switch (position) {
                    case 0:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                    case 1:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                    case 2:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = first.getWidth() / 2 + portraitSpacing / 2;
                        break;
                    case 3:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() / 2 - portraitSpacing / 2 - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 4:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = first.getWidth() / 2 - portraitSpacing / 2 - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 5:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = first.getWidth() / 2 - portraitSpacing / 2 - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                }
            case 7:
                switch (position) {
                    case 0:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 1:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 2:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 3:
                        pointLeft = portraitSpacing;
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 4:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 5:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 6:
                        pointLeft = first.getWidth() / 2 - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) / 2;
                        pointTop = portraitSpacing;
                        break;
                }
                break;
            case 8:
                switch (position) {
                    case 0:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 1:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 2:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 3:
                        pointLeft = portraitSpacing;
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 4:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 5:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 6:
                        pointLeft = (first.getWidth() / 2) - (portraitSpacing / 2) - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = portraitSpacing;
                        break;
                    case 7:
                        pointLeft = first.getWidth() / 2 + portraitSpacing / 2;
                        pointTop = portraitSpacing;
                        break;
                }
                break;
            case 9:
                switch (position) {
                    case 0:
                        pointLeft = portraitSpacing;
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 1:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 2:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = first.getWidth() - portraitSpacing - PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 3:
                        pointLeft = portraitSpacing;
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 4:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 5:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        break;
                    case 6:
                        pointLeft = portraitSpacing;
                        pointTop = portraitSpacing;
                        break;
                    case 7:
                        pointLeft = portraitSpacing * 2 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth);
                        pointTop = portraitSpacing;
                        break;
                    case 8:
                        pointLeft = portraitSpacing * 3 + PortraitBitmapUtil.getPortraitWidth(count, portraitBitmapWidth) * 2;
                        pointTop = portraitSpacing;
                        break;
                }
                break;
        }

        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, 0, 0, null);
        cv.drawBitmap(second, pointLeft, pointTop, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newBitmap;
    }

    //根据数量计算每张小图片的宽度
    private static int getPortraitWidth(int count, int bitmapBackgroundWidth) {
        float width = 0;
        switch (count) {
            case 1:
                width = bitmapBackgroundWidth;
                break;
            case 2:
            case 3:
            case 4:
                width = (bitmapBackgroundWidth - (portraitSpacing * 3)) / 2;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                width = (bitmapBackgroundWidth - (portraitSpacing * 4)) / 3;
                break;
        }
        return (int) width;
    }
    //--------------------------------------------------

    private static Bitmap getScaleBitmap(Resources res, int id) {

        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, id, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 800 * 480);
            // Log.d("BitmapUtil","inSampleSize===>"+opts.inSampleSize);
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeResource(res, id, opts);
        } catch (OutOfMemoryError err) {
            Log.d("BitmapUtil", "[getScaleBitmap] out of memory");

        }
        return bitmap;
    }

    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {

        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;

    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {

        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }

    }
}
