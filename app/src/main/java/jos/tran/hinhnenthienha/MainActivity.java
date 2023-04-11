package jos.tran.hinhnenthienha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ArrayList<String> arrayList;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rcv);

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


        reference = FirebaseDatabase.getInstance().getReference().child("images");
        
        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //progressBar.setVisibility(View.VISIBLE);
                arrayList = new ArrayList<>();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    String data = snapshot1.getValue().toString();
                    arrayList.add(data);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                adapter = new UserAdapter(arrayList,MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}