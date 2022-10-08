package com.ranawat.School.SchoolAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ranawat.Interview.InterviewModel.InterVCollModel;
import com.ranawat.Interview.InterviewNotesViewerActivity;
import com.ranawat.School.NotesViewActivity;
import com.ranawat.School.SchoolModel.NotesModel;
import com.ranawat.collagenotes.databinding.ModelNotesBinding;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    private static final long MAX_BYTES_NOTES = 500000000;
    private Context context;
    public ArrayList<NotesModel> modelArrayList;

    public NotesAdapter(Context context, ArrayList<NotesModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }
    ModelNotesBinding binding;

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       binding=ModelNotesBinding.inflate(LayoutInflater.from(context),parent,false);
        return new NotesHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {

        NotesModel notesModel=modelArrayList.get(position);
        String subject_id=notesModel.getSubject_id();
        String title=notesModel.getTitle();
        String sourceName=notesModel.getSourceName();
        String id=notesModel.getId();
        String pdf=notesModel.getPdf();

        holder.title.setText(title);
        holder.source.setText(sourceName);

        loadNotesFromUrl(notesModel,holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotesViewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("pdf", pdf);
                context.startActivity(intent);
            }
        });
    }

    private void loadNotesFromUrl(NotesModel notesModel, NotesHolder holder) {

        //using url we can get file and its metadata from firebase storage

        String notesUrl =notesModel.getPdf();
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

    public class NotesHolder extends RecyclerView.ViewHolder{

        PDFView pdfView;
        TextView title, source;

        public NotesHolder(@NonNull View itemView) {
            super(itemView);
            pdfView = binding.pdfView;
            title = binding.title;
            source = binding.author;
        }
    }
}
