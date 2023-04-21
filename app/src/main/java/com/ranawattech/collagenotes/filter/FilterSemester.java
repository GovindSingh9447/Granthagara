package com.ranawattech.collagenotes.filter;

import android.widget.Filter;

import com.ranawattech.collagenotes.Adapter.AdapterSemester;
import com.ranawattech.collagenotes.Model.ModelSemester;

import java.util.ArrayList;

public class FilterSemester extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelSemester> filterList;

    //adapter in which filter need to implement
    AdapterSemester adapterSemester;

    //constructor


    public FilterSemester(ArrayList<ModelSemester> filterList, AdapterSemester adapterSemester) {
        this.filterList = filterList;
        this.adapterSemester = adapterSemester;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results =new FilterResults();
        //value should not be null and empty
        if (constraint!= null && constraint.length()>0){
            //change to uppercase or lowercase to avoid case sensitive
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelSemester> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size();i++){

                //validation
                if (filterList.get(i).getSemester().toUpperCase().contains(constraint)){

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

        adapterSemester.semesterArrayList = (ArrayList<ModelSemester>) results.values;

        //  notify changes
        adapterSemester.notifyDataSetChanged();
    }
}
