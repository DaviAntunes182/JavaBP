package br.com.alura;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.services.AbrigoService;

import java.io.IOException;

public class ListarAbrigoCommand implements Command{
    @Override
    public void execute() {

        ClientHttpConfiguration config = new ClientHttpConfiguration();
        AbrigoService abrigoService = new AbrigoService(config);
        try{
        abrigoService.listarAbrigo();
        }catch (IOException | InterruptedException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
