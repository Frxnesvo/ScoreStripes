package com.example.springbootapp.utils;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("This class is  an utility class and cannot be instantiated");
    }

    public static final String S3_BUCKET_NAME = "scorestripespics";

    public static final Long ACCESS_TOKEN_EXPIRATION_MILLISECONDS = 900000L; // 15 minutes
    public static final Integer PRESIGNED_URL_EXPIRATION_MINUTES = 60;   //TODO DA METTERE 5

}
