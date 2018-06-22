package com.dzytsiuk.dbdeveloper.dao.customdb.mapper;

import com.dzytsiuk.dbdeveloper.entity.Data;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class CustomRowMapper {
    private static final SAXParserFactory SAX_PARSER_FACTORY = SAXParserFactory.newInstance();

    public Data mapRow(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParser saxParser = SAX_PARSER_FACTORY.newSAXParser();
        Data data = new Data();
        DefaultHandler handler = new RowHandler(data);

        saxParser.parse(file, handler);

        return data;
    }
}
