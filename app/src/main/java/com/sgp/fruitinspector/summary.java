package com.sgp.fruitinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class summary extends AppCompatActivity {
    TextView calorie,carbs,sugar,fat,fibre,protein;
    Button sharee;
    FirebaseAuth auth;
    private static final String FIRE_LOG = "Fire_log";
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseUser user;
    String userID;
    String message="No Data available for today";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        calorie=findViewById(R.id.cals);
        carbs=findViewById(R.id.carbss);
        sugar=findViewById(R.id.sugars);
        fat=findViewById(R.id.fats);
        fibre=findViewById(R.id.fibers);
        protein=findViewById(R.id.proteins);
        sharee=findViewById(R.id.share_button);
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
 implements NavigationView.OnNavigationItemSelectedListener

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        sharee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
                txtIntent .setType("text/plain");
                txtIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Nutrient Details");
                txtIntent.putExtra(android.content.Intent.EXTRA_TEXT, "*Today's Nutrient Intake*\n\n"+message);
                startActivity(Intent.createChooser(txtIntent ,"Share"));
            }
        });


        auth = FirebaseAuth.getInstance();


        firebaseFirestore=FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user= auth.getCurrentUser();
        userID= Objects.requireNonNull(auth.getCurrentUser()).getUid();


        firebaseFirestore.collection("users").document(userID).collection("summary").document("details").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    if(documentSnapshot.getString("calories")==null || !(documentSnapshot.getString("date").equals(date))){
                        calorie.setText("0 kcal");
                        carbs.setText("0 g");
                        sugar.setText("0 g");
                        fat.setText("0 g");
                        fibre.setText("0 g");
                        protein.setText("0 g");

                    }
                    else{
                        message="";

                        calorie.setText(documentSnapshot.getString("calories")+" kcal");
                    carbs.setText(documentSnapshot.getString("carbohydrates")+" g");
                    sugar.setText(documentSnapshot.getString("sugar")+" g");
                    fat.setText(documentSnapshot.getString("fat")+" g");
                    fibre.setText(documentSnapshot.getString("fiber")+" g");
                    protein.setText(documentSnapshot.getString("protein")+" g");
                    message=message+"Calories: "+documentSnapshot.getString("calories")+" kcal\n";
                        message=message+"Carbohydrates: "+documentSnapshot.getString("carbohydrates")+" g\n";
                        message=message+"Sugar: "+documentSnapshot.getString("sugar")+" g\n";
                        message=message+"Fat: "+documentSnapshot.getString("fat")+" g\n";
                        message=message+"Fiber: "+documentSnapshot.getString("fiber")+" g\n";
                        message=message+"Protein: "+documentSnapshot.getString("protein")+" g\n";
                    }
                }
                else {
                    Log.d(FIRE_LOG,"Errorrrrrrrrrrrrrrrrrrrrrrrr : "+ Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }
    /*public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //initializing firebase authentication object

        if (id == R.id.profile) {
            // Handle the camera action
            startActivity(new Intent(this, Profile.class));
        } else if (id == R.id.display) {
            startActivity(new Intent(this, DisplayImg.class));

        }
        else if (id == R.id.contact) {
            //starting login activity
            startActivity(new Intent(this, ContactUs.class));

        }else if (id == R.id.help) {
            //starting login activity
            startActivity(new Intent(this, help.class));

        }else if (id == R.id.navLogout) {
           // auth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}