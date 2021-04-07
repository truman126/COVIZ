package ca.group1.coviz.analysisengine.apiutils;

import java.util.List;

import ca.group1.coviz.util.Constants;
import ca.group1.coviz.util.Country;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * A set of functions for getting URLs specific to the GlobalHealth5050 API
 * https://api.globalhealth5050.org/docs/
 */
public class GlobalHealth5050URL {
    private GlobalHealth5050URL(){}

    /**
     * Get a list of country short names for calling the API with
     * @param targets A list of target countries
     * @return The string of country codes for the target countries
     */
    private static String createCountryListString(List<Country> targets){
        StringBuilder countryTargets = new StringBuilder();
        // construct string of country codes with comma after each code
        // CA,US,CN,
        for(Country c : targets){
            countryTargets.append(c.getShortName());
            countryTargets.append(',');
        }
        assert countryTargets.length() > 0;
        // remove last comma from string and add the compiled string to the request
        // ?code=CA,US,CN
        return countryTargets.substring(0, countryTargets.length()-1);
    }

    /**
     * Get the API request that needs to be sent to access the summary API with the given targets
     * @param targets The list of targets to request info about
     * @return The request that needs to be sent to get the summary info about the targets
     */
    public static Request summaryURL(List<Country> targets) {
        HttpUrl.Builder requestBuilder = new HttpUrl.Builder().scheme("http").host(Constants.GLOBALHEALTH_API).addPathSegments(Constants.GLOBALHEALTH_API_SUMMARY_PATH_SEGMENT);

        requestBuilder = requestBuilder.addQueryParameter("code", createCountryListString(targets));

        HttpUrl requestUrl = requestBuilder.build();
        return new Request.Builder().url(requestUrl).build();
    }

    /**
     * Get the API request that needs to be sent to access the agesex API with the given targets
     * @param targets The list of targets to request info about
     * @return The request that needs to be sent to get the summary info about the targets
     */
    public static Request ageSexURL(List<Country> targets) {
        HttpUrl.Builder requestBuilder = new HttpUrl.Builder().scheme("http").host(Constants.GLOBALHEALTH_API).addPathSegments(Constants.GLOBALHEALTH_API_AGESEX_PATH_SEGMENT);

        requestBuilder = requestBuilder.addQueryParameter("code", createCountryListString(targets));

        HttpUrl requestUrl = requestBuilder.build();
        return new Request.Builder().url(requestUrl).build();
    }

}
