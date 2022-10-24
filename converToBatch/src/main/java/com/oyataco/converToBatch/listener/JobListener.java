package com.oyataco.converToBatch.listener;

import com.oyataco.converToBatch.model.DataMonth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

//esta clase nos permitirá conocer el estado de ejecucion de nuestro Job
//Le añadimos la etiqueta 'Component' porque necesitaremos inyectar esta clase a un metodo de la configuracion del Spring Batch
@Component
public class JobListener extends JobExecutionListenerSupport {
//variable LOG para poder escribir cosas en la consola
    private static final Logger LOG= LoggerFactory.getLogger(JobListener.class);
    private JdbcTemplate jdbcTemplate;
    //hacemos la inyeccion de esta independencia

    @Autowired
    public JobListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //sobreescribimos afterJob para crear nuestra propia logica
    @Override
    public void afterJob(JobExecution jobExecution) {
        //si finaliza de forma correcta
        if (jobExecution.getStatus()== BatchStatus.COMPLETED){
            LOG.info("FINALIZÓ EL JOB!! Verifica los resultados:");

            //primero hacemos una consulta,y después mostramos estos resultados en la consola. rs(columna) row(fila)
            jdbcTemplate
                    .query("select year, month, total_days,first_day from dataMonth",
                    (rs,row) ->new DataMonth(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)))
                    .forEach(data ->LOG.info("Registro< "+data+" >"));

        }
    }
}
