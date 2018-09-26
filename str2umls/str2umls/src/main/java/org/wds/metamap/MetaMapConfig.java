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
        //theOption.add("-C");    // use relaxed model
        theOption.add("-z");    // use term processing
        theOption.add("-a");    // allow Acronym/Abbreviation variants
//        theOption.add("-b");    // compute/display all mappings
        api.setOptions(theOption);
        return api;
    }
}
