package mg.developer.patmi.polygo.tools;

import java.text.DecimalFormat;

/**
 * Created by developer on 2/21/17.
 */

public class Converter {

    public static Double gradeToRadian(Double gradeValue) {
        return 0.015707963267949 * gradeValue;
    }

    public static Double decimalFormat(Double value, Integer number) {
        String format = "0.";
        for(int i = 0; i < number; i++) {
            format += "#";
        }
        DecimalFormat df = new DecimalFormat(format);
        return Double.parseDouble(df.format(value));
    }
}
