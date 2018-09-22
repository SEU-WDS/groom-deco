package org.wds.metamap;

import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import java.util.List;

public class APITest {

    public static void main(String[] args) {

        MetaMapApi api = new MetaMapApiImpl();
        api.setOptions("-y");
        String terms = "laboratory culture";
        List<Result> resultList = api.processCitationsFromString(terms);
        for (Result s : resultList) {
        }
    }
}
