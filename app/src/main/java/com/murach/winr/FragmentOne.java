package com.murach.winr;

import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.aware.WifiAwareNetworkSpecifier;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentOne extends Fragment {
    private WineAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WineDBHelper db = new WineDBHelper(getActivity());

        //setup the recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.red_wine_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set the adapter
        mAdapter = new WineAdapter(this, getSomeItems());
        recyclerView.setAdapter(mAdapter);
    }
}
