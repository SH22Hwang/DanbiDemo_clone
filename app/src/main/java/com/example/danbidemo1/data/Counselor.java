package com.example.danbidemo1.data;

/** 상담사 리사이클러뷰 및 파이어베이스를 이용하기 위한 Getter and Setter */
public class Counselor {
    private String profile; // 프로필(사진)
    private String expert; // 전문분야
    private String counselorName; // 상담사 이름
    private long rating; // 점수, 별점
    private String email; // 이메일
    private String contact; // 전화번호

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
