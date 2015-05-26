package jauntexamples;

import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JauntExamples {

    public static void ex4() {
        int i=0;
        UserAgent userAgent = new UserAgent();
         userAgent.settings.autoSaveAsHTML = true;                    //change settings to autosave last visited page.
         //System.out.println("SETTINGS:\n" + userAgent.settings);   
        try {
            
            String href = "http://en.wiktionary.org/wiki/Category:English_uncountable_nouns";            
            
            for(i=0;i<5;i++){
            userAgent.visit(href);
            //Element anchor = userAgent.doc.findFirst("<a title>next page");            //find 1st anchor element with an href attribute
            //System.out.println("anchor's href attribute: " + anchor.getAt("href"));  //print the anchor's href attribute
            href=userAgent.doc.findFirst("<a title>next page").getAt("href");
            
                try {
                    System.out.println("uri "+new java.net.URI(href));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(JauntExamples.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                try {
                    java.net.URLDecoder.decode(href, "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(JauntExamples.class.getName()).log(Level.SEVERE, null, ex);
                }
            
             System.out.println("next page:"+href);
            }
            
        } catch (JauntException e) {
            System.err.println(e);
        } 
        finally {
            System.out.println("final i"+i);
        }
    }

    public static void runAllListPages() {
        try {
            UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
            userAgent.visit("http://en.wiktionary.org/wiki/Category:English_uncountable_nouns");                        //visit a url   
            //System.out.println(userAgent.doc.innerHTML());               //print the content as HTML
            String title = userAgent.doc.findFirst("<title>").getText(); //get child text of title element.
           
            System.out.println("\nwiktionary's website title: " + title);

        } catch (JauntException e) {         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        //runAllListPages();
        ex4();
    }

}
