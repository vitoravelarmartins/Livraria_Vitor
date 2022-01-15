package br.com.livraria.livrariaV.Services;

import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalcDate {
    public Integer calcDate(@PathVariable("dateSend") String dateSend) {

        Date dateToday = new Date(System.currentTimeMillis());
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date dateConvert = null;
        System.out.println(dateToday);
        try {
            dateConvert = formatterDate.parse(dateSend);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Essa data que estou convertendo "+dateSend);
        return dateConvert.compareTo(dateToday); // -1 = Atrasado, 0 = Entrega Hoje,  1 = Dentro do prazo
    }

    public String dateNow(){
        Date dateToday = new Date(System.currentTimeMillis());
        return "Bola";
    }
}
