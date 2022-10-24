package com.oyataco.converToBatch;

import com.oyataco.tiposFicheros.FileServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

@SpringBootApplication
public class ConverToBatchApplication {

	public static void main(String[] args) {

		String pathCSV;
		String pathJSON;
		String pathTXT;
		String selectedProperty;

		Scanner sc =new Scanner(System.in);
		Properties properties=new Properties();
		InputStream entrada=null;
		FileServices services;

		try{
			String ficheroProperty="src//main//resources//application.properties";
			entrada=new FileInputStream(ficheroProperty);//decimos que el fichero que quiero leer se llama ficheros.properties

			properties.setProperty("txt","\\taskOscarTXT.txt");//Añadimos clave valor
			properties.setProperty("csv","\\taskOscarCSV.csv");//Añadimos clave valor
			properties.setProperty("json","\\taskOscarJSON.json");//Añadimos clave valor

			properties.store(new FileWriter(ficheroProperty),"Archivos properties");//seleccionamos cual vamos a actualizar y añadimos su comentario

			properties.load(entrada);//cargamos nuestro properties

		}catch (FileNotFoundException e){
			e.printStackTrace();
			System.err.println(e.getMessage());

		}catch (IOException e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}



		System.out.println("seleccione el tipo de fichero que sea crear"+
				"\n1:txt"+
				"\n2:csv"+
				"\n3:json"+
				"\n4:batch");

		selectedProperty=sc.nextLine();

		try {
			String pathResource="\\src\\main\\resources";

			pathTXT=System.getProperty("user.dir")+ pathResource + properties.getProperty("txt");
			pathCSV=System.getProperty("user.dir") + pathResource + properties.getProperty("csv");
			pathJSON=System.getProperty("user.dir") + pathResource + properties.getProperty("json");


			switch (selectedProperty) {
				case "1", "txt" -> {

					System.out.println(pathTXT);
					services = new FileServices();
					services.createFileTXT(pathTXT);
				}

				case "2", "csv" -> {

					System.out.println(pathCSV);
					services=new FileServices();
					services.createFileCSV(pathCSV);
				}

				case "3", "json" -> {

					System.out.println(pathJSON);
					services=new FileServices();
					services.createFileJSON(pathJSON);
				}
				case "4", "batch" -> {
					//REVISAR TODOS LOS CASE

					SpringApplication.run(ConverToBatchApplication.class, args);
				}

				default -> {
					System.err.println("No ha seleccionado correctamente"+
							"\nRecuerde elegir el numero o teclear correctamente en minúscula");
					main(args);
					System.exit(0);
				}

			}
		}catch (Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
			main(args);
			System.exit(0);
		}


	}

}
