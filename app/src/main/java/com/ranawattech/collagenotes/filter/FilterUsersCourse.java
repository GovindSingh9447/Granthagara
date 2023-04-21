package com.ranawattech.collagenotes.filter;

import android.widget.Filter;

import com.ranawattech.collagenotes.Model.ModelCourse;

import java.util.ArrayList;

public class FilterUsersCourse extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelCourse> filterList;

    //adapter in which filter need to implement
    com.ranawattech.collagenotes.Adapter_Users.AdapterCourse adapterCourse;

    //constructor


    public FilterUsersCourse(ArrayList<ModelCourse> filterList, com.ranawattech.collagenotes.Adapter_Users.AdapterCourse adapterCourse) {
        this.filterList = filterList;
        this.adapterCourse = adapterCourse;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results =new FilterResults();
        //value should not be null and empty
        if (constraint!= null && constraint.length()>0){
            //change to uppercase or lowercase to avoid case sensitive
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelCourse> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size();i++){

                //validation
                if (filterList.get(i).getCourse().toUpperCase().contains(constraint)){

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
        adapterCourse.courseArrayList = (ArrayList<ModelCourse>) results.values;

        //  notify changes
        adapterCourse.notifyDataSetChanged();
    }
}
