package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HotelManagerActivity extends AppCompatActivity {

    private EditText edtHotelName, edtHotelAddress, edtHotelDescription, edtRoomCount;
    private Button btnSave, btnCancel, btnDelete;
    private HotelDatabaseHelper dbHelper;
    private HotelModel currentHotel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_manager);

        dbHelper = new HotelDatabaseHelper(this);

        edtHotelName = findViewById(R.id.edtHotelName);
        edtHotelAddress = findViewById(R.id.edtHotelAddress);
        edtHotelDescription = findViewById(R.id.edtHotelDescription);
        edtRoomCount = findViewById(R.id.edtRoomCount);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("hotel")) {
            currentHotel = (HotelModel) intent.getSerializableExtra("hotel");
            if (currentHotel != null) {
                edtHotelName.setText(currentHotel.getName());
                edtHotelAddress.setText(currentHotel.getAddress());
                edtHotelDescription.setText(currentHotel.getDescription());
                edtRoomCount.setText(String.valueOf(currentHotel.getRoomCount()));
                btnDelete.setVisibility(View.VISIBLE);
            }
        } else {
            btnDelete.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(v -> saveHotel());
        btnCancel.setOnClickListener(v -> finish());
        btnDelete.setOnClickListener(v -> {
            if (currentHotel != null) {
                dbHelper.deleteHotel(currentHotel.getId());
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void saveHotel() {
        String name = edtHotelName.getText().toString().trim();
        String address = edtHotelAddress.getText().toString().trim();
        String description = edtHotelDescription.getText().toString().trim();
        String roomCountStr = edtRoomCount.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(roomCountStr)) {
            if (TextUtils.isEmpty(name)) edtHotelName.setError("Không được để trống");
            if (TextUtils.isEmpty(address)) edtHotelAddress.setError("Không được để trống");
            if (TextUtils.isEmpty(roomCountStr)) edtRoomCount.setError("Không được để trống");
            return;
        }

        int roomCount;
        try {
            roomCount = Integer.parseInt(roomCountStr);
        } catch (NumberFormatException e) {
            edtRoomCount.setError("Số phòng phải là số");
            return;
        }

        if (currentHotel == null) {
            HotelModel hotel = new HotelModel(name, address, description, roomCount);
            dbHelper.insertHotel(hotel);
        } else {
            currentHotel.setName(name);
            currentHotel.setAddress(address);
            currentHotel.setDescription(description);
            currentHotel.setRoomCount(roomCount);
            dbHelper.updateHotel(currentHotel);
        }
        setResult(Activity.RESULT_OK);
        finish();
    }
}
