package com.murach.winr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.aware.WifiAwareNetworkSpecifier;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FragmentOne extends Fragment {
    private WineAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);

        //initiate the database
        WineDBHelper dbHelper = new WineDBHelper(container.getContext());
        mDatabase = dbHelper.getWritableDatabase();

        //parse the xml file
        //parseXML();

        //set the list view
        ListView wineListView = (ListView) rootView.findViewById(R.id.wine_list_view);

        //test output data
        String[] menuItems = {"one", "two", "three"};
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        );

        //set the query
        Cursor wineCursor = mDatabase.rawQuery("SELECT * FROM wine_data", null);

        //set the adapter
        mAdapter = new WineAdapter(container.getContext(), wineCursor);
        //attach to list view
        wineListView.setAdapter(mAdapter);

        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
