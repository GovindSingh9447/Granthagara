package com.ranawattech.collagenotes.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.ranawattech.Users_Sections.NotesViewUserActivity;
import com.ranawattech.collagenotes.Model.ModelNotes;
import com.ranawattech.collagenotes.MyApplication;
import com.ranawattech.collagenotes.databinding.RowNotesBinding;

import java.util.ArrayList;
import static com.ranawattech.collagenotes.Constants.MAX_BYTES_NOTES;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.HolderNotesAdmin> {



    //context
    private Context context;

    //arrayList to hold list of notes
    private ArrayList<ModelNotes> notesArrayList;

    //binding
    private RowNotesBinding binding;

    //progessDialog
    private ProgressDialog progressDialog;

    private static final String Tag="NOTES_REVIEW_TAG";

    public AdapterNotes(Context context, ArrayList<ModelNotes> notesArrayList) {
        this.context = context;
        this.notesArrayList = notesArrayList;

        //init progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
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
        String senderName=modelNotes.getSenderName();
        String url=modelNotes.getUrl();
        long timestamp=modelNotes.getTimestamp();


        //dd/mm/yy
        String formatDate= MyApplication.formatTimestamp(timestamp);

        //set data
        holder.notesName.setText(title);
        holder.notesDetails.setText(description);
        holder.notesDate.setText(formatDate);
        holder.notesType.setText(senderName);


//        //load further data
//        loadNotesCollage(modelNotes,holder);
//        loadNotesFromUrl(modelNotes,holder);
//        loadNotesSize(modelNotes,holder);



        //handel click with 2 options 1) EDIT 2) DELE TE
//        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                moreOptionDialogs(modelNotes, holder);
//
//
//            }
//        });


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

//    private void moreOptionDialogs(ModelNotes modelNotes, HolderNotesAdmin holder) {
//
//        //option to show in dialog
//        String[] options ={"Edit", "Delete"};
//
//        //alert dialog
//        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        builder.setTitle("Choose Options")
//                .setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        //handel dialog option click
//
//                        if(i==0){
//                            //edit  Click
//                        }
//                        else if(i==1){
//                            //Delete Click
//                            DeleteNotes(modelNotes,holder);
//                        }
//                    }
//                })
//                .show();
//    }

    private void DeleteNotes(ModelNotes modelNotes, HolderNotesAdmin holder) {

        String notesId=modelNotes.getId();
        String notesUrl=modelNotes.getUrl();
        String notesTitle =modelNotes.getTitle();

        progressDialog.setMessage("Deleting "+notesTitle+ ".....");
        progressDialog.show();

        //deleting from storage
        StorageReference storageReference=FirebaseStorage.getInstance().getReferenceFromUrl(notesUrl);
        storageReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notes");
                        reference.child(notesId)
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Book is Succesfully Delete", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        progressDialog.dismiss();
                                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
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
        ImageButton moreBtn;






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