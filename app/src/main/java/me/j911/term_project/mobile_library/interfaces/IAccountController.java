package me.j911.term_project.mobile_library.interfaces;

public interface IAccountController {
    public int signIn(int stdId, String stdPassword);
    public int signUp(String stdName, int stdId, String stdPassword);
    public int signOut();
    public boolean isLoggedIn();
}