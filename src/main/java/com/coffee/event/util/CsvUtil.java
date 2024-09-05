package com.coffee.event.util;

import com.coffee.event.entity.User;
import com.coffee.event.exceptions.CoffeeException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvUtil {

    public void organizeCsv(List<String> csvContent, List<User> users) {
        log.info("Got request for CSV format");
        for (User user : users) {
            String format = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                    user.getId(),
                    user.getUserEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEventId(),
                    user.getEmailReceivedAt(),
                    user.getCreatedAt(),
                    user.getUpdatedAt());
            csvContent.add(format);
        }
    }

    public void downloadCSVFile(String headers, List<String> csvContent, HttpServletResponse response, String fileName, String fileExtension) {
        try {
            response.setContentType("text/csv");
            String fullFileName = String.format("%s_%s.%s", fileName, getCurrentDate(), fileExtension);

            response.setHeader("Content-Disposition", "attachment; filename=\"" + fullFileName + "\"");

            OutputStream outputStream = response.getOutputStream();
            writeCSV(outputStream, csvContent, headers);

            log.info("CSV file stored successfully as " + fullFileName);
        } catch (Exception ex) {
            log.error("An error occurred while downloading the CSV file: {}", ex.getMessage());
            throw new CoffeeException(ErrorConstants.NOT_FOUND_ERROR_CODE, ErrorConstants.NOT_FOUND_ERROR_MESSAGE);
        }
    }


    private static void writeCSV(OutputStream outputStream, List<String> dataList, String headers) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            log.info("Writing CSV for user data");
            writer.write(headers);
            writer.newLine();

            for (String userDetails : dataList) {
                writer.write(userDetails);
                writer.newLine();
            }

            writer.flush();
        } catch (Exception e) {
            log.error("An error occurred while writing the CSV file: {}", e.getMessage());
            throw new CoffeeException(ErrorConstants.NOT_FOUND_ERROR_CODE, ErrorConstants.NOT_FOUND_ERROR_MESSAGE);
        }
    }

    private static String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

}
