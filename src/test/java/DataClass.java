

import com.opencsv.CSVReader;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;



public class DataClass {
    private static XSSFSheet ExcelWSheet;

    private static XSSFWorkbook ExcelWBook;

    private static XSSFCell cell;

    private static XSSFRow Row;


    public static Object[][] getTableArray(String FilePath, String SheetName) throws NullPointerException {

        String[][] tabArray = null;


        try {
            int startRow = 1;
            int startCol = 0;
            int ci, cj;
            ci = 0;

            FileInputStream ExcelFile = new FileInputStream(FilePath);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(SheetName);
            int totalRows = ExcelWSheet.getLastRowNum();

            // you can write a function as well to get Column count
            int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
            tabArray = new String[totalRows][totalCols];

            for (int i = startRow; i <= totalRows && ci <= totalRows; i++, ci++) {
                cj = 0;

                for (int j = startCol; j < totalCols; j++, cj++) {

                    tabArray[ci][cj] = getCellData(i, j);

                    //System.out.println(tabArray[ci][cj]);

                }

            }

        } catch (FileNotFoundException e) {

            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();

        } catch (IOException e) {

            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();

        }

        return (tabArray);

    }

    public static String getCellData(int RowNum, int ColNum) throws NullPointerException {
        String CellValue = null;

        try {

            cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            @SuppressWarnings("deprecation")
            int dataType = cell.getCellType();
            String CellData = cell.getRawValue();
            if (dataType == 3) {

                return "";

            } else if (dataType == cell.CELL_TYPE_STRING) {

                CellValue = cell.getStringCellValue();

            } else if (CellData.contains("E-2")) {

                Double cellDatafloat = Double.parseDouble(CellData);
                CellValue = cellDatafloat.toString();
                //System.out.println(CellValue);
            } else {

                CellValue = CellData;
            }
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        return CellValue;

    }

    public static Object[][] getCSVData(String path) {

        String[][] dataSet = null;

   /* Reader reader = new FileReader(path);
    CSVReader csvreader = new CSVReader(reader);
    List<String[]> list = null;
    try {
        list = csvreader.readAll();

    } catch (IOException e) {
        e.printStackTrace();

    } */

        BufferedReader bufferedReader = null;
        String value;
        int count = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            while ((value = bufferedReader.readLine()) != null) {
                count++;
            }
          //  System.out.println(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        BufferedReader newReader = null;
        try {
            newReader = new BufferedReader(new FileReader(path));
            String line = null;
            int k = 0;
            dataSet = new String[count-1][10];
            while ((line = newReader.readLine()) != null) {

                String[] words = line.split(",");
                for (int i = 0; i < words.length; i++) {
                    if (k == 0) {
                     break;
                    }
                    dataSet[k - 1][i] = words[i];
                   // System.out.println(dataSet[k][i]);

                }

                k++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (newReader != null) {
                try {
                    newReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(count);



    /*
    int j=0;
    Iterator<String[]> ite= list.iterator();
    System.out.println(count);

    System.out.println(count);
    dataSet=new String[count][9];
    while(ite.hasNext()){
        String[] data = ite.next();
        System.out.println(data.length);
        for(int i=0; i<data.length; i++){
            System.out.println(data[i]);
            dataSet[j][i] = data[i];

        }

        j++;

    } */
        return dataSet;
    }
}

