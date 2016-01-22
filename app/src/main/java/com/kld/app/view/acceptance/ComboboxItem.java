package com.kld.app.view.acceptance;

public class ComboboxItem{
    String key;
    String value;
    public ComboboxItem(){}
    public ComboboxItem(String key, String value){
        this.key = key;
        this.value = value;
    }
    public String toString() {
        return value;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
