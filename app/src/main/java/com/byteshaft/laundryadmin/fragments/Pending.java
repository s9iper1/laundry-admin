package com.byteshaft.laundryadmin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byteshaft.laundryadmin.R;

/**
 * Created by s9iper1 on 2/21/17.
 */

public class Pending extends Fragment {

    private View mBaseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.pending, container, false);
        return mBaseView;
    }
}
