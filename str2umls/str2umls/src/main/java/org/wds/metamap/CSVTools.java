package org.wds.metamap;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class CSVTools {

    /**
     * read csv file
     * @param filePath file location
     */
    public static void read(String filePath){
        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath);
            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                System.out.println(csvReader.getRawRecord());
                // 读这行的某一列
                System.out.println(csvReader.get("Link"));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write to csv file
     * @param filePath file location
     * @param map input
     */
    public static void write(String filePath, Map<String, String> map) {
        try {
            // create csvWriter object
            CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("UTF-8"));
            // write header
            String[] headers = {"sentence","CUI_LIST"};
            csvWriter.writeRecord(headers);
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            String[] content = new String[2];
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                content[0] = entry.getKey();
                content[1] = entry.getValue();
                csvWriter.writeRecord(content);
            }
            csvWriter.close();
        } catch (IOException e) {
            System.out.println("write to csv file failed, the reason is : " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        String filepath = "G:/htest.csv";
        Map<String, String> map = new HashMap<String, String>();
        List<String> arr1 = new ArrayList<String>();
        arr1.add("as1");
        arr1.add("as2");
        arr1.add("as3");
        map.put("measure", String.join("|", arr1));
        List<String> arr2 = new ArrayList<String>();
        arr2.add("as4");
        arr2.add("as5");
        arr2.add("as6");
        map.put("heart attack", String.join("|", arr2));
        write(filepath, map);
    }
}
