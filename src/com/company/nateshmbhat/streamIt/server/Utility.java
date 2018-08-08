package com.company.nateshmbhat.streamIt.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class Utility {

    public static HashMap<String , String> parsePostRequest(String postbody){
        //Input string is a url encoded post body and hence has to be split right
        HashMap<String , String> map  = new HashMap<>();

        for(String keyValue :  postbody.split("&"))
        {
            String[] keyval = keyValue.split("=") ;
            map.put(decodeUrl(keyval[0]) , decodeUrl(keyval[1])) ;
        }
        return map ;
    }


    ///Takes encoded URL and returns a decoded one
    public static String decodeUrl(String url)
    {
        try {
            String prevURL="";
            String decodeURL=url;
            while(!prevURL.equals(decodeURL))
            {
                prevURL=decodeURL;
                decodeURL= URLDecoder.decode( decodeURL, "UTF-8" );
            }
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" +e.getMessage();
        }
    }



    public static String getPostRequestBody(InputStream inputStream) {
        String s = new String();

        byte[] bytes = new byte[1024];
        while (true) {
            try {
                if (inputStream.read(bytes) == -1) break;
                s += new String(bytes);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        return s ;
    }
}
