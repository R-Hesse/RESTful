package org.ac.cst8277.hesse.ryan;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws SQLException, IOException {
		SpringApplication.run(Application.class, args);
		
	}
}
