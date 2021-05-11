package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ITEMS = API_PATH + "/items";

    public static final String PATIENTS = API_PATH + "/patients";
    public static final String USERS = API_PATH + "/users";
    public static final String CONSULTATIONS = API_PATH + "/consultations";
    public static final String CONSULTATIONS_DOCTORS = API_PATH + "/doctorsConsult";

    public static final String ENTITY = "/{id}";
    public static final String FILTERED = "/filtered";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String REVIEWS = "/reviews";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

}
