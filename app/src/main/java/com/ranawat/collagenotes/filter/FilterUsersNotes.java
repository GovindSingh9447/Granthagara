package com.ranawat.collagenotes.filter;

import android.widget.Filter;

import com.ranawat.collagenotes.Adapter_Users.AdapterNotes;
import com.ranawat.collagenotes.Model.ModelNotes;

import java.util.ArrayList;

public class FilterUsersNotes extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelNotes> filterList;

    //adapter in which filter need to implement
    AdapterNotes adapterNotes;

    //constructor


    public FilterUsersNotes(ArrayList<ModelNotes> filterList, AdapterNotes adapterNotes) {
        this.filterList = filterList;
        this.adapterNotes = adapterNotes;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results =new FilterResults();
        //value should not be null and empty
        if (constraint!= null && constraint.length()>0){
            //change to uppercase or lowercase to avoid case sensitive
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelNotes> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size();i++){

                //validation
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)){

                    //add to filtered list
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count =filteredModels.size();
            results.values=filteredModels;

        }
        else{
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {


        //apply filter change
        adapterNotes.notesArrayList =(ArrayList<ModelNotes>)results.values;

        //  notify changes
        adapterNotes.notifyDataSetChanged();
    }
}
