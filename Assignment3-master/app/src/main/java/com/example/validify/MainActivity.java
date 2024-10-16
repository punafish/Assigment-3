package com.example.validify;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, phoneInput, pinInput;
    private Spinner positionSpinner;
    private TextView errorText, resultText;
    private LinearLayout inputLayout, outputLayout;

    private static final String NAME_REGEX = "^[a-z A-Z._]+$";
    private static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
    private static final String PHONE_REGEX = "^01[3-9][0-9]{8}$";
    private static final String PIN_REGEX = "^[0-9]{3}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        pinInput = findViewById(R.id.pinInput);
        positionSpinner = findViewById(R.id.positionSpinner);
        Button submitBtn = findViewById(R.id.submitBtn);
        errorText = findViewById(R.id.errorText);
        resultText = findViewById(R.id.resultText);
        inputLayout = findViewById(R.id.inputLayout);
        outputLayout = findViewById(R.id.outputLayout);

        String[] positions = {"Select a Position", "Developer", "Designer", "Manager", "Tester"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, positions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(adapter);

        submitBtn.setOnClickListener(v -> validateForm());
    }

    private void validateForm() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String pin = pinInput.getText().toString().trim();
        String selectedPosition = positionSpinner.getSelectedItem().toString();

        errorText.setVisibility(View.GONE);
        outputLayout.setVisibility(View.GONE);

        if (name.isEmpty()) {
            showError("Name is required.");
            nameInput.requestFocus();
        } else if (!Pattern.matches(NAME_REGEX, name)) {
            showError("Invalid name format. Only letters and spaces are allowed.");
            nameInput.requestFocus();
        } else if (email.isEmpty()) {
            showError("Email is required.");
            emailInput.requestFocus();
        } else if (!Pattern.matches(EMAIL_REGEX, email)) {
            showError("Invalid email format.");
            emailInput.requestFocus();
        } else if (phone.isEmpty()) {
            showError("Phone number is required.");
            phoneInput.requestFocus();
        } else if (!Pattern.matches(PHONE_REGEX, phone)) {
            showError("Invalid phone number. It must be 11 digits starting with '01'.");
            phoneInput.requestFocus();
        } else if (pin.isEmpty()) {
            showError("Pin number is required.");
            pinInput.requestFocus();
        } else if (!Pattern.matches(PIN_REGEX, pin)) {
            showError("Invalid pin. It must be exactly 3 digits.");
            pinInput.requestFocus();
        } else if (selectedPosition.equals("Select a Position")) {
            showError("You must select a position.");
            positionSpinner.requestFocus();
        } else {
            inputLayout.setVisibility(View.GONE);
            outputLayout.setVisibility(View.VISIBLE);

            errorText.setVisibility(View.GONE);
            String result = "Name: " + name + "\nEmail: " + email + "\nPhone: " + phone + "\nPin: " + pin + "\nPosition: " + selectedPosition;
            resultText.setText(result);
        }
    }

    private void showError(String message) {
        errorText.setText(message);
        errorText.setVisibility(View.VISIBLE);
        outputLayout.setVisibility(View.GONE);
    }
}
