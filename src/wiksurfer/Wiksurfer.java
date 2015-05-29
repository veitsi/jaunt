package wiksurfer;

import com.jaunt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Wiksurfer {

    public static void main(String[] args) {
        surfPages();
    }

    public static void surfPages() {
        int i = 0;//counter for number of processed pages
        UserAgent userAgent = new UserAgent();
        userAgent.settings.autoSaveAsHTML = true;  //change settings to autosave last visited page.
        Elements elements;
        String wikiword;

        try {
            PrintWriter pw = new PrintWriter(new FileWriter("wkwords.txt"));
            try {
                String href = "http://en.wiktionary.org/wiki/Category:English_uncountable_nouns";

                for (i = 0;; i++) {
                    userAgent.visit(href);
                    href = userAgent.doc.getHyperlink("next page").getHref().replaceAll("amp;", "");
                    //Jaunt has now a strange bug on processing strings, so the fragment replaceAll("amp;", "")
                    //is devoted to fix this issue
                    System.out.println("next page:" + href);

                    elements = userAgent.doc.findEvery("<div class=mw-category-group>").findEach("<li>").findEach("<a>");
                    //select all links which contan words we need
                    for (Element el : elements) {           //iterate through Results
                        wikiword = el.getText().replaceAll("amp;", "");
                        if (isGoodWord(wikiword)) {
                            pw.println(wikiword);
                        }
                    }
                }

            } catch (JauntException e) {
                System.err.println(e);
            } finally {
                pw.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Wiksurfer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isGoodWord(String s) {
        char c;
        if (s.split(" +").length > 1) {
            return false;
        }
        c = s.charAt(0);
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }
}
