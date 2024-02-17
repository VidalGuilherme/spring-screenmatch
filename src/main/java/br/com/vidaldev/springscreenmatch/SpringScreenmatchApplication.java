package br.com.vidaldev.springscreenmatch;

import br.com.vidaldev.springscreenmatch.dtos.SerieApiDto;
import br.com.vidaldev.springscreenmatch.services.ConsumerApi;
import br.com.vidaldev.springscreenmatch.services.JacksonDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringScreenmatchApplication implements CommandLineRunner {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SpringScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String apiKey = env.getProperty("omdb.key");
		ConsumerApi consumerApi = new ConsumerApi();
		JacksonDataConverter converter = new JacksonDataConverter();
		var baseUrl = "http://www.omdbapi.com/?apikey="+apiKey;
		var json = consumerApi.getData(baseUrl+"&t=vikings");
		System.out.println(json);
		SerieApiDto serieApiDto = converter.convertData(json, SerieApiDto.class);
		System.out.println(serieApiDto);
	}
}
