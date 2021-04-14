package redmine.util;

import java.util.Random;

public class StringGenerator {
    public static final String ENGLISH_UPPER = "QAZWSXEDCRFVTGBYHNUJMIKOLP";
    public static final String ENGLISH_LOWER = "qazwsxedcrfvtgbyhnujmikolp";
    public static final String ENGLISH = "QAZWSXEDCRFVTGBYHNUJMIKOLPqazwsxedcrfvtgbyhnujmikolp";

    public static String randomString(int length, String ENGLISH) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            sb.append(ENGLISH.charAt(new Random().nextInt(ENGLISH.length())));

        }
        return sb.toString();

    }

    public static String randomEmail() {
        StringBuilder sb = new StringBuilder();
        return (sb.append(ENGLISH.charAt(new Random().nextInt(26))) + "@" + sb.append(ENGLISH.charAt(new Random().nextInt(26))) + "." + sb.append(ENGLISH.charAt(new Random().nextInt(26))));


    }


}
