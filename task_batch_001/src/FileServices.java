import com.csvreader.CsvWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;


public class FileServices {


    /**
     * Metodo para crear y escribir el archivo con formato TXT
     * @param path
     */
    public void createFileTXT(String path){

        LocalDate currentDate=LocalDate.now();
        boolean exist=new File(path).exists();
        if (exist){
            File existFile=new File(path);
            existFile.delete();
        }
        File file=new File(path);//Clase File representa un archivo, el cual contiene su direccion

        try(PrintWriter print=new PrintWriter(new FileWriter(file))){
            String date;
            //Aqui empieza lo de las fechas
            try {
                Month month=Month.JANUARY;
                boolean esBisiesto=LocalDate.now().isLeapYear();

                for (int i = 0; i < 12 ; i++) {
                    date=currentDate.getYear()+"\t"+month.plus(i)+"\t["+month.plus(i).length(esBisiesto)+
                            "]\t[Primer día del mes: "+LocalDate.of(2022,(i+1),1).getDayOfWeek()+"]";

                    print.println(date);
                }


            }catch (Exception e){
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
            //Aquí termina lo de las fechas


        }catch(IOException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }


    //==========================================
    //==========================================

    /**
     * Método para crear y escribir el archivo en formato CSV
     * @param path Contendrá la ruta del archivo
     */
    public void createFileCSV(String path){
        LocalDate currentDate=LocalDate.now();

        boolean exist=new File(path).exists();

        //Si el archivo existe lo borrará
        if (exist){
            File existFile=new File(path);
            existFile.delete();
        }

        File file=new File(path);

        try{

            CsvWriter csvWriter=new CsvWriter(new FileWriter(path),',');//creando el archivo

            try {
                int year=currentDate.getYear();
                String currentYear= Integer.toString(year);

                boolean isLeapYear=LocalDate.now().isLeapYear();

                for (int i = 0; i < 12 ; i++) {

                    csvWriter.write(currentYear);

                    Month month=Month.JANUARY.plus(i);
                    csvWriter.write(month.toString());

                    String totalDays=Integer.toString(month.length(isLeapYear));
                    csvWriter.write(totalDays);

                    String firstDay=String.valueOf(LocalDate.of(2022,(i+1),1).getDayOfWeek());
                    csvWriter.write("Primer dia del mes: "+firstDay);

                    csvWriter.endRecord();//deja de escribir en el archivo
                }

                csvWriter.close();//finaliza el archivo
            }catch (Exception e){
                e.printStackTrace();
                System.err.println(e.getMessage());
            }




        }catch (IOException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

    //==========================================
    //==========================================

    /**
     * Metodo para crear y escribir el archivo con formato JSON
     * @param path
     */
    public void createFileJSON (String path){
        JSONObject jObject=new JSONObject();
        JSONArray monthList=new JSONArray();
        LocalDate currentDate=LocalDate.now();


        boolean exist=new File(path).exists();

        if (exist){
            File existFile=new File(path);
            existFile.delete();
        }

        File file=new File(path);


            try {
                int year=currentDate.getYear();
                String currentYear= Integer.toString(year);

                boolean isLeapYear=LocalDate.now().isLeapYear();

                JSONObject objectMes;

                for (int i = 0; i < 12 ; i++) {
                    objectMes=objectMes=new JSONObject();

                    objectMes.put("year",currentYear);


                    Month month=Month.JANUARY.plus(i);
                    objectMes.put("name",month.toString());


                    String totalDays=Integer.toString(month.length(isLeapYear));
                    objectMes.put("numberofdays",totalDays);


                    String firstDay=String.valueOf(LocalDate.of(2022,(i+1),1).getDayOfWeek());
                    objectMes.put("firstdayofweek","primer dia del mes: "+firstDay);


                    monthList.put(objectMes);
                }
                jObject.put("months",monthList);



            }catch (Exception e){
                e.printStackTrace();
                System.err.println(e.getMessage());
            }

            try(FileWriter fileWriter=new FileWriter(file)) {
                fileWriter.write(jObject.toString()+"\n");
                fileWriter.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

}