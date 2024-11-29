package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.models.Abrigo;
import br.com.alura.services.AbrigoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbrigoServiceTest {
    // Mock é uma simulação de uma instância, não faz a chamada real
    private ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);
    // Para ser instânciado, precisa de um clientconfiguration para o construtor
    private AbrigoService abrigoService = new AbrigoService(client);

    // A resposta também é necessária a simulação para validar o comportamento do método
    private HttpResponse<String> response = mock(HttpResponse.class);
    // Um objeto de teste para o body do response
    private Abrigo abrigo = new Abrigo("Teste", "12345678910", "teste@teste.com");

    @Test
    public void deveVerificarQuandoHaAbrigo() throws IOException, InterruptedException {
        //Id gerado
        abrigo.setId(0L);
        // Sout("Abrigos cadastrados:")
        String expectedAbrigosCadastrados = "Abrigos cadastrados:";
        //Id gerado + - + Nome passado no construtor
        String expectedIdENome = "0 - Teste";

        // Array de bytes -> Retorno do sout
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Escrever no array de bytes
        PrintStream printStream = new PrintStream(baos);
        // Colocar o printStream no setOut
        System.setOut(printStream);

        //Quando o response tiver body, retorna o toStrng do abrigo
        when(response.body()).thenReturn("[{"+abrigo.toString()+"}]");
        //Quando o client chamar o metodo, retorna o response que tem o body que está sendo mocado
        when(client.iniciarRequisicaoGet(anyString())).thenReturn(response);

        abrigoService.listarAbrigo();

        //Dentro do array de bytes
        String[] lines = baos.toString().split(System.lineSeparator());
        //Cria a variável com o valor da primeira linha do retorno do array de bytes
        String actualAbrigosCadastrados = lines[0];
        //Esse como a segunda
        String actualIdENome = lines[1];

        //Verifica se os dois são iguais -> Correto
        Assertions.assertEquals(expectedAbrigosCadastrados, actualAbrigosCadastrados);
        Assertions.assertEquals(expectedIdENome, actualIdENome);
    }

    @Test
    public void deveVerificarQuandoNaoHaAbrigo() throws IOException, InterruptedException {
        abrigo.setId(0L);
        String expected = "Não há abrigos cadastrados";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        //Não passamos nada por que queremos o Retorno vazio
        //Então simulamos o array vazio no body
        when(response.body()).thenReturn("[]");
        when(client.iniciarRequisicaoGet(anyString())).thenReturn(response);

        abrigoService.listarAbrigo();

        //Só haverá 1 retorno, a mensagem experada
        String[] lines = baos.toString().split(System.lineSeparator());
        String actual = lines[0];

        Assertions.assertEquals(expected, actual);
    }
}
