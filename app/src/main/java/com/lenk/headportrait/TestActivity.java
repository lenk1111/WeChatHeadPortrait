package com.lenk.headportrait;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.lenk.headportrait.util.PortraitBitmapUtil;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mImageView = (ImageView) findViewById(R.id.imageView);

        createPortrait();
    }

    public void createPortrait() {
        final List<String> portraitUrlList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            portraitUrlList.add("http://www.iconpng.com/download/png/100002");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Bitmap> mBitmapList = PortraitBitmapUtil.generateBitmapList(TestActivity.this, portraitUrlList, "http://www.iconpng.com/download/png/100002");
                final Bitmap combineBitmap = PortraitBitmapUtil.getCombineBitmaps(mBitmapList);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(combineBitmap);
                    }
                });

            }
        }).start();
    }
}
