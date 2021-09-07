package com.ranawat.Users_Sections;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ranawat.collagenotes.databinding.ActivityNotesViewUserBinding;

import java.net.URLEncoder;

public class NotesViewUserActivity extends AppCompatActivity {
    private ActivityNotesViewUserBinding binding;

    private WebView notesView;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotesViewUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesView=binding.viewNotess;
        notesView.getSettings().setJavaScriptEnabled(true);

        String notesName=getIntent().getStringExtra("Title");
        String url=getIntent().getStringExtra("Url");
        String urlId=getIntent().getStringExtra("UrlId");
        String subject=getIntent().getStringExtra("subjectId");

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.titleTv.setText(notesName);


        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle(notesName);

        progressDialog.setMessage("Opening.....");


        notesView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();

            }
        });

        String urll="";
        try {
            urll= URLEncoder.encode(url,"UTF-8");
        }
        catch (Exception ex)
        {

        }
        notesView.loadUrl("http://docs.google.com/gview?embedded=true&url=" +urll);

    }

}