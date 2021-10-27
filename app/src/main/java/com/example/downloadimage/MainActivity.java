package com.example.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivShowImage;
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivShowImage = findViewById(R.id.ivShowImage);
        btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        new ImageDownloader().execute(" https://www.pngall.com/wp-content/uploads/2018/04/School-PNG-Image-HD.png");
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap>{
        HttpURLConnection httpURLConnection;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                Bitmap temp = BitmapFactory.decodeStream(inputStream);
                return temp;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if(bitmap != null){
                ivShowImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Download successful!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Download Error!", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}