package timey.ioc.ioc.reader;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class JsonToContextDefinitionMapper {
    private final Gson gson = new Gson();

    public ContextDefinition map(JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toJSONString(), ContextDefinition.class);
    }
}
