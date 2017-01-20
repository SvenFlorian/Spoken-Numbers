package se.cth.minges.spokennumbers.core.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Reads data from an excel-file.
 * This class was derived by the excel-tutorial of
 * Lars Vogel (www.vogella.com), which is FREE to use.
 * 
 * @author Florian Minges
 */
public class ReadExcel {

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getInputFile() {
		return inputFile;
	}

	/**
	 * Returns null if template does not exist, OR throws exception.
	 * 
	 * @return
	 * @throws IOException
	 */
	public Workbook readTemplate() throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
		} catch (BiffException e) {
			e.printStackTrace();
		}

		return w;
	}
	
	@Deprecated
	public String readCell(String fileName, int col, int row) throws IOException {
		String s = null;
		try {
			File inputWorkbook = new File(fileName);
			Workbook w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			Cell cell = sheet.getCell(col, row);
			s = cell.getContents();
		} catch (BiffException e) {
			e.printStackTrace();
		}

		return s;
	}

	public List<Integer> readNumbers(String fileName) throws IOException {
		List<Integer> numbers = new ArrayList<Integer>();
		File inputWorkbook = new File(fileName);
		Workbook w = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);

			boolean stopReading = false;
			int colLimit = ExcelConstants.NBRS_PER_ROW + ExcelConstants.START_COLUMN_INDEX;
			int rowLimit = (ExcelConstants.ROWS * ExcelConstants.LINES_PER_ROW) + 
																	ExcelConstants.START_ROW_INDEX;
			
			
			for (int row = ExcelConstants.START_ROW_INDEX; row < rowLimit; 
									row += ExcelConstants.LINES_PER_ROW) {
				for (int col = ExcelConstants.START_COLUMN_INDEX; col < colLimit; col++) { 
					Cell cell = sheet.getCell(col, row);
					try {
						Integer number = Integer.parseInt(cell.getContents());
						numbers.add(number);
					} catch (NumberFormatException nfe) {
						//Cell contains no number
						stopReading = true;
						break;
					}
				}
				if (stopReading)
					break; 
			}
		} catch (BiffException be) {
			be.printStackTrace();
		} 

		return numbers;
	}


//	public static void main(String[] args) throws IOException {
//		ReadExcel test = new ReadExcel();
//		String userHome = System.getProperty("user.dir");
//		test.setInputFile(userHome + System.getProperty("file.separator") + "template_sheet.xls");
//		// test.read();
//		List<Integer> list = test.readNumbers(test.getInputFile());
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i));
//			if ((i+1) % 30 == 0)
//				System.out.print(System.getProperty("line.separator"));
//		}
//	}

}
