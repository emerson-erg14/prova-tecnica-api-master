package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

//Classe de testes para a API
public class APITest {
    // Variável para armazenar o código de status
    int StatusCode;

    // Teste para verificar se há restrições para CPF
    @Test
    public void verificaRestricaoCPF() {

        // Cria uma lista para armazenar os CPFs
        List cpf = new ArrayList<>();
        cpf.add("97093236014");
        cpf.add("60094146012");
        cpf.add("84809766080");
        cpf.add("62648716050");
        cpf.add("26276298085");
        cpf.add("01317496094");
        cpf.add("55856777050");
        cpf.add("19626829001");
        cpf.add("24094592008");
        cpf.add("58063164083");

        // Loop para verificar cada CPF
        for (int i = 0; i < cpf.size(); i++) {
            Response response = RestAssured.get("http://localhost:8080/api/v1/restricoes/" + cpf.get(i));

            StatusCode = response.getStatusCode();
            System.out.println("Fiz o Check do cpf: " + cpf.get(i));

            // Verificar se o status é igual a 200
            Assert.assertEquals(StatusCode, 200);
        }

    }

    // Teste para criar uma simulação com sucesso
    @Test
    public void criarSimulacao201() {

        // Cria um Map para armazenar os dados
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("nome", "Emerson Rodrigues");
        map.put("cpf", "97093236014");
        map.put("email", "emersonrodrigues.com.br");
        map.put("valor", "1200");
        map.put("parcelas", "3");
        map.put("seguro", "true");

        // Cria um objeto JSON com os dados
        JSONObject request = new JSONObject(map);

        System.out.println(request.toJSONString());

        // Realiza uma postagem com os dados, e verifica se o status é 201(Sucesso)
        given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()).when().post("http://localhost:8080/api/v1/simulacoes/").then()
                .statusCode(201);
    }

    // Teste para criar uma simulação com erro
    @Test
    public void criarSimulacao400() {

        // Cria um Map para armazenar os dados
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("nome", "Emerson Rodrigues");
        map.put("cpf", "97093236014");
        map.put("email", "emersonrodrigues.com.br");
        map.put("valor", "1200");
        map.put("parcelas", "3");
        map.put("seguro", "true");

        // Cria um objeto JSON com os dados
        JSONObject request = new JSONObject(map);

        System.out.println(request.toJSONString());

        // Realiza uma postagem com os dados, e verifica se o status é 400(Erro)
        given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()).when().post("http://localhost:8080/api/v1/simulacoes/").then()
                .statusCode(400);
    }

    // Teste para criar uma simulação com conflito
    @Test
    public void criarSimulacao409() {

        // Cria um Map para armazenar os dados
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("nome", "Emerson Rodrigues");
        map.put("cpf", "97093236014");
        map.put("email", "emersonrodrigues.com.br");
        map.put("valor", "1200");
        map.put("parcelas", "3");
        map.put("seguro", "true");

        // Cria um objeto JSON com os dados
        JSONObject request = new JSONObject(map);

        System.out.println(request.toJSONString());

        // Realiza uma postagem com os dados, e verifica se o status é 409(Conflito)
        given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()).when().post("http://localhost:8080/api/v1/simulacoes/").then()
                .statusCode(409);
    }

    // Teste para alterar uma simulação com sucesso
    @Test
    public void alterarSimulacaoCPF200() {
        String cpf = "97093236014";

        // Cria um Map para armazenar os dados
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("nome", "Emerson Ro_90");
        map.put("cpf", "97093236014");
        map.put("email", "emersonrodrigues.com.br");
        map.put("valor", "1200");
        map.put("parcelas", "3");
        map.put("seguro", "true");
        // Cria um objeto JSON com os dados
        JSONObject request = new JSONObject(map);

        System.out.println(request.toJSONString());
        // Realiza uma postagem com os dados, e verifica se o status é 200(Sucesso)
        given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()).when().put("http://localhost:8080/api/v1/simulacoes/" + cpf).then()
                .statusCode(200);
    }

    // Teste para alterar uma simulação com erro
    @Test
    public void alterarSimulacaoCPF404() {
        String cpf = "97093238048";
        // Cria um Map para armazenar os dados
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("nome", "Emerson Rodrigues");
        map.put("cpf", "97093236014");
        map.put("email", "emersonrodrigues.com.br");
        map.put("valor", "1200");
        map.put("parcelas", "3");
        map.put("seguro", "true");

        JSONObject request = new JSONObject(map);

        System.out.println(request.toJSONString());

        given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()).when().put("http://localhost:8080/api/v1/simulacoes/" + cpf).then()
                .statusCode(404);
    }
    // Teste para alterar uma simulação com um cpf existente

    @Test
    public void alterarSimulacaoCPF409() {
        String cpf = "97093236014";

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("nome", "Emerson Rodrigues");
        map.put("cpf", "97093236014");
        map.put("email", "emersonRodrigues.com.br");
        map.put("valor", "1200");
        map.put("parcelas", "3");
        map.put("seguro", "true");

        JSONObject request = new JSONObject(map);

        System.out.println(request.toJSONString());

        given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()).when().put("http://localhost:8080/api/v1/simulacoes/" + cpf).then()
                .statusCode(409);
    }
    // Teste para consultar uma simulação cadastrada

    @Test
    public void consultarSimulacaoCadastrada() {

        Response response = RestAssured.get("http://localhost:8080/api/v1/simulacoes/");
        StatusCode = response.getStatusCode();
        Assert.assertEquals(StatusCode, 204);

    }

    // Teste para consultar uma simulação cadastrada atraves do cpf
    @Test
    public void consultarSimulacaoCadastradaPorCPF() {

        String cpf = "97093236014";
        // Realiza uma requisição do tipo GET para consultar a simulação

        Response response = RestAssured.get("http://localhost:8080/api/v1/simulacoes/" + cpf);
        StatusCode = response.getStatusCode();
        Assert.assertEquals(StatusCode, 404);

    }

    // Teste para deletar uma simulação cadastrada atraves do id com status code 204
    @Test
    public void deletarSimulacaoCadastradaPorID204() {

        String id = "1";
        // Realiza uma requisição do tipo DELETE para deletar a simulação

        Response response = RestAssured.get("http://localhost:8080/api/v1/simulacoes/" + id);
        StatusCode = response.getStatusCode();
        Assert.assertEquals(StatusCode, 204);

    }
    // Teste para deletar uma simulação cadastrada atraves do id com status code 404

    @Test
    public void deletarSimulacaoCadastradaPorID404() {

        String id = "1";
        // Realiza uma requisição do tipo DELETE para deletar a simulação
        Response response = RestAssured.get("http://localhost:8080/api/v1/simulacoes/" + id);
        StatusCode = response.getStatusCode();
        Assert.assertEquals(StatusCode, 404);

    }

}

