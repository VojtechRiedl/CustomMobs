package me.histal.custommobs;

public class Utils {

    public static Double convertToDouble(String string) {

        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
