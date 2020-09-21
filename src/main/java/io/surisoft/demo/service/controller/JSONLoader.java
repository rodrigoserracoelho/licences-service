package io.surisoft.demo.service.controller;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;

public class JSONLoader {

    public JSONObject getLicenses() throws IOException, ParseException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("license.json");
        ByteSource byteSource = new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return inputStream;
            }
        };
        JSONParser jsonParser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        return (JSONObject) jsonParser.parse(byteSource.asCharSource(Charsets.UTF_8).read());
    }
}
