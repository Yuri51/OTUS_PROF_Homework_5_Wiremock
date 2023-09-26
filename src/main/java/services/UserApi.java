package services;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

public class UserApi {

  public static final String WIREMOCK_URI = System.getProperty("baseUrl", "http://localhost:20");
  private static final String GET_SCORE_USER = "/user/get/2";
  private static final String GET_ALL_USERS = "/user/get/all";
  private static final String GET_COURSES = "/course/get/all";

  private RequestSpecification requestSpecification;
  private ResponseSpecification responseSpecification;

  public UserApi() {
    requestSpecification = given()
        .baseUri(WIREMOCK_URI)
        .contentType(ContentType.JSON)
        .log().all();

    responseSpecification = RestAssured.expect()
        .statusCode(200)
        .statusLine("HTTP/1.1 200 OK")
        .contentType(ContentType.JSON)
        .time(Matchers.lessThan(10000L))
        .log().all();
  }

  public Response getRequest(RequestSpecification spec, String path) {
    return given(spec)
        .log().all()
        .when()
        .get(path)
        .then()
        .log().all()
        .extract()
        .response();
  }

  public Response getUser() {
    return getRequest(requestSpecification, GET_SCORE_USER);
  }

  public Response getCourses() {
    return getRequest(requestSpecification, GET_COURSES);
  }

  public Response getAllUsers() {
    return getRequest(requestSpecification, GET_ALL_USERS);
  }
}
