package com.sgp.fruitinspector;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
/*import android.app.ActionBar;
import android.view.MenuItem;*/
import android.content.Intent;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureImage extends AppCompatActivity {
    String curimgpath=null;
    private static final int img_req=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.appbar_layout);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);
    }
    public void captureimg(View view) {
        Intent cameraInt=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraInt.resolveActivity(getPackageManager())!=null){
            File imgfile=null;
            try{
                imgfile=getimgfile();

            }
            catch (IOException e){e.printStackTrace();}
            if (imgfile!=null){
                Uri imguri= FileProvider.getUriForFile(this,"com.sgp.android.fileprovider",imgfile);
                cameraInt.putExtra(MediaStore.EXTRA_OUTPUT,imguri);
                startActivityForResult(cameraInt,img_req);

            }

        }
    }

    public void displayimg(View view) {
        Intent intent=new Intent(this, DisplayImg.class);
        intent.putExtra("image_path",curimgpath);
        startActivity(intent);
    }

    private File getimgfile() throws IOException {
        String timestamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgname="img_"+timestamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imgfile=File.createTempFile(imgname,".jpg",storageDir);
        curimgpath=imgfile.getAbsolutePath();
        return imgfile;

    }
     /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}