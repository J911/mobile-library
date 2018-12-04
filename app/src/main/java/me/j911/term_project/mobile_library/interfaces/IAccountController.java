package me.j911.term_project.mobile_library.interfaces;

public interface IAccountController {
    public int signin(int stdId, String stdPassword);
    public int signup(String stdName, int stdId, String stdPassword);
    public int signout();
    public boolean isLoggedIn();
}