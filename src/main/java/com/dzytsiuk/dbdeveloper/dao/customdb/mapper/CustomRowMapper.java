package com.dzytsiuk.dbdeveloper.dao.customdb.mapper;

import com.dzytsiuk.dbdeveloper.entity.Data;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.EmptyStackException;

public class CustomRowMapper {
    private static final SAXParserFactory SAX_PARSER_FACTORY = SAXParserFactory.newInstance();
    private static final int ESCAPE_CHARS_COUNT = 3;

    public Data mapRow(InputStream stream) throws ParserConfigurationException, SAXException, IOException {
        SAXParser saxParser = SAX_PARSER_FACTORY.newSAXParser();
        Data data = new Data();
        DefaultHandler handler = new RowHandler(data);

        if (stream.available() > ESCAPE_CHARS_COUNT) {
           //?
            try {
                saxParser.parse(stream, handler);
            } catch (EmptyStackException e) {
            }
        }

        return data;
    }
}
