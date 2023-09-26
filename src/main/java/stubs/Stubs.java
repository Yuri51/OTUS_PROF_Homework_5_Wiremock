package stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import utils.JsonUtil;


public class Stubs {


  private JsonUtil jsonUtil;
  public WireMockServer wireMockServer;

  public Stubs setUp() {
    wireMockServer = new WireMockServer(20);
    wireMockServer.start();
    jsonUtil = new JsonUtil();
    return this;
  }

  public Stubs resetServer() {
    wireMockServer.resetAll();
    return this;
  }

  public Stubs stopServer() {
    wireMockServer.stop();
    return this;
  }

  public Stubs stubGetCourses(String responseFileName) {
    wireMockServer.stubFor(get("/course/get/all")
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBodyFile(responseFileName)));
    return this;
  }

  public Stubs stubGetScoreUser(String responseFileName) {
    wireMockServer.stubFor(get(urlPathMatching("/user/get/([0-9]*)"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBodyFile(responseFileName)));
    return this;
  }

  public Stubs stubGetUsers(String responseFileName) {
    wireMockServer.stubFor(get("/user/get/all")
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBodyFile(responseFileName)));
    return this;
  }

  public Stubs startStatus() {
    System.out.println("stubs.Stubs started!");
    return this;
  }

  public Stubs stopStatus() {
    System.out.println("stubs.Stubs stopped!");
    return this;
  }
}