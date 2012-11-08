package jyhuang;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sun.media.sound.InvalidFormatException;

/**
 * 
 * Excel analyzer
 * 
 * @author jfli
 * @Create date 2012-09-12
 */
public class ExcelAnalyzer {
	
	private final static Log log = LogFactory.getLog(ExcelAnalyzer.class);
	
	private static String[] cellString = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };
	
	private Workbook excelFile = null; // excel file
	
	private Sheet excelSheet = null; //excel sheet

	public ExcelAnalyzer(InputStream fileInputStream) throws IOException, InvalidFormatException, OpenXML4JException {
		excelFile = WorkbookFactory.create(fileInputStream);
		if (null == excelFile) {
			throw new OpenXML4JException("Create workbook error");
		}
		excelSheet = excelFile.getSheetAt(0);
		if (null == excelSheet) {
			throw new OpenXML4JException("Create sheet error");
		}
	}
	
	/*
	 * move to another sheet
	 */
	public boolean moveSheetTo(int sheetIndex) {
		if ((0 <= sheetIndex) && (null != excelFile.getSheetAt(sheetIndex))) {
			excelSheet = excelFile.getSheetAt(sheetIndex);
			return true;
		}
		
		return false;
	}

	/*
	 * get number of rows
	 */
	public int getRowNum() {
		return excelSheet.getPhysicalNumberOfRows();
	}
	
	/*
	 * get string value from cell
	 */
	public String getStringValue(int rowIndex, int cellIndex) {

		String result = null;
		
		Row row = excelSheet.getRow(rowIndex);
		if (checkIfCellIsNull(row, cellIndex)) {
			return null;
		}
		
		Cell cell = row.getCell(cellIndex);
		if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
			result = cell.getStringCellValue();
		} else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
			result = "" + cell.getBooleanCellValue();
		} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			result = "" + (int) cell.getNumericCellValue();
		} else if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
			result = "";
		}
		if (result != null) {
			result = result.trim();
		}
		return result;
	}
	
	/*
	 * get date value from cell
	 */
	public Date getDateValue(int rowIndex, int cellIndex, SimpleDateFormat format) {
    	
    	Date date = null;
    		
		Row row = excelSheet.getRow(rowIndex);
		if (checkIfCellIsNull(row, cellIndex)) {
			return null;
		}
		
		Cell cell = row.getCell(cellIndex);
		
		try {
			date = cell.getDateCellValue();
		} catch (IllegalStateException e) {
			log.error("Cell type is String", e);
		} catch (NumberFormatException e) {
			log.error("Cell value isn't a parsable double", e);
		}
        return date;
    }
	
    /*
     * check if cell is null
     */
    private boolean checkIfCellIsNull(Row row, int cellNum) {
        return row == null || row.getCell(cellNum) == null;
    }
    
    public static String getCellColumn(int cellIndex) {
		if (cellIndex > 25) {
			return null;
		}
		
		return cellString[cellIndex];
	}
	
}
