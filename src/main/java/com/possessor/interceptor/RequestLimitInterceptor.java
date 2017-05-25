package com.possessor.interceptor;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rpiotrowicz on 2017-05-18.
 */

@Async
public class TrafficLoggerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pathToFile = "src/resources/stats/TrafficStatistic.txt";

        Path pathToStatistic = Paths.get(pathToFile);

        //read
        String reqClientHost = request.getRemoteHost();

        String csvClientHost = "";
        int clientHostNumber = 0;


        //operation


        //write

        try (BufferedReader reader = Files.newBufferedReader(pathToStatistic, Charset.forName("UTF-8"));
             CSVReader csvReader = new CSVReader(reader, ',');
             BufferedWriter writer = Files.newBufferedWriter(pathToStatistic, Charset.forName("UTF-8"));
             CSVWriter csvWriter = new CSVWriter(writer, ',')) {

            String[] line = {};
            List<String> linesWords;
            List<String[]> allContent = new LinkedList<>();

            while ((line = csvReader.readNext()) != null) {

                    linesWords =  Arrays.asList(line);

                if (linesWords.contains(reqClientHost)) {

                    clientHostNumber = Integer.parseInt(linesWords.get(1));
                    clientHostNumber += 1;

                    linesWords.set(1, String.valueOf(clientHostNumber));

                    allContent.add(linesWords.toArray(new String[1]));

                    break;
                }else {

                }
            }



            csvWriter.writeAll(allContent);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
