package utils;

import logger.Log;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ParamsTransformation {

    private static Signature sign = new Signature();

    private static String getTimeStamp() {
        long timestamp = System.currentTimeMillis();
        return "timestamp=" + String.valueOf(timestamp);
    }

    public static String joinQueryParameters(HashMap<String,String> parameters) {
        String urlPath = "";
        boolean isFirst = true;

        for (Map.Entry mapElement : parameters.entrySet()) {
            if (isFirst) {
                isFirst = false;
                urlPath += (String)mapElement.getKey() + "=" + (String)mapElement.getValue();
            } else {
                urlPath += "&" + (String)mapElement.getKey() + "=" + (String)mapElement.getValue();
            }
        }
        return urlPath;
    }

    public static URL getFinalURL(HashMap<String, String> parameters, String partOfUri){
        String queryPath = "";
        String signature = "";
        parameters.put("recvWindow", "60000");
        if(!parameters.isEmpty()){
            queryPath += joinQueryParameters(parameters) + "&" + getTimeStamp();
        } else{
            queryPath += getTimeStamp();
        }
        try{
            signature = sign.getSignature(queryPath, TestDataReader.getTestData("SECRET_KEY"));
        }
        catch (Exception e) {
            Log.error("Please Ensure Your Secret Key Is Set Up Correctly! " + e);
            System.exit(0);
        }
        queryPath += "&signature=" + signature;
        URL url = null;
        try{
            url = new URL(TestDataReader.getTestData("URI") + partOfUri + "?" + queryPath);
            Log.info("Url created: " + url);
        }
        catch(Exception ex){
            Log.error(ex.getMessage());
        }

        return url;
    }
}
