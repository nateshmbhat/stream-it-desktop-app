package com.streamit.nateshmbhat.streamIt.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class Utility {
    public static HashMap<String , String> parsePostRequest(String postbody){
        //Input string is a url encoded post body and hence has to be split right
        System.out.println(postbody);
        HashMap<String , String> map  = new HashMap<>();

        if(postbody.isEmpty()) return map ;

        for(String keyValue :  postbody.split("&"))
        {
            String[] keyval = keyValue.split("=") ;
            try {
                map.put(URLDecoder.decode(keyval[0] , "utf-8") , URLDecoder.decode(keyval[1] , "utf-8")) ;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return map ;
    }

    public static HashMap<String , String> parsePostRequest(InputStream inputStream){
        //Input string is a url encoded post body and hence has to be split right
        String postbody = getPostRequestBody(inputStream) ;

        System.out.println(postbody);
        HashMap<String , String> map  = new HashMap<>();

        if(postbody.isEmpty()) return map ;

        for(String keyValue :  postbody.split("&"))
        {
            String[] keyval = keyValue.split("=") ;
            try {
                map.put(URLDecoder.decode(keyval[0] , "utf-8") , URLDecoder.decode(keyval[1] , "utf-8")) ;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return map ;
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
