package com.sgp.fruitinspector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;



public class CaptureImage extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    String curimgpath=null;
    private static final int img_req=1;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        auth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //initializing firebase authentication object

        if (id == R.id.profile) {
            // Handle the camera action
            startActivity(new Intent(this, Profile.class));
        } else if (id == R.id.display) {
            startActivity(new Intent(this, summary.class));

        }
        else if (id == R.id.contact) {
            //starting login activity
            startActivity(new Intent(this, ContactUs.class));

        }else if (id == R.id.help) {
            //starting login activity
            startActivity(new Intent(this, help.class));

        }else if (id == R.id.navLogout) {
            auth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void captureimg(View view) {
        Intent intent=new Intent(this, ClassifierActivity.class);
        startActivity(intent);
        /*Intent cameraInt=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
        }*/
    }

    public void displayimg(View view) {
        Intent intent=new Intent(this, summary.class);
       // intent.putExtra("image_path",curimgpath);
        startActivity(intent);
    }
    private File getimgfile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timestamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgname="img_"+timestamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imgfile=File.createTempFile(imgname,".jpg",storageDir);
        curimgpath=imgfile.getAbsolutePath();
        return imgfile;
    }
}