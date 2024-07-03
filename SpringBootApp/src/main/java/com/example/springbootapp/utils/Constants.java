package com.example.springbootapp.utils;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("This class is  an utility class and cannot be instantiated");
    }

    public static final String S3_BUCKET_NAME = "scorestripespics";
    public static final Long ACCESS_TOKEN_EXPIRATION_MILLISECONDS = 900000L; // 15 minutes
    public static final Integer PRESIGNED_URL_EXPIRATION_MINUTES = 10;
    public static final String CLUB_FOLDER = "clubs";
    public static final String USER_FOLDER = "users";
    public static final String LEAGUE_FOLDER = "leagues";
    public static final String PRODUCT_FOLDER = "products";

}
