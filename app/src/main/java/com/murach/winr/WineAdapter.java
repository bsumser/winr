package com.murach.winr;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class WineAdapter extends CursorAdapter {
    public WineAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.wine_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView outputTextView = (TextView) view.findViewById(R.id.wine_list_view_text_view);
        // Extract properties from cursor
        String color = cursor.getString(cursor.getColumnIndexOrThrow("Color"));
        String year = cursor.getString(cursor.getColumnIndexOrThrow("Year"));
        String varietal = cursor.getString(cursor.getColumnIndexOrThrow("Varietal"));
        String region = cursor.getString(cursor.getColumnIndexOrThrow("Region"));
        String output = color + " " + year + " " + varietal + " " + region;
        // Populate fields with extracted properties
        outputTextView.setText(output);
    }
}
