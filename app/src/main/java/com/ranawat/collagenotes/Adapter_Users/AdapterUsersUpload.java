package com.ranawat.collagenotes.Adapter_Users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.ranawat.Users_Sections.NotesViewUserActivity;
import com.ranawat.collagenotes.Model.ModelUserUpload;
import com.ranawat.collagenotes.NotesViewerAdminActivity;
import com.ranawat.collagenotes.databinding.RowUserNotesUploadBinding;

import java.util.ArrayList;

import static com.ranawat.collagenotes.Constants.MAX_BYTES_NOTES;

public class AdapterUsersUpload extends RecyclerView.Adapter<AdapterUsersUpload.HolderUserNotes> {


    private Context context;
    private ArrayList<ModelUserUpload> userUploads;

    private RowUserNotesUploadBinding binding;

    public AdapterUsersUpload(Context context, ArrayList<ModelUserUpload> userUploads) {
        this.context = context;
        this.userUploads = userUploads;
    }

    @NonNull
    @Override
    public HolderUserNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //binding Row_User_upload_notes.xml
        binding=RowUserNotesUploadBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderUserNotes(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUserNotes holder, int position) {

        //get data
        ModelUserUpload modelUserUpload= userUploads.get(position);
        String title=modelUserUpload.getTitle();
        String description =modelUserUpload.getDescriptions();
        String url =modelUserUpload.getUrl();
        String subject=modelUserUpload.getSubject();
        String semester=modelUserUpload.getSemesterTitle();
        String course=modelUserUpload.getCourseName();
        String collage=modelUserUpload.getCollageTitle();

        //set data
        holder.notesName.setText(title);
        holder.notesDetails.setText(description);
        holder.collage.setText("collage "+collage);
        holder.course.setText("course "+course);
        holder.subject.setText("subject "+subject);
        holder.semester.setText("semester "+semester);

        loadNotesFromUrl(modelUserUpload,holder);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotesViewUserActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Url", url);
                context.startActivity(intent);
            }
        });

    }

    private void loadNotesFromUrl(ModelUserUpload modelUserUpload, HolderUserNotes holder) {

        //using url we can get file and its metadata from firebase storage

        String notesUrl =modelUserUpload.getUrl();
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
        return userUploads.size();
    }
    /*-------------------View holder class to hold UI views for row_User_upload_Notes.xml---------------*/

    class HolderUserNotes extends RecyclerView.ViewHolder{

        TextView notesName,notesDetails,collage,course,semester,subject;
        PDFView pdfView;
        ProgressBar progressBar;

        public HolderUserNotes(@NonNull View itemView) {
            super(itemView);


            pdfView=binding.pdfView;
            progressBar=binding.progressBar;
            notesName=binding.notesName;
            notesDetails=binding.notesDetails;
            collage=binding.collageN;
            course=binding.courseN;
            semester=binding.semestertN;
            subject=binding.subjectN;
        }
    }

}
