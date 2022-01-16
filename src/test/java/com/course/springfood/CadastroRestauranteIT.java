package com.course.springfood;

import com.course.springfood.domain.model.Cozinha;
import com.course.springfood.domain.model.Restaurante;
import com.course.springfood.domain.repository.CozinhaRepository;
import com.course.springfood.domain.repository.RestauranteRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static com.course.springfood.util.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT extends RestAssuredOAuth2Test {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private static final int RESTAURANTE_ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private Restaurante donTabascoRestaurante;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/v1/restaurantes";
    }

    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
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
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
            .header("Authorization", "Bearer " + getToken())
            .body(getContentFromResource("/json/incorreto/restaurante-khea-thai-sem-frete.json"))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
            .header("Authorization", "Bearer " + getToken())
            .body(getContentFromResource("/json/incorreto/restaurante-khea-thai-sem-cozinha.json"))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        cadastrarRestaurante();

        given()
            .header("Authorization", "Bearer " + getToken())
            .pathParam("restauranteId", donTabascoRestaurante.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(donTabascoRestaurante.getNome()));
    }

    @Test
    @Sql(scripts = "classpath:db/testdata/afterMigrate.sql")
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
            .header("Authorization", "Bearer " + getToken())
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void cadastrarRestaurante() {
        Cozinha cozinhaMexicana = new Cozinha();
        cozinhaMexicana.setNome("Mexicana");
        cozinhaRepository.save(cozinhaMexicana);

        donTabascoRestaurante = new Restaurante();
        donTabascoRestaurante.setNome("Don Tabasco Cocina Mexicana");
        donTabascoRestaurante.setTaxaFrete(new BigDecimal(10));
        donTabascoRestaurante.setCozinha(cozinhaMexicana);
        restauranteRepository.save(donTabascoRestaurante);
    }

}
