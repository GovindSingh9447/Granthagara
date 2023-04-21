package com.ranawattech.collagenotes.filter;

import android.widget.Filter;

import com.ranawattech.collagenotes.Model.ModelCollage;

import java.util.ArrayList;

public class FilterUsersCollage extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelCollage> filterList;

    //adapter in which filter need to implement
    com.ranawattech.collagenotes.Adapter_Users.AdapterCollage adapterCollage;

    //constructor


    public FilterUsersCollage(ArrayList<ModelCollage> filterList, com.ranawattech.collagenotes.Adapter_Users.AdapterCollage adapterCollage) {
        this.filterList = filterList;
        this.adapterCollage = adapterCollage;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results =new FilterResults();
        //value should not be null and empty
        if (constraint!= null && constraint.length()>0){
            //change to uppercase or lowercase to avoid case sensitive
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelCollage> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size();i++){

                //validation
                if (filterList.get(i).getCollage().toUpperCase().contains(constraint)){

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
        adapterCollage.collageArrayList =(ArrayList<ModelCollage>)results.values;

        //  notify changes
        adapterCollage.notifyDataSetChanged();
    }
}
