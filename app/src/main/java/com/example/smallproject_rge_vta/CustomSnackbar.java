package com.example.smallproject_rge_vta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;

public class CustomSnackbar extends BaseTransientBottomBar<CustomSnackbar> {

    protected CustomSnackbar(ViewGroup parent, View content, com.google.android.material.snackbar.ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    public static CustomSnackbar make(View view, CharSequence text, int duration) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_snackbar_book_success, null);

        TextView textView = customView.findViewById(R.id.pop_up_text);
        textView.setText(text);

        CustomSnackbar customSnackbar = new CustomSnackbar((ViewGroup) view, customView, contentViewCallback);

        customSnackbar.getView().setPadding(0, 0, 0, 0);
        customSnackbar.setDuration(duration);

        return customSnackbar;
    }

    private static final com.google.android.material.snackbar.ContentViewCallback  contentViewCallback = new com.google.android.material.snackbar.ContentViewCallback() {
        @Override
        public void animateContentIn(int delay, int duration) {}

        @Override
        public void animateContentOut(int delay, int duration) {}
    };
}
