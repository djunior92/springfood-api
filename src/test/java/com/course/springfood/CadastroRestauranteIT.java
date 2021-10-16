package com.course.springfood;

import com.course.springfood.domain.model.Cozinha;
import com.course.springfood.domain.model.Restaurante;
import com.course.springfood.domain.repository.CozinhaRepository;
import com.course.springfood.domain.repository.RestauranteRepository;
import com.course.springfood.util.DatabaseCleaner;
import com.course.springfood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static com.course.springfood.util.ResourceUtils.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private static final int RESTAURANTE_ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;

    private Restaurante donTabascoRestaurante;
    private Restaurante sushiRestaurant;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        jsonRestauranteCorreto = getContentFromResource(
                "/json/correto/restaurante-khea-thai.json");

        jsonRestauranteSemFrete = getContentFromResource(
                "/json/incorreto/restaurante-khea-thai-sem-frete.json");

        jsonRestauranteSemCozinha = getContentFromResource(
                "/json/incorreto/restaurante-khea-thai-sem-cozinha.json");

        jsonRestauranteComCozinhaInexistente = getContentFromResource(
                "/json/incorreto/restaurante-khea-thai-com-cozinha-inexistente.json");

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
                .body(jsonRestauranteCorreto)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
                .body(jsonRestauranteSemFrete)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
                .body(jsonRestauranteSemCozinha)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
                .body(jsonRestauranteComCozinhaInexistente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
                .pathParam("restauranteId", donTabascoRestaurante.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(donTabascoRestaurante.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinhaJaponesa= new Cozinha();
        cozinhaJaponesa.setNome("Japonesa");
        cozinhaRepository.save(cozinhaJaponesa);

        Cozinha cozinhaMexicana = new Cozinha();
        cozinhaMexicana.setNome("Mexicana");
        cozinhaRepository.save(cozinhaMexicana);

        donTabascoRestaurante = new Restaurante();
        donTabascoRestaurante.setNome("Don Tabasco Cocina Mexicana");
        donTabascoRestaurante.setTaxaFrete(new BigDecimal(10));
        donTabascoRestaurante.setCozinha(cozinhaMexicana);
        restauranteRepository.save(donTabascoRestaurante);

        sushiRestaurant = new Restaurante();
        sushiRestaurant.setNome("Sushi Restaurant");
        sushiRestaurant.setTaxaFrete(new BigDecimal(10));
        sushiRestaurant.setCozinha(cozinhaJaponesa);
        restauranteRepository.save(sushiRestaurant);
    }

}