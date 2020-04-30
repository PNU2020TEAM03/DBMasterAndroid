package com.example.dbmasterandroid.ui.tableselect;

//어댑터 안에 들어갈 아이템 (테이블 클래스)
public class Table {
    String tableName;
    //String tableContent;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public String getTableTitle() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
