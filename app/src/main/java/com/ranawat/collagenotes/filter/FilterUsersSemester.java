package com.ranawat.collagenotes.filter;

import android.widget.Filter;

import com.ranawat.collagenotes.Adapter.AdapterSemester;
import com.ranawat.collagenotes.Model.ModelSemester;

import java.util.ArrayList;

public class FilterUsersSemester extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelSemester> filterList;

    //adapter in which filter need to implement
    com.ranawat.collagenotes.Adapter_Users.AdapterSemester adapterSemester;

    //constructor


    public FilterUsersSemester(ArrayList<ModelSemester> filterList, com.ranawat.collagenotes.Adapter_Users.AdapterSemester adapterSemester) {
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
