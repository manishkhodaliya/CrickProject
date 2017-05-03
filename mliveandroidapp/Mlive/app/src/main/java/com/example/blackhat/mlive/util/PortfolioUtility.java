package com.example.blackhat.mlive.util;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

/**
 * Created by DigitalDesk on 18/04/2017.
 */

public class PortfolioUtility {
    private static PortfolioUtility utility;
    DocumentBuilder builder;
    InputSource is;
    DocumentBuilderFactory factory;
    final Map<String, String> requestHeader;


    private PortfolioUtility() {

        factory = DocumentBuilderFactory.newInstance();

        requestHeader = new HashMap<String, String>();
        requestHeader.put(AppConstant.CONTENT_TYPE, AppConstant.APPLICATION_X_FORM_URL);

    }
    public static PortfolioUtility getUtility() {
        if (utility == null) {
            utility = new PortfolioUtility();
        }
        return utility;
    }

    public static HttpResponse postRequest(final String URLString, final List<NameValuePair> requestData)
            throws ClientProtocolException, IOException {

        System.out.println("In Post Request of PortfolioUtility ");
        final HttpClient httpClient = HttpClientBuilder.create().build();
        final HttpPost httpPost = new HttpPost(AppConstant.HTTP+URLString);

        //Map<String, String> requestHeader=generateRequestHeader(AppConstant.APPLICATION_X_FORM_URL);
        // requestHeader.setHeader
        if (requestData != null && !requestData.isEmpty()) {
            //List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            httpPost.setEntity(new UrlEncodedFormEntity(requestData));
        }
        System.out.println(httpPost);

        return httpClient.execute(httpPost);
    }
    /**
     * The <code>generateRequestHeader</code> is responsible for generate the request header with below parameters.
     *
     * @param contentType
     * @return
     */
    public static Map<String, String> generateRequestHeader(final String contentType) {

        Map<String, String> requestHeader = null;

        if (!isEmpty(contentType)) {

            requestHeader = new HashMap<String, String>();
            requestHeader.put(AppConstant.CONTENT_TYPE, contentType);
            requestHeader.put("Accept", "application/json");

        }
        return requestHeader;
    }

    /**
     * This method responsible to check whether the String is empty or not.
     *
     * @param param
     * @return
     */
    public static boolean isEmpty(final String param) {

        final boolean error = false;

        if (param == null || param.trim().length() <= 0) {
            return true;
        }

        return error;
    }

    public String getJsonFromXml(String xmlString) {
        String JsonString = "";
        try {
            builder = factory.newDocumentBuilder();
            is = new InputSource(new StringReader(xmlString));
            Document doc = builder.parse(is);
            NodeList list = doc.getElementsByTagName("string");
            JsonString =list.item(0).getTextContent();
            System.out.println("json string ==> "+JsonString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return JsonString;
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }

}
