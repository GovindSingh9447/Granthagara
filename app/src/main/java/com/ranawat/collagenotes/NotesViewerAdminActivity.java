package com.ranawat.collagenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.collagenotes.databinding.ActivityNotesViewerAdminBinding;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotesViewerAdminActivity extends AppCompatActivity {

    private ActivityNotesViewerAdminBinding binding;

    private String title;
    private String url;
    private TextView tit1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesViewerAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = new Intent();

        title = intent.getStringExtra("Title");
        url = intent.getStringExtra("Url");

        binding.title.setText(title);

        tit1 = binding.text1;

        loadNotesView();
    }


    private void loadNotesView() {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("url");
        reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        tit1.setText(value);
                        String url = tit1.getText().toString();

                        new RetriveNotesStream().execute(url);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(NotesViewerAdminActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
        class RetriveNotesStream extends AsyncTask<String, Void, InputStream> {

            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream = null;

                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode() == 200) {
                        inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    }
                } catch (IOException e) {
                    return null;
                }
                return inputStream;
            }

            @Override
            protected void onPostExecute(InputStream inputStream) {
                binding.pdfviewers.fromStream(inputStream).load();
            }
        }
    }
