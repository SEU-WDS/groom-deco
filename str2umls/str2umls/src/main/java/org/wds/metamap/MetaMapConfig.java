package org.wds.metamap;

import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;

import java.util.ArrayList;
import java.util.List;

public class MetaMapConfig {

    public static MetaMapApi config() {
        MetaMapApi api = new MetaMapApiImpl();
        List<String> theOption = new ArrayList<String>();
        theOption.add("-y");    // use word_sense_disambiguation
//        theOption.add("-A");    // use relaxed model
//        theOption.add("-K");    // ignore stop phrases.
//        theOption.add("-z");    // use term processing
//        theOption.add("-a");    // allow Acronym/Abbreviation variants
//        theOption.add("-b");    // compute/display all mappings
//        theOption.add("-r 10");
//        theOption.add("-k dsyn");
        theOption.add("-i");
        theOption.add("-l");
        for (String opt:theOption)
            api.setOptions(opt);
        return api;
    }
}
