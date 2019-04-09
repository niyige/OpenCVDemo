package com.oyy.opencvdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView img;

    private Bitmap bitmap;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.txt);
        tv.setText(stringFromJNI());

        img = findViewById(R.id.img);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.oyy);

        img.setImageBitmap(bitmap);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();

                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

                int[] result = bitmapToGrey(pixels, width, height);

                Bitmap newBit = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                newBit.setPixels(result, 0, width, 0, 0, width, height);
                img.setImageBitmap(newBit);
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native int[] bitmapToGrey(int[] pixels, int width, int height);
}
