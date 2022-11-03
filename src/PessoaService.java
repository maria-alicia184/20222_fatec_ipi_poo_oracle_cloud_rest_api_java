import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.RequiredArgsConstructor;
import java.util.*;

@RequiredArgsConstructor
public class PessoaService {
    private static HttpClient client = HttpClient.newHttpClient();
    private final String url;


    public List<Pessoa> listar(){
        //generics
        List <Pessoa> pessoas = new ArrayList<>();
        try{
            //design pattern: builder
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)).
            build();
            var response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject raiz = new JSONObject(response.body());
            JSONArray items = raiz.getJSONArray("items");
            for(int i =0; i < items.length(); i++){
            JSONObject pessoaJSON = items.getJSONObject(i);
            String nome = pessoaJSON.getString("nome");
            int idade = pessoaJSON.getInt("idade");
            String hobby = pessoaJSON.getString("hobby");
            System.out.printf("%s, %d, %s\n", nome, idade, hobby);
            Pessoa p = new Pessoa();
            p.setNome(nome);
            p.setIdade(idade);
            p.setHobby(hobby);
            pessoas.add(p);
        }
        }

        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return pessoas;
    }

}
