package com.ranawat.collagenotes.filter;

import android.widget.Filter;

import com.ranawat.collagenotes.Adapter.AdapterCollage;
import com.ranawat.collagenotes.Model.ModelCollage;

import java.util.ArrayList;

public class FilterCollage extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelCollage> filterList;

    //adapter in which filter need to implement
    AdapterCollage adapterCollage;

    //constructor


    public FilterCollage(ArrayList<ModelCollage> filterList, AdapterCollage adapterCollage) {
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
