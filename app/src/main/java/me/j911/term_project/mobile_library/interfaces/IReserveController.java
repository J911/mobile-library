package me.j911.term_project.mobile_library.interfaces;

public interface IReserveController {
    public boolean reserveSeat(String seatId);
    public boolean unreserveSeat(String seatId);
    public ISeat getReservedInfoById(String seatId);
    public ISeat[] getAllReservedInfo();
}
