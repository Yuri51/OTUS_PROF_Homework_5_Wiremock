import static org.assertj.core.api.Assertions.assertThat;

import dto.CourseDTO;
import dto.ScoreUserDTO;
import dto.UsersDTO;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserApi;
import stubs.Stubs;
import java.util.List;

public class ApiTest {

  public static Stubs stubs = new Stubs();

  @BeforeAll
  public static void startWiremock() {
    stubs.setUp()
       .stubGetCourses("getCourses.json")
        .stubGetScoreUser("getScoreUser.json")
        .stubGetUsers("getUsers.json")
        .startStatus();
  }

  @Test
  @DisplayName("Получение всех клиентов")
  public void userGetAllTest() {
    UserApi userApi = new UserApi();
    Response response = userApi.getAllUsers();
    assertThat(response.getStatusCode())
        .as("Запрос не прошел успешно")
        .isEqualTo(200);

    UsersDTO actualUsers = response.getBody().as(UsersDTO.class);
    UsersDTO expectedUsers = UsersDTO.builder()
        .name("Test user")
        .course("QA")
        .email("test@test.test")
        .age(23)
        .build();

    assertThat(actualUsers)
        .as("Ответ не соответствует ожиданиям")
        .isEqualTo(expectedUsers);
  }

  @Test
  @DisplayName("Получение всех курсов")
  public void courseGetAllTest() {
    UserApi userApi = new UserApi();
    Response response = userApi.getCourses();
    assertThat(response.getStatusCode())
        .as("Запрос не прошел успешно")
        .isEqualTo(200);

    List<CourseDTO> actualCourse = List.of(response.getBody().as(CourseDTO[].class));
    List<CourseDTO> expectedCourse = List.of(
        CourseDTO.builder()
            .name("QA java")
            .price(15000)
            .build(),
        CourseDTO.builder()
            .name("Java")
            .price(12000)
            .build()
    );
    assertThat(actualCourse)
        .as("Ответ не соответствует ожиданиям")
        .isEqualTo(expectedCourse);
  }

  @Test
  @DisplayName("Получение оценки пользователя")
  public void getUserForId() {
    UserApi userApi = new UserApi();
    Response response = userApi.getUser();
    assertThat(response.getStatusCode())
        .as("Запрос не прошел успешно")
        .isEqualTo(200);

    ScoreUserDTO actualCourse = response.getBody().as(ScoreUserDTO.class);
    ScoreUserDTO expectedCourse = ScoreUserDTO.builder()
            .name("Test user")
            .score(78)
            .build();

    assertThat(actualCourse)
        .as("Ответ не соответствует ожиданиям")
        .isEqualTo(expectedCourse);
  }

  @AfterAll
  public static void stopWiremockServer() {
    stubs.stopServer()
        .stopStatus();
  }
}


