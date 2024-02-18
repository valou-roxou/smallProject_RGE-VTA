package com.example.smallproject_rge_vta.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smallproject_rge_vta.CustomSnackbar;
import com.example.smallproject_rge_vta.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;

import java.util.Calendar;

public class ReservationFragment extends Fragment {

    private EditText guestsEditText;

    private EditText dateEditText;

    private Calendar calendar;

    private Button bookButton;

    private View view;

    public ReservationFragment() {
        super(R.layout.fragment_reservation);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.view = view;

        calendar = Calendar.getInstance();

        dateEditText = view.findViewById(R.id.date_editText);
        dateEditText.setOnClickListener(dateListener);
        dateEditText.addTextChangedListener(enableBookButton);

        guestsEditText = view.findViewById(R.id.guests_editText);
        guestsEditText.addTextChangedListener(enableBookButton);

        bookButton = view.findViewById(R.id.book_button);
        bookButton.setOnClickListener(bookListener);
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

            // Afficher le dialogue de sélection de la date
            datePickerDialog.show();
    };

    private final View.OnClickListener bookListener = v -> {
        String date = dateEditText.getText().toString();
        String nbGuests = guestsEditText.getText().toString();
        // TODO: requête SQL

        // Pop-up
        // TODO: remplacer le champ nom restaurant
        String textPopUp = getString(R.string.reservation_book_pop_up, "Restautn", date, nbGuests);

        CustomSnackbar.make(view, textPopUp, BaseTransientBottomBar.LENGTH_LONG).show();
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
