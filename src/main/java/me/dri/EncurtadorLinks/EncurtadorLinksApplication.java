package me.dri.EncurtadorLinks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class  EncurtadorLinksApplication {



	public static void main(String[] args) throws NoSuchAlgorithmException {

		SpringApplication.run(EncurtadorLinksApplication.class, args);
	}

}
