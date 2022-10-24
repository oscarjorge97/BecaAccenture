package com.oyataco.converToBatch.processor;

import com.oyataco.converToBatch.model.DataMonth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// clase implementando interfaz 'ItemProcessor' con entrada y salida 'DataMonth'
public class DataMonthItemProcessor implements ItemProcessor<DataMonth,DataMonth> {

    //variable LOG para escribir cosas en consola
    private static final Logger LOG= LoggerFactory.getLogger(DataMonthItemProcessor.class);

    /**
     * En este metodo podemos modificar la informacion que procesa nuestro Batch
     * @param dataMonth
     * @return
     * @throws Exception
     */
    @Override
    public DataMonth process(DataMonth dataMonth) throws Exception {



        return null;
    }
}
