package kz.talipovsn.rates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// СОЗДАТЕЛЬ КОТИРОВОК ВАЛЮТ
public class RatesReader {

    private static final String BASE_URL = "http://www.gorodpavlodar.kz/Valuta.html"; // Адрес с котировками

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getRatesData() {
        StringBuilder data = new StringBuilder();
        try {
            Document doc = Jsoup.connect(BASE_URL).timeout(5000).get(); // Создание документа JSOUP из html
            data.append("Курсы валюты:\n"); // Считываем заголовок страницы
            Elements e = doc.select("div.corner"); // Ищем в документе "<div class="exchange"> с данными о валютах
            Elements tables = e.select("table"); // Ищем таблицы с котировками
            Element table = tables.get(0); // Берем 1 таблицу
            Elements row = table.select("tr");
            data.append("          USD:                 EUR:                RUB:\n          куп/прод:        куп/прод:    куп/прод:\n");
            // Цикл по строкам таблицы
                // Цикл по столбцам таблицы
            for (int i = 2; i < 5; i++){
                Element row1 = row.get(i);
                for (Element col : row1.select("td")) {
                    Elements b1 = col.select("b");
                    Elements h4 = col.select("h4");//
                    data.append(b1.text() + h4.text() + "\t");// Считываем данные с ячейки таблицы
                }
                data.append("\n");
            }
        } catch (Exception ignored) {
            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}