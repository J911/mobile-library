package me.j911.term_project.mobile_library.entites;

import me.j911.term_project.mobile_library.interfaces.IRequest;

public class Request implements IRequest {

    private String title;
    private String contents;
    private int like;
    private int stdId;

    public Request(String title, String contents, int stdId, int like) {
        this.title = title;
        this.contents = contents;
        this.stdId = stdId;
        this.like = like;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public int getStdLike() {
        return like;
    }

    @Override
    public int getRequestStdId() {
        return stdId;
    }

}
