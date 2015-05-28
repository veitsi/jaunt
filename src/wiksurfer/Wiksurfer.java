package wiksurfer;

import com.jaunt.*;

public class Wiksurfer {

    public static void surfPages() {
        int i = 0;
        UserAgent userAgent = new UserAgent();
        userAgent.settings.autoSaveAsHTML = true;  //change settings to autosave last visited page.
        Elements elements;
        //System.out.println("SETTINGS:\n" + userAgent.settings);   
        try {

            String href = "http://en.wiktionary.org/wiki/Category:English_uncountable_nouns";

            for (i = 0; i < 1; i++) {
                userAgent.visit(href);
                href = userAgent.doc.findFirst("<a title>next page").getAt("href");

                System.out.println("next page:" + href);
                elements = userAgent.doc.findEvery("<div class=mw-category-group>").findEach("<li>").findEach("<a>");                        //find all non-nested divs
                System.out.println("Each a: " + elements.size() + elements);
                for (Element el : elements) {                                     //iterate through Results
                    System.out.println(el.getText());         //print each element and its contents
                }
            }
        } catch (JauntException e) {
            System.err.println(e);
        } finally {
            System.out.println("final i" + i);
        }
    }

    public static void main(String[] args) {
        surfPages();
    }

}
