package com.gamez.zombiecampus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;

/**
 * Provides a set of methods for reading and writing CSV data files through libgdx.
 * @author Greg Breard
 * 
 * Note: This object assumes the first column is always an integer based id field.
 */
public class CSV {
	
	private final String SPLIT_BY = ",";
	
	// Stores the row and column data
	private HashMap<String, Integer> m_columns = new HashMap<String, Integer>();
	private HashMap<Integer, String[]> m_rows = new HashMap<Integer, String[]>();
		
	/**
	 * Initializes a new instance of a CSV object.
	 * @param file : the FileHandle containing file data.
	 */
	public CSV(FileHandle file) {
		readFile(file);
	} // end CSV

	/**
	 * Adds a row to the file.
	 * @param row : the array of row data to be added. 
	 * @throws Exception Duplicate id.
	 */
	public void AddRow(String[] row) throws Exception {
		// Get the row id
		Integer id = Integer.parseInt(row[0]);
		
		// Check if the row id already exists in the table
		if (m_rows.containsKey(id)){
			throw new Exception("A row with id " + id + " already exists in the file.");
		} // end if
		
		// Add the row
		m_rows.put(id, row);
	} // end AddRow
	
	/**
	 * Deletes a row from the file.
	 * @param row : the id of row data to be deleted. 
	 * @throws Exception Row not found.
	 */
	public void DeleteRow(String[] row) throws Exception {
		// Get the row id
		Integer id = Integer.parseInt(row[0]);
		
		// Check if the row id already exists in the table
		if (m_rows.containsKey(id)){
			throw new Exception("No row found matching id " + id + ".");
		} // end if
		
		// Remove the row
		m_rows.remove(id);		
	} // end DeleteRow
	
	/**
	 * Updates the CSV file.
	 * @param file : the FileHandle containing file data.
	 */
	public void Save(FileHandle file){
		writeFile(file);
	} // end Save
	
	/**
	 * Returns an array of all the row data row a specified column.
	 * @param column : the name of the column to get the data for.
	 * @return String[] containing data.
	 */
	public String[] GetColumn(String column) {
		String[] data = new String[GetRowCount()];
		
		// Copy data to return array
		for (int i = 0; i < GetRowCount(); i++) {
    		data[i] = GetRowCol(i, column);
    	} // end for
		
		// Return the column array
		return data;
	} // end GetRow
	
	/**
	 * Returns the number of columns in the file.
	 * @return Integer
	 */
	public Integer GetColumnCount() {
		return m_columns.size();		
	} // end GetColumnCount
	
	/**
	 * Returns an array of all the column data in a specified row.
	 * @param row : the index of the row to get the data for.
	 * @return String[] containing data.
	 */
	public String[] GetRow(Integer row) {
		// Return the row array		
		return m_rows.get(row);
	} // end GetRow
	
	/**
	 * Returns data for the specified column in a specified row.
	 * @param row : the index of the row to get the data for.
	 * @param column : the name of the column to get the data for.
	 * @return String containing data.
	 */
	public String GetRowCol(Integer row, String column) {
		String[] data = GetRow(row);		
		return data[m_columns.get(column)];
	} // end GetRowCol
	
	/**
	 * Returns the number of rows in the file.
	 * @return Integer
	 */
	public Integer GetRowCount() {
		return m_rows.size();		
	} // end GetRowCount
	
	/**
	 * Reads the data stored in a CSV file.
	 * @param file : the FileHandle containing file data.
	 * @param splitBy : the character used to divide the columns.
	 */
	private void readFile(FileHandle file){
		// Initialize the reader
		BufferedReader reader = null;
		String line = "";
		Boolean firstRow = true;
		
		try {
			// Read the file
			reader = new BufferedReader(file.reader());
			
			// Read each line from the file
			while ((line = reader.readLine()) != null) {	 
			   	// Split the columns
				String[] row = line.split(SPLIT_BY);
				
				// Check if this is the first row, which contains the column names
				if (firstRow) {
					// Get the columns
			    	for (int i = 0; i < row.length; i++) {
			    		m_columns.put(row[i], i);
			    	} // end for
										
					firstRow = false;
				} else {
					// Get the row
					m_rows.put(Integer.parseInt(row[0]), row);
			    } // end if				 
			}// end while
		} catch (Exception e) {
			e.printStackTrace();		
		} finally {
			// Make sure to close the reader
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				} // end catch
			} // end if
		} // end finally
	} // end readFile
		
	/**
	 * Writes the data to a CSV file.
	 * @param file : the FileHandle containing file data.
	 * @param splitBy : the character used to divide the columns.
	 */
	private void writeFile(FileHandle file) {
		// Initialize the writer
		BufferedWriter writer = null;
		try {
			
			// Create the writer
			writer = new BufferedWriter(file.writer(false));
			String line = "";
			
			// Get the columns
			String[] columns = m_columns.keySet().toArray(new String[0]);
			
			// Copy the column data
			for (int i = 0; i <= columns.length; i++) {
				// need a second loop to make sure they are output in correct order
				for (int j = 0; j < columns.length; j++) {
					if  (m_columns.get(columns[j]) == i) {
						line += columns[j];																	
						break;					
					} // end if										
				} // end for j
				
				// Add separator
				if (i < columns.length - 1)
					line += SPLIT_BY;	
	    	} // end for i
						
			// Write the column names to the file
			writer.write(line);
			writer.newLine();
			
			// Get the row ids
			Integer[] rows = m_rows.keySet().toArray(new Integer[0]);
			
			// Copy the rows
			for (int i = 0; i < rows.length; i++) {
				// Reset the line
				line = "";
				
				// Get the row
				String[] row = m_rows.get(rows[i]);
				
				// Copy the row data
				for (int j = 0; j < row.length; j++) {
					line += row[j];
	 
					if (j < row.length)
						line += SPLIT_BY;	    		
		    	} // end for	
				
				// Write the column names to the file
				writer.write(line);
				writer.newLine();
	    	} // end for
		} catch (Exception e) {
				e.printStackTrace();		
		} finally {
			// Make sure to close the writer
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				} // end catch
			} // end if
		} // end finally
	} // end writeFile
	
} // end CSV
