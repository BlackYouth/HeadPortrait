package headportrait.lc.com.headportrait.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import headportrait.lc.com.headportrait.R;
import headportrait.lc.com.headportrait.view.WrhImageView;


public class PhotoTailorActivity extends ActionBarActivity implements View.OnClickListener{

    private WrhImageView wrhImageView;
    private ImageView imageView;
    private Button btnClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_tailor_layout);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        initview();

        Glide.with(PhotoTailorActivity.this).load(uri).into(wrhImageView);
    }

    private void initview() {
        wrhImageView = (WrhImageView)findViewById(R.id.img);
        imageView = (ImageView)findViewById(R.id.img2);
        btnClip = (Button)findViewById(R.id.btn_clip);
        btnClip.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        wrhImageView.setVisibility(View.INVISIBLE);
        Bitmap bitmap = wrhImageView.clipBitmap();
        imageView.setImageBitmap(bitmap);

    }

}
