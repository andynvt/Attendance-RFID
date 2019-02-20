/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

/**
 *
 * @author chuna
 */
public class Check {

    public static boolean isNumber(String num) {
        try {
            int n = Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean unAccent(String s) {
        return s.matches("^[a-zA-Z0-9_.-]*$");
    }

    public static boolean isInterger(String s) {
        return s.matches("^\\d+$");
    }

    public static String reduceToAlphaNumerics(String value) {
        return value.replaceAll("[^0-9a-zA-Z]+", " ");
    }

    public static boolean isNumberWith2Decimals(String string) {
        return string.matches("^\\d+\\.\\d{2}$");
    }

    public static boolean isNumeric(String string) {
        return string.matches("^[-+]?\\d+(\\.\\d+)?$");
    }

    public static boolean isValidEmailAddress(String email) {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isNameValid(String name) {
        boolean retval;
        String namePattern = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
        retval = name.matches(namePattern);
        return retval;
    }

    public static boolean isRFIDValid(String RFID) {
        boolean retval;
        String phoneNumberPattern = "^[0-9]*$";
        retval = RFID.matches(phoneNumberPattern);
//        String msg = "NO MATCH: pattern:" + RFID
//                + "\r\n regex: " + phoneNumberPattern;
//        if (retval) {
//            msg = " MATCH: pattern:" + RFID + "\r\n             regex: "
//                    + phoneNumberPattern;
//        }
//        System.out.println(msg + "\r\n");
        return retval;
    }

    public static void main(String args[]) throws Exception {
        System.out.println(Check.unAccent("0"));
    }
}
