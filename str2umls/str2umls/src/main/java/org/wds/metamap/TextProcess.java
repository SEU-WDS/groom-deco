package org.wds.metamap;

import gov.nih.nlm.nls.metamap.*;

import java.io.*;
import java.util.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcess {

    private static MetaMapApi api;

    public TextProcess() {
        api = new MetaMapApiImpl();
        List<String> theOptions = new ArrayList<String>();
        theOptions.add("-y"); // turn on Word Sense Disambiguation
        theOptions.add("-i");
        theOptions.add("-l");
        for (String opt : theOptions)
            api.setOptions(opt);
    }

    /**
     * str2umls_concept_list
     *
     * @param text sentence
     * @return cui list
     * @throws Exception
     */
    public List<String> Sentence2CUI(String text)
            throws Exception {
        text = WashText(text);
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
        return cuiList;
    }

    public String WashText(String term) {
        term = term.replaceAll("'", "");
        term = term.replaceAll("^ - ", "");
        term = term.replaceAll("\"", "");
        term = term.replaceAll("\\?", " ");
        term = term.trim();
        term = term.replace("\n", "");
        term = term.replace("&", " and ");
        //normalizing input for just string that are processable by MetaMap
        String patternString = "[a-zA-Z0-9 +-=~\\/()\\[\\]@\"\'.%^#&\\*{};:]*";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(term);
        String sa = "";
        while (matcher.find()) {
            sa += matcher.group();
        }
        term = sa;
        term = term.replaceAll("  ", " ");
        term = term.replaceAll("   ", " ");
        term = term.replaceAll("  ", " ");
        term = term.replaceAll("  ", " ");
        term = term.replaceAll("\\d", "X");

        String[] terms = term.split(" ");
        term = "";
        for(String terma:terms)
        {
            terma=terma.trim();
            term +=" "+terma;
        }
        term = term.replaceFirst(" ", "");
        term = term.trim();
        return term;
    }

    /**
     * get cui list from file content
     * and write to csv file
     * csv line format:
     * sentence , cui_list such as "G0010021|G0010023|G0010024"
     *
     * @param file_name file location
     * @throws Exception
     */
    public void File2CUI(String file_name) throws Exception {
        File file = new File(file_name);
        String name = file.getAbsolutePath().split("\\.")[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(file_name));
            String line;
            List<String> cuiList;
            Map<String, String> str_and_cui = new HashMap<String, String>();
            int i = 0;
            while ((line = br.readLine()) != null) {
                i++;
                cuiList = this.Sentence2CUI(line);
                System.out.println("the " + i + " th sentences have been resolved.");
                if (cuiList.size() > 0) {
                    str_and_cui.put(line, "|||" + String.join("|", cuiList));
                } else {
                    str_and_cui.put(line, "|||" + "NULL");
                }
            }
            br.close();
            name = name + ".csv";
            CSVTools.write(name, str_and_cui);
        } catch (Exception e) {
            System.out.println("File2CUI Failed, the reason is : " + e.getMessage());
        }
    }

    /**
     * get cui list from files
     *
     * write to csv file failed, the reason is : /home/stu/MetaMap/str2umls/pico_dir/O.txt/O.txt.csv (Not a directory)
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
            } else {
                System.out.println("the dir_path is invalid");
            }
        } catch (Exception e) {
            System.out.println("Files2CUI failed, the reason is : " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {

        TextProcess tp = new TextProcess();
        if (args[0].equals("s")) {
            String testStr = args[1];
            List<String> cuiList = tp.Sentence2CUI(testStr);
            System.out.println("cuilist size is :" + cuiList.size());
        }
        if (args[0].equals("fs")) {
            tp.Files2CUI(args[1]);
        }
    }
}
