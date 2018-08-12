package com.streamit.nateshmbhat.streamIt.server;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
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

    public static String getAudioMetaData(String filepath)
    {
        AudioFile f = null;
        try {
            f = AudioFileIO.read(new File(filepath));
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }

        Tag tag = f.getTag();
        AudioHeader audioHeader = f.getAudioHeader();


        StringBuilder string = new StringBuilder() ;

        string.append("trackLength = "+ String.valueOf(f.getAudioHeader().getTrackLength())+"\n") ;
        string.append("sampleRate = "+ String.valueOf(f.getAudioHeader().getSampleRate())+"\n") ;
        string.append("artist = "+ String.valueOf(tag.getFirst(FieldKey.ARTIST))+"\n") ;
        string.append("title = "+ tag.getFirst(FieldKey.TITLE)+"\n") ;
        string.append("album = "+ tag.getFirst(FieldKey.ALBUM) +"\n") ;
        string.append("year= "+ tag.getFirst(FieldKey.YEAR) +"\n") ;
        string.append("track= "+ tag.getFirst(FieldKey.TRACK) +"\n") ;
        string.append("composer= " + tag.getFirst(FieldKey.COMPOSER) + "\n" ) ;
        string.append("comment = " + tag.getFirst(FieldKey.COMMENT) + "\n" ) ;
        string.append("genre = " + tag.getFirst(FieldKey.GENRE) + "\n" ) ;
        string.append("lyricist = " + tag.getFirst(FieldKey.LYRICIST) + "\n" ) ;
        string.append("lyricsSite = " + tag.getFirst(FieldKey.URL_LYRICS_SITE) + "\n" ) ;
        string.append("lyrics = " + tag.getFirst(FieldKey.LYRICS) + "\n" ) ;

        System.out.println(string.toString());

        return string.toString();
    }
}
