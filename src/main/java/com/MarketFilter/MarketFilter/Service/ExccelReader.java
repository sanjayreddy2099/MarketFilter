package com.MarketFilter.MarketFilter.Service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExccelReader {
    public List<String> readStockSymbols() {
        List<String> stockSymbols = new ArrayList<>();
        try {
           
            ClassPathResource resource = new ClassPathResource("Book1.xlsx");
            try (InputStream inputStream = resource.getInputStream();
                 Workbook workbook = new XSSFWorkbook(inputStream)) {

                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        stockSymbols.add(cell.getStringCellValue());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockSymbols;
    }
}
