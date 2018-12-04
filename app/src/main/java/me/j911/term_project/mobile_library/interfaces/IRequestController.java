package me.j911.term_project.mobile_library.interfaces;

public interface IRequestController {
    public boolean addRequest(String seatId, String title, String contents);
    public boolean isLiked(int requestId);
    public boolean like(int requestId);
    public boolean unlike(int requestId);
    public IRequest getRequestById(int requestId);
    public IRequest[] getAllRequest();
}