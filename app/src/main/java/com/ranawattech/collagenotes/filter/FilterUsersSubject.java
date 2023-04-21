package com.ranawattech.collagenotes.filter;

import android.widget.Filter;

import com.ranawattech.collagenotes.Adapter_Users.AdapterSubject;
import com.ranawattech.collagenotes.Model.ModelSubject;

import java.util.ArrayList;

public class FilterUsersSubject extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelSubject> filterList;

    //adapter in which filter need to implement
    AdapterSubject adapterSubject;

    //constructor
    public FilterUsersSubject(ArrayList<ModelSubject> filterList, AdapterSubject adapterSubject) {
        this.filterList = filterList;
        this.adapterSubject = adapterSubject;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results =new FilterResults();
        //value should not be null and empty
        if (constraint!= null && constraint.length()>0){
            //change to uppercase or lowercase to avoid case sensitive
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelSubject> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size();i++){

                //validation
                if (filterList.get(i).getSubject().toUpperCase().contains(constraint)){

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
        adapterSubject.subjectArrayList =(ArrayList<ModelSubject>)results.values;


        //  notify changes
        adapterSubject.notifyDataSetChanged();
    }
}
