package com.ranawattech.School;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranawattech.collagenotes.databinding.ActivityNotesViewBinding;

import java.net.URLEncoder;

public class NotesViewActivity extends AppCompatActivity {
    ActivityNotesViewBinding binding;
    String title, pdf, url;
    private WebView notesView;
    private ProgressDialog progressDialog;

    TextView bookName;
    ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotesViewBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        bookName= binding.bookName;
        backBtn=binding.backBtn;
        notesView=binding.pdfviewers;
        notesView.getSettings().setJavaScriptEnabled(true);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("pdf");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBackPressed();

                /*Intent intent = new Intent(InterviewNotesViewerActivity.this, InterviewCollectionActivity.class);
                startActivity(intent);
                finish();*/
            }
        });
        bookName.setText(title);
        bookName.setSelected(true);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle(title);

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