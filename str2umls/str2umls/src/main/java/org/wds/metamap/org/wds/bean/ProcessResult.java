package org.wds.metamap.org.wds.bean;

public class ProcessResult<K, V> {

    private final K Phrase2CuiMap;
    private final V CuiList;

    public ProcessResult(K Phrase2CuiMap, V CuiList) {
        this.Phrase2CuiMap = Phrase2CuiMap;
        this.CuiList = CuiList;
    }

    public K getPhrase2CuiMap() {
        return Phrase2CuiMap;
    }

    public V getCuiList() {
        return CuiList;
    }

}
