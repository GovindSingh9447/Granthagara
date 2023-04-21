package com.ranawattech.entrance.EntranceAdapter;

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
import com.ranawattech.Interview.InterviewNotesViewerActivity;
import com.ranawattech.collagenotes.databinding.ModelEntranceBinding;
import com.ranawattech.collagenotes.databinding.ModelEntranceCollBinding;
import com.ranawattech.entrance.EntranceModel.EntranceVModel;

import java.util.ArrayList;

public class EntranceVAdapter extends RecyclerView.Adapter<EntranceVAdapter.EntranceVHolder> {



    private static final long MAX_BYTES_NOTES = 500000000;
    private Context context;
    public ArrayList<EntranceVModel> entranceVModels;


    ModelEntranceCollBinding binding;

    @NonNull
    @Override
    public EntranceVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ModelEntranceCollBinding.inflate(LayoutInflater.from(context), parent, false);
        return new EntranceVHolder(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull EntranceVHolder holder, int position) {

        EntranceVModel entranceVModel = entranceVModels.get(position);
        String fid = entranceVModel.getF_id();
        String title = entranceVModel.getTitle();
        String pdf = entranceVModel.getPdf();
        String img = entranceVModel.getImg();
        String source = entranceVModel.getSource();

        holder.title.setText(title);
        holder.source.setText(source);
        //Glide.with(context).load(interVCollModel.getImg()).into(holder.img);


        loadNotesFromUrl(entranceVModel,holder);

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

    private void loadNotesFromUrl(EntranceVModel entranceVModel, EntranceVHolder holder) {

        //using url we can get file and its metadata from firebase storage

        String notesUrl =entranceVModel.getPdf();
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
        return entranceVModels.size();
    }

    public class EntranceVHolder extends RecyclerView.ViewHolder{

        PDFView pdfView;
        TextView title, source;
        ProgressBar progressBar;


        public EntranceVHolder(@NonNull View itemView) {
            super(itemView);

            pdfView = binding.pdfView;
            title = binding.title;
            source = binding.author;
            progressBar = binding.progressBar;
        }
    }


}



