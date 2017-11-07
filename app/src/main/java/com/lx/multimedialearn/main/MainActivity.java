package com.lx.multimedialearn.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lx.multimedialearn.R;
import com.lx.multimedialearn.drawbmp.DrawBmpActivity;
import com.lx.multimedialearn.openglstudy.OpenGLIndexActivity;
import com.lx.multimedialearn.player.VideoPlayerActivity;
import com.lx.multimedialearn.usecamera.CameraUseActivity;
import com.lx.multimedialearn.utils.PermissionUtils;
import com.lx.multimedialearn.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;

/**
 * 多媒体学习
 * 1. drawBmp-在Android平台绘制一张图片，使用多种种不同的API，ImageView，SurfaceView，自定义View，GLSurfaceView等
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecContent;
    List<TabModel> list = new ArrayList<>();

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.checkPermission(this);
        mRecContent = (RecyclerView) findViewById(R.id.rec_main_content);
        mRecContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.add(new TabModel("1. 画Bitmap-drawbmp", "在Android平台绘制一张图片，使用多种不同的API，ImageView，SurfaceView，自定义View，GlSurfaceView，TextureView等", DrawBmpActivity.class));
        list.add(new TabModel("2. 使用Camera-usecamera", "分别使用SurfaceView、TextureView、GLSurfaceView来预览Camera+数据，取到NV21的数据回调,双GLSurfaceView等，并总结Android图形图像架构", CameraUseActivity.class));
        list.add(new TabModel("3. 播放音视频-player", "分别使用SurfaceView、TextureView、GLSurfaceView播放mp4文件", VideoPlayerActivity.class));
        list.add(new TabModel("4. OpenGl ES的学习-openglstudy", "OpenGL入门，开发空气曲棍球游戏，动态桌面等功能", OpenGLIndexActivity.class));
        mRecContent.setAdapter(new MainAdapter(list));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int temp : grantResults) {
            if (temp == PERMISSION_DENIED) {
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("申请权限").setMessage("这些权限很重要").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtils.show(MainActivity.this, "取消");
                    }
                }).setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
                        MainActivity.this.startActivity(intent);
                    }
                }).create();
                dialog.show();
                break;
            }
        }
    }

    public native String stringFromJNI();
}
