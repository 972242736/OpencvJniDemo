package com.mmf.opencvjnidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmf.jarunpack.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private ImageView ivGray;
    private TextView tvTest;

    // Used to load the 'opencv_jni' library on application startup.
    static {
        System.loadLibrary("opencv_jni");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tvTest = findViewById(R.id.tv_test);
//        tvTest.setText(Test.testMethod());
//        tvTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvTest.setText(getFromAssets("testFile.txt"));
//            }
//        });
        ivGray = findViewById(R.id.iv_gray);
        ivGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.welcome_test);
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
                int[] pixels = new int[w * h];
                bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
                long startTime = System.currentTimeMillis();
                int[] resultInt = getGray(pixels, w, h);
                long endTime = System.currentTimeMillis();

                Log.e("JNITime", "" + (endTime - startTime));
                Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

                resultImg.setPixels(resultInt, 0, w, 0, 0, w, h);
                ivGray.setImageBitmap(resultImg);
            }
        });
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * A native method that is implemented by the 'opencv_jni' native library,
     * which is packaged with this application.
     */
    public static native int[] getGray(int[] pixels, int w, int h);
}
