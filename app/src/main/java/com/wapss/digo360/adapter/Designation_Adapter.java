package com.wapss.digo360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wapss.digo360.R;

import java.util.List;

public class Designation_Adapter extends ArrayAdapter<String> {
    private List<String> items;
    public Designation_Adapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        items = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);

    }
    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner, parent, false);
        }
        TextView spinner = convertView.findViewById(R.id.spinner);

        // Customize the appearance based on your data
        spinner.setText(items.get(position));

        return convertView;
    }

}
