package com.revature.search;
//
//import javax.persistence.*;
//import java.util.Locale;
//
//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//abstract public  class SearchEntity implements Searchable {
//
//    @Column(name = "key")
//    private String key;
//    @Column(name = "label")
//    private String label;
//
//    public SearchEntity() { };
//
//    @Override
//    public String getLabel() {
//        return this.label;
//    };
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    @Override
//    public String getKey() {
//        return this.key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public <T> void setKey(T id) {
//        this.key = id.toString();
//    }
//}
