package me.j911.term_project.mobile_library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import me.j911.term_project.mobile_library.controller.AccountController;
import me.j911.term_project.mobile_library.controller.ReserveController;
import me.j911.term_project.mobile_library.entites.Seat;

public class Reserve extends AppCompatActivity {

    private AccountController accountController;
    private ReserveController reserveController;
    private Seat[] seats;

    private ArrayList<String> items;
    private ArrayAdapter adapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservescreen);

        accountController = AccountController.getInstance(getApplicationContext());
        reserveController = ReserveController.getInstance(getApplicationContext());

        loggedInCheck();
        items = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter) ;
        initializeSeat();

    }

    private void loggedInCheck() {
        if (!accountController.isLoggedIn()) {
            Toast.makeText(getApplicationContext(), R.string.login_require, Toast.LENGTH_SHORT).show();
        }
    }

    public void reserveAction(View view) {
        switch (view.getId()) {
            case R.id.reserveDoit:
                reserve();
                break;
            case R.id.reserveCancle:
                reserveCancle();
                break;
        }
    }

    private void initializeSeat() {
        seats = reserveController.getAllReservedInfo();
        for (int i = 0; i < seats.length; i++) {
            if (seats[i].isReserved()) items.add("["+getString(R.string.reserved_seat) +"]: " + seats[i].getSeatId());
            else items.add("["+getString(R.string.reserved_available)+"]: "+ seats[i].getSeatId());
            adapter.notifyDataSetChanged();
        }
    }

    private void reserveCancle() {
        int pos = listview.getCheckedItemPosition();
        String seatId = seats[pos].getSeatId();
        if (pos == -1) {
            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
            return;
        }
        Seat selectedSeat = reserveController.getReservedInfoById(seatId);
        if (!selectedSeat.isReserved()) {
            Toast.makeText(getApplicationContext(), R.string.empty_seat_msg, Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedSeat.getReservedStdId() != accountController.getAccountId()) {
            Toast.makeText(getApplicationContext(), R.string.no_permission_msg, Toast.LENGTH_SHORT).show();
            return;
        }
        reserveController.unreserveSeat(seatId);
        Toast.makeText(getApplicationContext(), R.string.cancel_reservation_msg, Toast.LENGTH_SHORT).show();
        adapter.clear();
        initializeSeat();
    }

    private void reserve() {
        int pos = listview.getCheckedItemPosition();
        String seatId = seats[pos].getSeatId();
        if (pos == -1) {
            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
            return;
        }
        Seat selectedSeat = reserveController.getReservedInfoById(seatId);
        if (selectedSeat.isReserved()) {
            Toast.makeText(getApplicationContext(), R.string.reserved_msg, Toast.LENGTH_SHORT).show();
            return;
        }
        reserveController.reserveSeat(seatId, accountController.getAccountId());
        Toast.makeText(getApplicationContext(), R.string.reserve_msg, Toast.LENGTH_SHORT).show();
        adapter.clear();
        initializeSeat();
    }
}