/*
 * Teste verificaRestricaoCPF()
 * Verifica se os CPFs presentes na lista estão validos
 *
 * Teste criarSimulacao201()
 * Cria uma simulação e testa se o status code é igual a 201
 *
 * Teste criarSimulacao400()
 * Cria uma simulação com parametros errados e testa se o status code é igual a
 * 400
 *
 * Teste criarSimulacao409()
 * Cria uma simulação com um CPF já existente e testa se o status code é igual a
 * 409
 *
 * Teste alterarSimulacaoCPF200()
 * Altera uma simulação por meio do CPF e testa se o status code é igual a 200
 *
 * Teste alterarSimulacaoCPF404()
 * Altera uma simulação com um CPF inexistente e testa se o status code é igual
 * a 404
 *
 * Teste alterarSimulacaoCPF409()
 * Altera uma simulação com um email inválido e testa se o status code é igual a
 * 409
 *
 * Teste consultarSimulacaoCadastrada()
 * Consulta uma simulação e testa se o status code é igual a 204
 *
 * Teste consultarSimulacaoCadastradaPorCPF()
 * Consulta uma simulação por meio do CPF e testa se o status code é igual a 404
 *
 * Teste deletarSimulacaoCadastradaPorID204()
 * Deleta uma simulação por meio do ID e testa se o status code é igual a 204
 *
 * Teste deletarSimulacaoCadastradaPorID404()
 * Deleta uma simulação com um ID inexistente e testa se o status code é igual a
 * 404
 */