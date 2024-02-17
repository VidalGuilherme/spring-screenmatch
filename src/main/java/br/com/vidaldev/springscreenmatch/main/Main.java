package br.com.vidaldev.springscreenmatch.main;

import br.com.vidaldev.springscreenmatch.dtos.api.SeasonDto;
import br.com.vidaldev.springscreenmatch.dtos.api.SerieDto;
import br.com.vidaldev.springscreenmatch.interfaces.IDataConverter;
import br.com.vidaldev.springscreenmatch.services.ConsumerApi;
import br.com.vidaldev.springscreenmatch.services.JacksonDataConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Main {

    @Value(value = "${omdb.key}")
    private String API_KEY;

    private static String BASE_URL = "http://www.omdbapi.com/";

    private final Scanner scanner = new Scanner(System.in);

    private final ConsumerApi consumerApi = new ConsumerApi();

    IDataConverter converter = new JacksonDataConverter();

    public void showMenu(){
        System.out.println("Digite o nome da série");
        var title = scanner.nextLine();
        var url = BASE_URL+ "?apikey="+API_KEY;

        title = title.replace(" ", "+");
        //Get Serie
        var json = consumerApi.getData(url+"&t="+title);
        System.out.println(json);
        SerieDto serieDto = converter.convertData(json, SerieDto.class);
        System.out.println(serieDto);

        var season = 1;
        //Get Season
        json = consumerApi.getData(url+"&t="+title+"&season="+season);
        System.out.println(json);
        SeasonDto seasonDto = converter.convertData(json, SeasonDto.class);
        System.out.println(seasonDto);

        //Show Episodes
        if(seasonDto.episodes() != null){
            System.out.println("Episódios:");
            seasonDto.episodes().forEach(System.out::println);
        }
    }
}
