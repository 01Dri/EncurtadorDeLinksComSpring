package me.dri.EncurtadorLinks;

import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import me.dri.EncurtadorLinks.services.UrlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class EncurtadorLinksApplication {



	public static void main(String[] args) throws NoSuchAlgorithmException {

		SpringApplication.run(EncurtadorLinksApplication.class, args);
	}

}
