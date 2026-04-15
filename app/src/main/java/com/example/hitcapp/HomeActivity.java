package com.example.hitcapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String[] items = {"Item1", "Item2", "Item3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnHome), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });

        ListView listView = findViewById(R.id.myList);

        ArrayList<CustomAdapter.AppItem> list = new ArrayList<>();

        list.add(new CustomAdapter.AppItem("Facebook", "#3b5998"));
        list.add(new CustomAdapter.AppItem("YouTube", "#FF0000"));
        list.add(new CustomAdapter.AppItem("TikTok", "#000000"));
        list.add(new CustomAdapter.AppItem("Zalo", "#0068FF"));
        list.add(new CustomAdapter.AppItem("Instagram", "#E1306C"));
        list.add(new CustomAdapter.AppItem("Messenger", "#0084FF"));
        list.add(new CustomAdapter.AppItem("WhatsApp", "#25D366"));
        list.add(new CustomAdapter.AppItem("Telegram", "#229ED9"));
        list.add(new CustomAdapter.AppItem("Spotify", "#1DB954"));
        list.add(new CustomAdapter.AppItem("Netflix", "#E50914"));
        list.add(new CustomAdapter.AppItem("Shopee", "#EE4D2D"));
        list.add(new CustomAdapter.AppItem("Lazada", "#0F146D"));
        list.add(new CustomAdapter.AppItem("Tiki", "#1A94FF"));
        list.add(new CustomAdapter.AppItem("Google Maps", "#4285F4"));
        list.add(new CustomAdapter.AppItem("Gmail", "#D44638"));
        list.add(new CustomAdapter.AppItem("Chrome", "#F4B400"));
        list.add(new CustomAdapter.AppItem("Photos", "#34A853"));
        list.add(new CustomAdapter.AppItem("Drive", "#0F9D58"));
        list.add(new CustomAdapter.AppItem("CapCut", "#000000"));
        list.add(new CustomAdapter.AppItem("Canva", "#00C4CC"));

        CustomAdapter adapter = new CustomAdapter(this, list);
        listView.setAdapter(adapter);


    }
}