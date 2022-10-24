package com.oyataco.converToBatch;

import com.oyataco.converToBatch.listener.JobListener;
import com.oyataco.converToBatch.model.DataMonth;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration //indica que la clase en la que se encuentra contiene la configuracion principal del proyecto
@EnableBatchProcessing //al utilizar esta anotacion estamos proporcionando un JobRepository a nuestra aplicacion
public class BatchConfiguration {

    //dos dependencias
    @Autowired /*inyecta la dependencia del objeto implicitamente, cuando se pasen los valores de los campos con el nombre de la propiedad
    Spring asignara automaticamente los campos con los valores que se pasan*/
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    /**
     * el metodo reader,un batch se compone de uno o muchos Steps, y cada Step se compone de un ItemReader,ItemWriter y/p Processor
     * FlatFileItemReader de tipo 'DataMonth'
     * este metodo obtiene de nuestro archivo csv los datos y asigna a cada una de las columnas la propiedad de nuestra clase
     * @return newFliteFileItemReaderBuilder de tipo persona con su configuracion
     */
    @Bean //se utiliza en las clases que tengan la anotacion Configuration para crear beans Spring
    public FlatFileItemReader<DataMonth> reader(){
        return new FlatFileItemReaderBuilder<DataMonth>()
                //nombre del ItemReader
                .name("personaItemReader")
                //Indicamos cual es el recurso de nuestra fuente, es decir donde esta el archivo con los datos que vamos a importar
                .resource(new ClassPathResource("taskOscarCSV.csv"))
                //indicamos que es delimitado por comas
                .delimited()
                //indicamos los nombres de las propiedades, en el mismo orden e iguales a las propiedades de la clase
                .names(new String[]{"anio","mes","totalDias","primerDia"})
                //indicamos cual es la clase a la cual deben asignarse las propiedades
                .fieldSetMapper(new BeanWrapperFieldSetMapper<DataMonth>(){{
                    setTargetType(DataMonth.class);
                }})
                //para construirlo
                .build();
    }

    /**
     * el metodo writer se encargará de escribir en nuestra base de datos embebida, cada uno de los registros leídos por SpringBatch
     * En nuestro caso es un JdbcBatchItemWriter, pero tambien podria ser un FileWriter
      * @param dataSource el cual sera inyectado por Spring Framework al momento de ejecucion de nuestro Batch
     * @return new JdbcBatchItemWriterBuilder de tipo 'DataMonth' con su configuracion
     */
    @Bean
    public JdbcBatchItemWriter<DataMonth> writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<DataMonth>()
                //las propiedades de nuestra clase DataMonth van a ser guardadas en nuestra base de datos
                //mejor dicho, que tome los parametros de la clase DataMonth y las guarde como parametros al insert que definimos despues

                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                //consulta insert donde introducimos los datos
                .sql("insert into dataMonth (year, month, total_days, first_day) values (:anio, :mes, :totalDias, :primerDia)")
                //indicamos cual es el datasource que recibimos como parametro
                .dataSource(dataSource)
                //construimos
                .build();
    }

    /**
     * Metodo de Job que será la configuración de nuestro Job en la que indicaremos cual es el Step que va ejecutar, el listener..
     * @param listener para indicarle cual es el listener que se ejecutará en caso de que el estado de nuestro Job cambie
     * @param step1 indicamos el step que tendremos más adelante
     * @return jobBuilderFactory con su configuracion
     */
    @Bean
    public Job importDataMonthJob(JobListener listener, Step step1){
        //usamos la primera dependencia que inyectamos en la clase y le damos el mismo nombre del metodo
        return jobBuilderFactory.get("importDataMonthJob")
                //definimos un incrementer debido a que los Jobs crean registro en memoria o base de datos y si tenemos varios Batch, cada uno tiene un id de ejecucion diferente
                .incrementer(new RunIdIncrementer())
                //definimos un listener
                .listener(listener)
                //definimos un step
                .flow(step1)
                .end().build();
    }

    /**
     * Metodo step1 el cual tendrá definido un reader, un writer y si se quiere un processor
     * @param writer recibirá nuestro writer como metodo
     * @return devuelve un Step usando la inyeccion stepBuilderFactory
     */
    @Bean
    public Step step1(JdbcBatchItemWriter<DataMonth> writer){
        //usamos la segunda dependencia que inyectamos en la clase y le damos el nombre del metodo
        return stepBuilderFactory.get("step1")
                //definimos el tamaño del lote de registros
                .<DataMonth,DataMonth>chunk(12)
                //le pasamos nuestro metodo reader
                .reader(reader())
                //le pasamos el parametro writer que recibe
                .writer(writer)
                .build();
    }

}
