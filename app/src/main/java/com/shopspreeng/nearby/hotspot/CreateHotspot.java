package com.shopspreeng.nearby.hotspot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shopspreeng.nearby.R;

public class CreateHotspot extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotspot_create, container, false);

        //TODO before creating hotspot, search all other hotspots and check if name already exists
        //TODO, check the id of the device hosting the hotspot

        return rootView;
    }
}
