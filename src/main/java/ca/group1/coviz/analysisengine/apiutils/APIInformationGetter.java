package ca.group1.coviz.analysisengine.apiutils;

import java.io.IOException;

import okhttp3.OkHttpClient;

/**
 * provides useful methods for lazy API requests
 */
public abstract class APIInformationGetter implements IAPI{
    /**
     * Gets and caches a string based API request
     */
    private static OkHttpClient apiGetterClient = new OkHttpClient();
    private String storedData;

    /**
     * Sends a get request to this getter's data url
     * @return the raw String format of the API response
     * @throws IOException If there was an issue sending the web request
     */
    public String getData() throws IOException{
        if(storedData == null){
            String requestResponse = apiGetterClient.newCall(this.getDataURL()).execute().body().string();
            this.storedData = requestResponse;
            return requestResponse;
        }else{
            return storedData;
        }
    }
}
