package com.ranawattech.Interview.InterviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ranawattech.Interview.InterviewModel.InterVCollModel;
import com.ranawattech.Interview.InterviewNotesViewerActivity;
import com.ranawattech.collagenotes.databinding.ModelHindiupnBinding;
import com.ranawattech.collagenotes.databinding.ModelInterCollBinding;


import java.util.ArrayList;

public class InterVCollAdapter extends RecyclerView.Adapter<InterVCollAdapter.InterVColHolder> {


    private static final long MAX_BYTES_NOTES = 500000000;
    private Context context;
    public ArrayList<InterVCollModel> modelArrayList;


    ModelInterCollBinding binding;

    public InterVCollAdapter(Context context, ArrayList<InterVCollModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public InterVColHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ModelInterCollBinding.inflate(LayoutInflater.from(context), parent, false);
        return new InterVColHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull InterVColHolder holder, int position) {
        InterVCollModel interVCollModel = modelArrayList.get(position);
        String fid = interVCollModel.getF_id();
        String title = interVCollModel.getTitle();
        String pdf = interVCollModel.getPdf();
        String img = interVCollModel.getImg();
        String source = interVCollModel.getSource();

        holder.title.setText(title);
        holder.source.setText(source);
        //Glide.with(context).load(interVCollModel.getImg()).into(holder.img);


        loadNotesFromUrl(interVCollModel,holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InterviewNotesViewerActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("pdf", pdf);
                context.startActivity(intent);
            }
        });

    }

    private void loadNotesFromUrl(InterVCollModel interVCollModel, InterVCollAdapter.InterVColHolder holder) {

        //using url we can get file and its metadata from firebase storage

        String notesUrl =interVCollModel.getPdf();
        StorageReference reference= FirebaseStorage.getInstance().getReferenceFromUrl(notesUrl);
        reference.getBytes(MAX_BYTES_NOTES)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {


                        //set to pdfView
                        holder.pdfView.fromBytes(bytes)
                                .pages(0) //show ony first pages;
                                .spacing(0)
                                .swipeHorizontal(false)
                                .enableSwipe(false)
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {

                                    }
                                })
                                .onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {

                                    }
                                })
                                .load();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class InterVColHolder extends RecyclerView.ViewHolder {

        PDFView pdfView;
        TextView title, source;
        ProgressBar progressBar;

        public InterVColHolder(@NonNull View itemView) {
            super(itemView);
            pdfView = binding.pdfView;
            title = binding.title;
            source = binding.author;
            progressBar = binding.progressBar;
        }
    }
}
