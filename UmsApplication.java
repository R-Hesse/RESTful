package org.ac.cst8277.hesse.ryan;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UmsApplication {

	public static void main(String[] args) throws SQLException, IOException {
		SpringApplication.run(UmsApplication.class, args);
		
	}

}
