package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;



public class ApiUtils {

    static String url = System.getProperty("sut.url");

    static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(url)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static int getRequestStatusCode(Card card, String path) {
        int statusCode =
                given()
                        .spec(requestSpec)
                        .body(card)
                        .when()
                        .post(path)
                        .then()
                        .log().body()
                        .extract()
                        .statusCode();
        return statusCode;
    }
    public static String getHeaderName (Card card, String path) {
        String status = given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post(path)
                .then()
                .extract().jsonPath().getString("status");
        return status;
    }
}

