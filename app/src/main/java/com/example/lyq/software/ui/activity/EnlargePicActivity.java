package com.example.lyq.software.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.lyq.software.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class EnlargePicActivity extends AppCompatActivity {

    PhotoViewAttacher attacher;
    private ArrayList<String> imgPaths;
    private int position;
    private ImageView ivPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_pic);
        intitView();
    }

    private void intitView() {
        ivPic = (ImageView) findViewById(R.id.iv_pic);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imgPaths = bundle.getStringArrayList("imgPaths");
            position=bundle.getInt("position");
        }
        attacher = new PhotoViewAttacher(ivPic);
        Glide.with(this).load(imgPaths.get(position)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ivPic.setImageBitmap(resource);
                attacher.update();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                ivPic.setImageDrawable(errorDrawable);
            }
        });

        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
//                hideOrShowToolBar();
            }

            @Override
            public void onOutsidePhotoTap() {
//                hideOrShowToolBar();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_pic_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_delete:
//                setResult(RESULT_OK);
//                finish();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        attacher.cleanup();
//        ButterKnife.unbind(this);
    }
}
