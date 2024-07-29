package Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExccelReader {
	public List<String> readStockSymbols(String filePath){
		
		List<String> stockSymbols = new ArrayList<>();
		try(FileInputStream fis = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fis)){
			Sheet sheet = workbook.getSheetAt(0);
			for(Row row: sheet) {
				Cell cell = row.getCell(0);
				if(cell != null) {
					stockSymbols.add(cell.getStringCellValue());
				}
				
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return stockSymbols;
		
	}

}
