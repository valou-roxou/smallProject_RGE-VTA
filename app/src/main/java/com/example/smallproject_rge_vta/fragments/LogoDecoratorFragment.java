package com.example.smallproject_rge_vta.fragments;

import android.net.vcn.VcnUnderlyingNetworkTemplate;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.smallproject_rge_vta.R;

public class LogoDecoratorFragment extends Fragment {
    private View topView;
    private View downView;

    public LogoDecoratorFragment(View topView, View downView) {
        this.topView = topView;
        this.downView = downView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logo_decorator, container, false);
    }
}
