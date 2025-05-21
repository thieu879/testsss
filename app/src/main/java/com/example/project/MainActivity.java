package com.example.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HotelAdapter.OnHotelActionListener {

    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private HotelDatabaseHelper dbHelper;
    private FloatingActionButton btnAddHotel;
    private List<HotelModel> hotelList;

    private static final int REQUEST_ADD_HOTEL = 1;
    private static final int REQUEST_EDIT_HOTEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HotelDatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hotelList = dbHelper.getAllHotels();
        hotelAdapter = new HotelAdapter(this, hotelList, this);
        recyclerView.setAdapter(hotelAdapter);

        btnAddHotel = findViewById(R.id.btnAddHotel);
        btnAddHotel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HotelManagerActivity.class);
            startActivityForResult(intent, REQUEST_ADD_HOTEL);
        });
    }

    private void refreshHotelList() {
        hotelList = dbHelper.getAllHotels();
        hotelAdapter.setHotelList(hotelList);
    }

    @Override
    public void onViewDetails(HotelModel hotel) {
        new AlertDialog.Builder(this)
                .setTitle(hotel.getName())
                .setMessage(
                        "Địa chỉ: " + hotel.getAddress() + "\n\n" +
                                "Số phòng: " + hotel.getRoomCount() + "\n\n" +
                                "Mô tả: " + hotel.getDescription()
                )
                .setPositiveButton("Đóng", null)
                .show();
    }

    @Override
    public void onEditHotel(HotelModel hotel) {
        Intent intent = new Intent(MainActivity.this, HotelManagerActivity.class);
        intent.putExtra("hotel", hotel);
        startActivityForResult(intent, REQUEST_EDIT_HOTEL);
    }

    @Override
    public void onDeleteHotel(HotelModel hotel) {
        showDeleteConfirmationDialog(hotel);
    }

    private void showDeleteConfirmationDialog(HotelModel hotel) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_confirmation, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        Button btnCancel = dialogView.findViewById(R.id.btnCancelDelete);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirmDelete);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnConfirm.setOnClickListener(v -> {
            dbHelper.deleteHotel(hotel.getId());
            refreshHotelList();
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            refreshHotelList();
        }
    }
}
