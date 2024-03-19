package com.example.smallproject_rge_vta.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smallproject_rge_vta.R;

public class FeedbackFragment extends Fragment {

    private Button saveButton;

    private EditText comment;

    private SlideshowFragment slideshowFragment;

    public FeedbackFragment() {
        super(R.layout.fragment_feedback);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        comment = view.findViewById(R.id.comment_editText);
        comment.addTextChangedListener(enableSaveFeedback);

        saveButton = view.findViewById(R.id.save_button);

        slideshowFragment = (SlideshowFragment) getChildFragmentManager().findFragmentById(R.id.feedback_slideshow_fragment_container);
    }

    public SlideshowFragment getSlideshowFragment() {
        return this.slideshowFragment;
    }

    private final TextWatcher enableSaveFeedback = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveButton.setEnabled(!TextUtils.isEmpty(comment.getText()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
