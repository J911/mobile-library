package me.j911.term_project.mobile_library.entites;

import me.j911.term_project.mobile_library.interfaces.ISeat;

public class Seat implements ISeat {

    private boolean isReserved;
    private String seatId;
    private int accountId;

    public Seat(String seatId, int accountId, boolean isReserved) {
        this.seatId = seatId;
        this.accountId = accountId;
        this.isReserved = isReserved;
    }

    @Override
    public int getReservedStdId() {
        return accountId;
    }

    @Override
    public String getSeatId() {
        return seatId;
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }
}
