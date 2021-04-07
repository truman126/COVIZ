package ca.group1.coviz.analysisengine.apiutils;

import java.io.IOException;

import org.json.JSONObject;

/**
 * Gets APi info and attempts to parse it as JSON
 */
public abstract class JSONAPIInformationGetter extends APIInformationGetter {
    JSONObject jsonData;

    /**
     * gets the API response and tries to parse it asd JSON
     * @return The JSON parsed API response
     * @throws IOException If the JSON is malformed
     */
    public JSONObject getJSONData() throws IOException {
        if(jsonData == null){
            jsonData = new JSONObject(this.getData());
        }

        return jsonData;
    }
}
