package TripCo.Model;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by mjdun on 2/19/2017.
 */
public class CSVData {
    public CSVData(){
        columns = new HashMap<String, Column>();
        columnNames = new Vector<String>();
    }

    public void addColumn(String columnName ){
        columns.put(columnName.toLowerCase(), new Column(columnName.toLowerCase()));
        columnNames.add(columnName.toLowerCase());
    }

    public void addDataToColumn(String columnName, String data){
        columns.get(columnName.toLowerCase()).add(data);

        if(columns.get(columnName.toLowerCase()).getHeight() > height)
            height = columns.get(columnName.toLowerCase()).getHeight();
    }

    public String getValue(String columnName, int index){
        return columns.get(columnName.toLowerCase()).getDataAt(index);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return columns.size();
    }

    public void printData(){
        for(int i = 0; i < height; ++i) {

            for (String key : columns.keySet()) {
                System.out.print(columns.get(key).getDataAt(i));
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

    protected HashMap<String, Column> columns;

    public Vector<String> getColumnNames() {
        return columnNames;
    }

    protected Vector<String> columnNames;
    protected int height;
}