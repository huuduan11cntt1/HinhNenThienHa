package jos.tran.hinhnenthienha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.IOException;

public class SetWallPaper extends AppCompatActivity {
    private ImageView imageView;
    private Button apply;
    DisplayMetrics displayMetrics;
    int with, height;
    Bitmap bitmap, bitmap2;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wall_paper);

        imageView = findViewById(R.id.fullImage);
        apply = findViewById(R.id.apply);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-5382625544778444/1388168861");


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Glide.with(this).load(getIntent().getStringExtra("images")).into(imageView);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackGround();
            }
        });
    }
    private void setBackGround() {
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        with = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bitmap2 = Bitmap.createScaledBitmap(bitmap,with,height,true);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        wallpaperManager.setWallpaperOffsetSteps(1,1);
        wallpaperManager.suggestDesiredDimensions(with,height);

        try {
           wallpaperManager.setBitmap(bitmap2);
            Toast.makeText(this,"Set wallpaper ok !" ,Toast.LENGTH_LONG).show();
           /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N )  {
                manager.setBitmap(bitmap2,null,true,WallpaperManager.FLAG_SYSTEM);

            }*/

        } catch (IOException e) {
            Toast.makeText(this,"Erro"+e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}