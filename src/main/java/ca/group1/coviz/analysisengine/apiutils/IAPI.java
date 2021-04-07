package ca.group1.coviz.analysisengine.apiutils;

import okhttp3.Request;

/**
 * An API interface
 */
public interface IAPI {
    /**
     * Implementors of IAPI will override this to get the http request they will send
     * @return The http request that will be sent to an api
     */
    Request getDataURL();
}
