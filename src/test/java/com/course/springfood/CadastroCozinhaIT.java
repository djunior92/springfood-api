package com.course.springfood;

import com.course.springfood.domain.model.Cozinha;
import com.course.springfood.domain.repository.CozinhaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static com.course.springfood.util.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT extends RestAssuredOAuth2Test {

    private static final int COZINHA_ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Cozinha cozinhaAmericana;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/v1/cozinhas";
    }

    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given()
            .header("Authorization", "Bearer " + getToken())
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
        given()
            .header("Authorization", "Bearer " + getToken())
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("_embedded.cozinhas", hasSize((int) cozinhaRepository.count()));
    }

    @Disabled
    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
            .header("Authorization", "Bearer " + getToken())
            .body(getContentFromResource("/json/correto/cozinha-chinesa.json"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        cadastrarCozinha();

        given()
            .header("Authorization", "Bearer " + getToken())
            .pathParam("cozinhaId", cozinhaAmericana.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        given()
            .header("Authorization", "Bearer " + getToken())
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void cadastrarCozinha() {
        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);
    }

}