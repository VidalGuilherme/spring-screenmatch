package br.com.vidaldev.springscreenmatch.main;

import br.com.vidaldev.springscreenmatch.dtos.api.EpisodeDto;
import br.com.vidaldev.springscreenmatch.dtos.api.SeasonDto;
import br.com.vidaldev.springscreenmatch.dtos.api.SerieDto;
import br.com.vidaldev.springscreenmatch.enums.TextLanguage;
import br.com.vidaldev.springscreenmatch.interfaces.IDataConverter;
import br.com.vidaldev.springscreenmatch.interfaces.ITextTranslater;
import br.com.vidaldev.springscreenmatch.services.ConsumerApi;
import br.com.vidaldev.springscreenmatch.services.JacksonDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Main {

    @Value(value = "${omdb.key}")
    private String API_KEY;

    private static String BASE_URL = "http://www.omdbapi.com/?type=series";

    private final Scanner scanner = new Scanner(System.in);

    private final ConsumerApi consumerApi = new ConsumerApi();

    private IDataConverter converter = new JacksonDataConverter();

    @Autowired
    private ITextTranslater translater;

    public void showMenu(){
        try {

            String choice = "";
            while (true) {
                System.out.println("\n##### BEM VINDO AO SCREEN MATCH CONSOLE #####");
                System.out.println("Digite 0 para sair.");
                System.out.println("Digite o nome da série:");
                choice = new Scanner(System.in).nextLine();
                if (choice.equals("0")) {
                    break;
                }
                SearchTitle(choice);
            }

            System.out.println("Finalizando o programa...");
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Digite o nome da série");
        var title = scanner.nextLine();
    }

    private void SearchTitle(String title){

        var url = BASE_URL+ "&apikey="+API_KEY;
        title = title.replace(" ", "+");

        //Get Serie
        var json = consumerApi.getData(url+"&t="+title);
        SerieDto serieDto = converter.convertData(json, SerieDto.class);

        if(serieDto != null){

            List<SeasonDto> seasons = new ArrayList<>();
            var totalSeasons = serieDto.totalSeasons().equals("N/A") ? 0 : Integer.parseInt(serieDto.totalSeasons());
            for (int i = 1; i <= totalSeasons; i++){
                json = consumerApi.getData(url+"&t="+title+"&season="+i);
                SeasonDto seasonDto = converter.convertData(json, SeasonDto.class);
                seasons.add(seasonDto);
            }

            List<EpisodeDto> allEpisodes = seasons.stream()
                    .flatMap(s -> s.episodes().stream().map(e -> e.withSeason(s.season())) )
                    .toList();

            List<String> top10BestEspisodes = allEpisodes.stream()
                    .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                    .sorted(Comparator.comparing(EpisodeDto::rating).reversed())
                    .limit(10)
                    .map(e -> "- T"+e.season()+"E"+e.episode()+" Nota: "+e.rating() + " - Título: "+e.title()+"\n")
                    .toList();

            List<String> top10WorstEspisodes = allEpisodes.stream()
                    .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                    .sorted(Comparator.comparing(EpisodeDto::rating))
                    .limit(10)
                    .map(e -> "- T"+e.season()+"E"+e.episode()+" Nota: "+e.rating() + " - Título: "+e.title()+"\n")
                    .toList();

            Map<Integer, Double> ratingSeason = allEpisodes.stream()
                    .filter(e -> checkRating(e.rating()))
                    .collect(Collectors.groupingBy(EpisodeDto::season, Collectors.averagingDouble(e -> Double.parseDouble(e.rating()))));

            DoubleSummaryStatistics est = allEpisodes.stream()
                    .filter(e -> checkRating(e.rating()))
                    .collect(Collectors.summarizingDouble(e -> Double.parseDouble(e.rating())));

            System.out.println("#### RESUMO DA SÉRIE #####");
            System.out.println(serieDto);
            System.out.println("\nResumo: "+translater.translate(serieDto.plot(), TextLanguage.PT_BR));

            if(!seasons.isEmpty()) {
                Optional<Map.Entry<Integer, Double>> bestSeason = ratingSeason.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
                Optional<Map.Entry<Integer, Double>> worstSeason = ratingSeason.entrySet().stream().min(Comparator.comparing(Map.Entry::getValue));

                System.out.println(
                        "\nMelhor temporada: " + (bestSeason.isPresent() ? "T" + bestSeason.get().getKey() + " Nota: " + bestSeason.get().getValue() : "Não Definida") +
                        "\nPior temporada: " + (worstSeason.isPresent() ? "T" + worstSeason.get().getKey() + " Nota: " + worstSeason.get().getValue() : "Não Definida")
                );
                System.out.println(
                        "\nTotal de episódios: " + allEpisodes.size() +
                        "\nMelhor episódio: " + top10BestEspisodes.getFirst() +
                        "Pior episódio: " + top10WorstEspisodes.getFirst()
                );

                System.out.println("\n## Nota média por Temporada:");
                seasons.forEach(s -> System.out.println("T" + s.season() + " - Nota: " + String.format("%.1f", ratingSeason.get(s.season())) + " - Episódios: " + s.episodes().size()));

                System.out.println("\n## Top 10 Melhores Episódios");
                top10BestEspisodes.forEach(System.out::println);

                System.out.println("\n## Top 10 Piores Episódios");
                top10WorstEspisodes.forEach(System.out::println);
            }else {
                System.out.println("É um filme!");
            }

            System.out.println("\n\n########################");
        }
    }

    boolean checkRating(String rating){
        try {
            return Double.parseDouble(rating) > 0.0;
        }catch (NumberFormatException exception){
            return  false;
        }
    }
}
