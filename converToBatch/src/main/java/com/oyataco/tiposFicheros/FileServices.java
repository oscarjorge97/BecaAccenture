package com.oyataco.tiposFicheros;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.csv.*;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;


public class FileServices {

    /**
     * Metodo para crear y escribir el archivo con formato TXT
     * @param path le pasamos la ruta del archivo txt
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

            CSVPrinter csvWriter=new CSVPrinter(new FileWriter(path), CSVFormat.EXCEL);//creando el archivo

            try {
                int year=currentDate.getYear();
                String currentYear= Integer.toString(year);

                boolean isLeapYear=LocalDate.now().isLeapYear();

                for (int i = 0; i < 12 ; i++) {

                    Month month=Month.JANUARY.plus(i);

                    String totalDays=Integer.toString(month.length(isLeapYear));

                    String firstDay=String.valueOf(LocalDate.of(2022,(i+1),1).getDayOfWeek());


                    csvWriter.printRecord(currentYear,month.toString(),totalDays,"Primer dia del mes: "+firstDay);
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


    public void createFileXSL(String path) {

        //Si el archivo existe lo borrará
        boolean exist=new File(path).exists();
        if (exist){
            File existFile=new File(path);
            existFile.delete();
        }


        HSSFWorkbook workbook = new HSSFWorkbook();//Para el crear libro Excel
        HSSFSheet sheet = workbook.createSheet();//para crear una nueva hoja dentro del libro
        workbook.setSheetName(0, "DataMonth");

        HSSFCellStyle cellStyleDate=workbook.createCellStyle();
        cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("M"));

        CellStyle cellStyleHeader = workbook.createCellStyle();//creamos un estilo para la/s celda/s de la cabecera
        Font fontHeader = workbook.createFont();//creamos una fuente para la cabecera
        cellStyleHeader.setFillBackgroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex()); //le asignamos un color de fondo
        fontHeader.setBold(true); //seleccionamos una fuente
        cellStyleHeader.setFont(fontHeader); //asignamos la fuente a la/s celda/s de la cabecera


        CellStyle styleBody = workbook.createCellStyle(); //creamos un estilo para el cuerpo de la celda
        styleBody.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());//le asignamos un color de fondo
        //styleBody.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        HSSFRow rowHeader = sheet.createRow(0); //crear fila en la hoja


        /*CREANDO DATOS DE LA CABECERA*/
        String[] dataHeader = {"year", "month", "total_days", "first_day"};//data de la cabecera

        //for para añadir los datos de la cabecera en la misma fila
        for (int i = 0; i < dataHeader.length; i++) {
            String header = dataHeader[i]; //asignamos la info de la posicion [i] deL array dataHeader a la variable header
            HSSFCell cell = rowHeader.createCell(i);//creamos una celda en la fila de la cabecera y seleccionamos en que columna se crea

            cell.setCellStyle(cellStyleHeader); //añadimos los estilos creados para la celda
            cell.setCellValue(header); //añadimos el valor de la celda

        }
        /*FINALIZACION DE CREACION DE DATOS DE LA CABECERA*/


        /*CREANDO DATOS DEL CUERPO*/
       String [] monthNames={"January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        LocalDate currentDate = LocalDate.now();
        int year=currentDate.getYear();
        String currentYear= Integer.toString(year);
        boolean isLeapYear=LocalDate.now().isLeapYear();

        //for para insertar uno a uno los datos del cuerpo
        for (int i = 0; i < 12 ; i++) {
            HSSFRow dataMonthRow=sheet.createRow(i+1); //crear fila que empiece por la fila1 de la hoja
            Calendar calendar=Calendar.getInstance();

            Month month=Month.JANUARY.plus(i);
            String totalDays=Integer.toString(month.length(isLeapYear));
            String firstDay=String.valueOf(LocalDate.of(2022,(i+1),1).getDayOfWeek());

            dataMonthRow.createCell(0).setCellStyle(styleBody);
            dataMonthRow.createCell(0).setCellValue(currentYear);

            dataMonthRow.createCell(1).setCellStyle(styleBody);
            dataMonthRow.createCell(1).setCellValue(monthNames[i]);

            dataMonthRow.createCell(2).setCellStyle(styleBody);
            dataMonthRow.createCell(2).setCellValue(totalDays);

            dataMonthRow.createCell(3).setCellStyle(styleBody);
            dataMonthRow.createCell(3).setCellValue(firstDay);
        }

        try {
            FileOutputStream file=new FileOutputStream(path);
            workbook.write(file);
            file.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*FINALIZACION DE CREACION DE DATOS DEL CUERPO*/
    }



    //==========================================
    //==========================================

    /**
     * Metodo para crear y escribir el archivo con formato JSON
     * @param path Contiene la ruta de archivo JSON
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
                objectMes=new JSONObject();

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
