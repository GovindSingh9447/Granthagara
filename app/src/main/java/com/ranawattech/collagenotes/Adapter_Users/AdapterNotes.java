package com.ranawattech.collagenotes.Adapter_Users;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.ranawattech.Users_Sections.NotesViewUserActivity;
import com.ranawattech.collagenotes.Model.ModelNotes;
import com.ranawattech.collagenotes.MyApplication;
import com.ranawattech.collagenotes.databinding.RowNotesBinding;
import com.ranawattech.collagenotes.filter.FilterUsersNotes;

import java.util.ArrayList;

import static com.ranawattech.collagenotes.Constants.MAX_BYTES_NOTES;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.HolderNotesAdmin> implements Filterable {



    //context
    private Context context;

    //arrayList to hold list of notes
    public ArrayList<ModelNotes> notesArrayList, filterList;

    //binding
    private RowNotesBinding binding;

    private FilterUsersNotes filterUsersNotes;

    public AdapterNotes(Context context, ArrayList<ModelNotes> notesArrayList) {
        this.context = context;
        this.notesArrayList = notesArrayList;
        this.filterList = notesArrayList;
    }

    @NonNull
    @Override
    public HolderNotesAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=RowNotesBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderNotesAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNotesAdmin holder, int position) {

        //get data

        ModelNotes modelNotes=notesArrayList.get(position);

        String title=modelNotes.getTitle();
        String description=modelNotes.getDescriptions();
        String url=modelNotes.getUrl();
        String subjId=modelNotes.getSubjectId();
        String senderName=modelNotes.getSenderName();
        String urlId=modelNotes.getId();
        long timestamp=modelNotes.getTimestamp();


        //dd/mm/yy
        String formatDate= MyApplication.formatTimestamp(timestamp);

        //set data
        holder.notesName.setText(title);
        holder.notesDetails.setText(description);
        holder.notesDate.setText(formatDate);
        holder.notesType.setText(senderName);



        //load further data
        loadNotesCollage(modelNotes,holder);
        loadNotesFromUrl(modelNotes,holder);
        loadNotesSize(modelNotes,holder);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotesViewUserActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Url", url);
                intent.putExtra("UrlId", urlId);
                intent.putExtra("subjectId",subjId);
                context.startActivity(intent);
            }
        });


    }

    private void loadNotesSize(ModelNotes modelNotes, HolderNotesAdmin holder) {

        //using url we can get file and its metadata from firebase storage
        String notesUrl=modelNotes.getUrl();

        StorageReference reference= FirebaseStorage.getInstance().getReferenceFromUrl(notesUrl);
        reference.getMetadata()
                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {

                        //get size in bytes
                        double bytes =storageMetadata.getSizeBytes();

                        //convert bytes to KB , MB
                        double kb = bytes/1024;
                        double mb = kb/1024;

                        if (mb >= 1){
                            holder.notesSize.setText(String.format("%.2f",mb)+" MB");
                        }
                        else if (kb>= 1){
                            holder.notesSize.setText(String.format("%.2f",kb)+" KB");
                        }
                        else
                        {
                            holder.notesSize.setText(String.format("%.2f",bytes)+" bytes");
                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //failed getting metadata
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        
    }

    private void loadNotesFromUrl(ModelNotes modelNotes, HolderNotesAdmin holder) {

        //using url we can get file and its metadata from firebase storage

        String notesUrl =modelNotes.getUrl();
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

    private void loadNotesCollage(ModelNotes modelNotes, HolderNotesAdmin holder) {

    }

    @Override
    public int getItemCount() {

        //return number of records / list size
        return notesArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filterUsersNotes == null){
            filterUsersNotes = new FilterUsersNotes(filterList,this);
        }
        return filterUsersNotes;
    }

    //view Holder for row_Notes.xml
    class HolderNotesAdmin extends RecyclerView.ViewHolder{

        //ui view of row_Notes.xml
        PDFView pdfView;
        ProgressBar progressBar;
        TextView notesName;
        TextView notesDetails;
        TextView notesType;
        TextView notesSize;
        TextView notesDate;
        ImageButton btnbac;






        public HolderNotesAdmin(@NonNull View itemView) {
            super(itemView);

            pdfView=binding.pdfView;
            progressBar=binding.progressBar;
            notesName=binding.notesName;
            notesDetails=binding.notesDetails;
            notesType=binding.notesType;
            notesSize=binding.notesSize;
            notesDate=binding.notesDate;
            btnbac=binding.moreBtn;

        }
    }
}