package timey.ioc.ioc.reader;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class JsonDefinitionReader implements ContextDefinitionReader {

    private final JSONParser jsonParser = new JSONParser();
    private final JsonToContextDefinitionMapper mapper = new JsonToContextDefinitionMapper();

    @Override
    public ContextDefinition readDefinition(String fileName) {
        try {
            URL fileUrl = ClassLoader.getSystemClassLoader().getResource(fileName);
            if (fileUrl != null) {
                JSONObject object = (JSONObject) jsonParser.parse(new FileReader(new File(fileUrl.toURI())));
                return mapper.map(object);
            } else {
                throw new RuntimeException("Can't find file " + fileName);
            }
        } catch (IOException | ParseException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
