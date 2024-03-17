package com.example.smallproject_rge_vta.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smallproject_rge_vta.MainActivity;
import com.example.smallproject_rge_vta.R;

import java.util.Calendar;

public class ReservationFragment extends Fragment {

    private EditText guestsEditText;

    private EditText dateEditText;

    private Calendar calendar;

    private Button bookButton;

    public ReservationFragment() {
        super(R.layout.fragment_reservation);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        calendar = Calendar.getInstance();

        dateEditText = view.findViewById(R.id.date_editText);
        dateEditText.setOnClickListener(dateListener);
        dateEditText.addTextChangedListener(enableBookButton);

        guestsEditText = view.findViewById(R.id.guests_editText);
        guestsEditText.addTextChangedListener(enableBookButton);

        bookButton = view.findViewById(R.id.book_button);
        bookButton.setEnabled(false);
    }

    private final View.OnClickListener dateListener = v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Créer une instance de DatePickerDialog et afficher le dialogue
            DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        selectedMonth += 1;
                        String day = selectedDay < 10 ? "0" + selectedDay : String.valueOf(selectedDay);
                        String monthOfYear = selectedMonth < 10 ? "0" + (selectedMonth) : String.valueOf(selectedMonth);
                        String selectedDate = day + "/" + monthOfYear + "/" + selectedYear;
                        dateEditText.setText(selectedDate);
                    }, year, month, dayOfMonth);

            // Sélection date actuel
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            // Afficher le dialogue de sélection de la date
            datePickerDialog.show();
    };

    private final TextWatcher enableBookButton = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            bookButton.setEnabled(!TextUtils.isEmpty(dateEditText.getText()) && !TextUtils.isEmpty(guestsEditText.getText()));
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
