package com.dzytsiuk.dbdeveloper.dao.customdb.mapper;

import com.dzytsiuk.dbdeveloper.entity.Data;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class RowHandler extends DefaultHandler {

    private Data data;
    private int depth;
    private List<String> row;

    RowHandler(Data data) {
        this.data = data;

    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) {
        depth++;

        if (depth == 2) {
            row = new ArrayList<>();
        }
        if (depth == 3) {
            List<String> columnNames = data.getColumnNames();
            if (columnNames == null) {
                columnNames = new ArrayList<>();
                data.setColumnNames(columnNames);
            }
            if (!columnNames.contains(qName)) {
                columnNames.add(qName);
            }

            if (data.getData() == null) {
                data.setData(new ArrayList<>());
            }

        }

    }

    public void characters(char ch[], int start, int length) {
        if (depth == 3) {
            row.add(new String(ch, start, length));
        }

    }

    public void endElement(String uri, String localName, String qName) {

        if (depth == 2) {
            data.getData().add(row);
        }
        depth--;
    }
}
