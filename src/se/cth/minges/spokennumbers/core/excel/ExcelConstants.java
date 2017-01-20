package se.cth.minges.spokennumbers.core.excel;

/**
 * Some Excel-constants.
 * @author Florian Minges
 */
public interface ExcelConstants {
	public final int START_COLUMN_INDEX = 2;
	public final int START_ROW_INDEX = 1;
	public final int NBRS_PER_ROW = 30;
	public final int ROWS = 25;
	public final int LINES_PER_ROW = 2;
	
	/** Name of the xls-template. */
	public final String TEMPLATE = System.getProperty("user.dir") +
			System.getProperty("file.separator") + "resources" + 
			System.getProperty("file.separator") + "template_sheet.xls";
	
	/** Name of xls-output file. */
	public final String OUTPUT = System.getProperty("user.dir") + 
			System.getProperty("file.separator") + "key_sheet.xls";
}
