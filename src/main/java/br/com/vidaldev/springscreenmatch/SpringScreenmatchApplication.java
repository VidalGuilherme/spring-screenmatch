package br.com.vidaldev.springscreenmatch;

import br.com.vidaldev.springscreenmatch.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringScreenmatchApplication implements CommandLineRunner {

	@Autowired
	Main main;

	public static void main(String[] args) {
		SpringApplication.run(SpringScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		main.showMenu();
	}
}
