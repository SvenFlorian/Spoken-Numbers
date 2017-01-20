package se.cth.minges.spokennumbers.core.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Writes data to an excel-file.
 * This class was derived by the excel-tutorial of
 * Lars Vogel (www.vogella.com), which is FREE to use.
 * 
 * @author Florian Minges
 */
public class WriteExcel {
	
	public void writeNumbersToFile(String fileName, List<Integer> numbers) throws IOException, 
															RowsExceededException, WriteException {
		try {
			File file = new File(ExcelConstants.TEMPLATE);
			Workbook workbook = Workbook.getWorkbook(file);
			WritableWorkbook writable = Workbook.createWorkbook(new File(fileName), workbook);
			WritableSheet excelSheet = writable.getSheet(0);
			fillNumbers(excelSheet, numbers);
			writable.write();
			writable.close();
			
		} catch (BiffException e) {
			e.printStackTrace();
		}
		
	}
	
	private void fillNumbers(WritableSheet sheet, List<Integer> numbers) {
		for (int index = 0; index < numbers.size(); index++) {
			int col = (index % ExcelConstants.NBRS_PER_ROW) + ExcelConstants.START_COLUMN_INDEX;
			int row = (ExcelConstants.LINES_PER_ROW * (index / ExcelConstants.NBRS_PER_ROW))
																+ ExcelConstants.START_ROW_INDEX;
			Number number = new Number(col, row, numbers.get(index));
			
			try {
				sheet.addCell(number);
				
				//format cell
				sheet.mergeCells(col, row, col, row+1);
				WritableCell cell = sheet.getWritableCell(col, row);
				formatCell(cell, true); 
				
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		try {
			int nbrOfRows = 1 + ((numbers.size() - 1) / ExcelConstants.NBRS_PER_ROW);
			addRowLabels(sheet, nbrOfRows);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	private void addRowLabels(WritableSheet sheet, int nbrOfLabels) throws RowsExceededException, 
																					WriteException {
		int col = ExcelConstants.START_COLUMN_INDEX + ExcelConstants.NBRS_PER_ROW;
		
		// Create a cell format for a bold Arial 10 point font
		WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		WritableCellFormat arial10format = new WritableCellFormat (arial10font); 
		
		for (int index = 0; index < nbrOfLabels; index++) {
			int row = ExcelConstants.START_ROW_INDEX + (index * ExcelConstants.LINES_PER_ROW);
			String rowText = "Row " + (index + 1);
			Label rowLabel = new Label(col, row, rowText, arial10format);
			sheet.addCell(rowLabel);
			
			//format cell
			sheet.mergeCells(col, row, col, row+1);
			WritableCell cell = sheet.getWritableCell(col, row);
			formatCell(cell, false);
		}
		
	}
	
	private void formatCell(WritableCell cell, boolean border) throws WriteException {
		WritableCellFormat format = new WritableCellFormat();
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setAlignment(Alignment.CENTRE);
		if (border) {
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
		}
		
		cell.setCellFormat(format);
	}
}
