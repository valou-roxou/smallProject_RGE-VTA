package com.example.smallproject_rge_vta.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

        guestsEditText = view.findViewById(R.id.guests_editText);

        bookButton = view.findViewById(R.id.book_button);
        bookButton.setOnClickListener(bookListener);
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

    private final View.OnClickListener bookListener = v -> {
        // dateEditText.getText();
        // guestsEditText.getText();
        // TODO: requête SQL
    };
}
