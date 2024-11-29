package br.com.alura;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.services.AbrigoService;
import br.com.alura.services.PetService;

import java.io.IOException;

public class ListarPetsDoAbrigoCommand implements Command{
    @Override
    public void execute() {
        ClientHttpConfiguration config = new ClientHttpConfiguration();
        PetService petService = new PetService(config);
        try{
            petService.listarPetsDoAbrigo();
        }catch (IOException | InterruptedException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
