package org.wds.metamap;

import gov.nih.nlm.nls.metamap.*;

import java.io.*;
import java.util.*;
import java.util.Map;

public class TextProcess {

    private MetaMapApi api = MetaMapConfig.config();

    /**
     * str2umls_concept_list
     * @param text sentence
     * @return cui list
     * @throws Exception
     */
    public List<String> Sentence2CUI(String text)
            throws Exception {
        List<String> cuiList = new ArrayList<String>();
        if (text.trim().length() > 0) {
            List<Result> resultList = api.processCitationsFromString(text);
            for (Result result : resultList) {
                if (result != null) {
                    for (Utterance utterance : result.getUtteranceList()) {
                        for (PCM pcm : utterance.getPCMList()) {
                            for (Mapping map : pcm.getMappingList()) {
                                for (Ev mapEv : map.getEvList()) {
                                    cuiList.add(mapEv.getConceptId());
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(text + " have been processed...");
        return cuiList;
    }

    public void IteratorMap(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> entryIterator = map.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, String> entry = entryIterator.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    public void IteratorList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print("cui index " + i + " is : ");
            System.out.println(list.get(i));
        }
    }

    /**
     * get cui list from file content
     * and write to csv file
     * csv line format:
     * sentence , cui_list such as "G0010021|G0010023|G0010024"
     * @param file_name file location
     * @throws Exception
     */
    public void File2CUI(String file_name) throws Exception {
        File file = new File(file_name);
        String name = file.getName();
//        String name = file_name.substring(file_name.trim().lastIndexOf("/") + 1);
        String location = file.getAbsolutePath();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file_name));
            String line;
            List<String> cuiList;
            Map<String, String> str_and_cui = new HashMap<String, String>();
            while ((line = br.readLine()) != null) {
                cuiList = this.Sentence2CUI(line);
                if (cuiList.size() > 0) {
                    str_and_cui.put(line, String.join("|", cuiList));
                } else {
                    str_and_cui.put(line, "NULL");
                }
            }
            br.close();
            file = null;
            name = location + "/" + name + ".csv";
            CSVTools.write(name, str_and_cui);
        } catch (Exception e) {
            System.out.println("File2CUI Failed, the reason is : " + e.getMessage());
        }
    }

    /**
     * get cui list from files
     * @param dir_path files dir
     */
    public void Files2CUI(String dir_path) {
        String file_path = "";
        try {
            File file = new File(dir_path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        file_path = dir_path + "/" + f.getName();
                        this.File2CUI(file_path);
                    }
                }
                files = null;
            } else {
                System.out.println("the dir_path is invalid");
            }
            file = null;
        } catch (Exception e) {
            System.out.println("Files2CUI failed, the reason is : " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String testStr = "75% reached  the  target  dose of  16 mg,  a dose  shown  to";
        TextProcess tp = new TextProcess();
        List<String> cuiList = tp.Sentence2CUI(testStr);
        System.out.println("cuiList size is : " + cuiList.size());

    }
}
