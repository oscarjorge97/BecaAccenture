import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {


        String path;
        String selectedProperty;


        Scanner sc =new Scanner(System.in);
        Properties properties=new Properties();
        InputStream entrada=null;
        FileServices services;

        try{

            entrada=new FileInputStream("ficheros.properties");//decimos que el fichero que quiero leer se llama ficheros.properties

            properties.setProperty("txt","\\taskOscarTXT.txt");//Añadimos clave valor
            properties.setProperty("csv","\\taskOscarCSV.csv");//Añadimos clave valor
            properties.setProperty("json","\\taskOscarJSON.json");//Añadimos clave valor

            properties.store(new FileWriter("ficheros.properties"),"Archivos properties");//seleccionamos cual vamos a actualizar y añadimos su comentario

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
                "\n3:json");

        selectedProperty=sc.nextLine();
        try {

            switch (selectedProperty) {
                case "1", "txt" -> {
                    path = System.getProperty("user.dir") + properties.getProperty("txt");
                    System.out.println(path);
                    services = new FileServices();
                    services.createFileTXT(path);
                }

                case "2", "csv" -> {
                    path = System.getProperty("user.dir") + properties.getProperty("csv");
                    System.out.println(path);
                    services=new FileServices();
                    services.createFileCSV(path);
                }

                case "3", "json" -> {
                    path = System.getProperty("user.dir") + properties.getProperty("json");
                    System.out.println(path);
                    services=new FileServices();
                    services.createFileJSON(path);
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