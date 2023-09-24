package lk.ijse.healthcare.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(LinkedHashMap<JFXTextField, Pattern> map, Button btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-border-color: red");

                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-border-color: green");

        }
        btn.setDisable(false);
        return true;
    }
    public static final Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
    public static final Pattern contactPattern = Pattern.compile("^(071|072|073|074|070|075|076|077|078|079)[-]?[0-9]{7}$");
    public static final Pattern CustIdPAttern = Pattern.compile("^(C0-)[0-9]{3}$");
    public static final Pattern AddressPAttern = Pattern.compile("^[A-z0-9/ ]{3,30}$");
    public static final Pattern EmpIdPAttern = Pattern.compile("^(Em-)[0-9]{3}$");
    public static final Pattern SallaryPAttern = Pattern.compile("^()[0-9]{1,7}$");
    public static final Pattern ItemIdPAttern = Pattern.compile("^(It-)[0-9]{3}$");
    public static final Pattern QtyPAttern= Pattern.compile("^()[0-9]{2,7}$");
    public static final Pattern PayIdPAttern = Pattern.compile("^(Pi-)[0-9]{3}$");
    public static final Pattern OderIdPAttern = Pattern.compile("^(Or-)[0-9]{3}$");
    public static final Pattern RepIdPattern = Pattern.compile("^(Ri-)[0-9]{3}$");
    public static final Pattern ReIdPAttern = Pattern.compile("^(Re-)[0-9]{3}$");
    public static final Pattern SupIdPAttern = Pattern.compile("^[0-9]{3}$");

    public static final Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    public static final Pattern datePattern = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
    public static final Pattern timePattern = Pattern.compile("(?:[01]\\d|2[0-3]):(?:[0-5]\\d)");
    public static final Pattern idPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
    public static final Pattern emailPattern = Pattern.compile("(^[a-zA-Z0-9_.-]+)@(gmail+)([\\.])(com)$");
    public static final Pattern mobilePattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");

    public static final Pattern genderTypePattern = Pattern.compile("^(MALE|FEMALE)$");

    public static final Pattern doublePattern22 = Pattern.compile("^[0-9]{1,2}+($|\\.[0-9]{1,2})$");
    public static final Pattern doublePattern42 = Pattern.compile("^[0-9]{1,4}+($|\\.[0-9]{1,2})$");
    public static final Pattern doublePattern82 = Pattern.compile("^[0-9]{1,8}+($|\\.[0-9]{1,2})$");
    public static final Pattern intPattern2 = Pattern.compile("^[1-9]{1}+[0-9]{0,1}$");
    public static final Pattern intPattern1 = Pattern.compile("^[1-9]{1}$");

    public static final Pattern orderIdPattern = Pattern.compile("^(O)[0-9]{3}$");
    public static final Pattern docIdPattern = Pattern.compile("^(D)[0-9]{3}$");
    public static final Pattern appointmentIdPattern = Pattern.compile("^(I)[0-9]{3}$");
    public static final Pattern patientIdPattern = Pattern.compile("^(P)[0-9]{3}$");
    public static final Pattern employeeIdPattern = Pattern.compile("^(E)[0-9]{3}$");
    public static final Pattern medicineIdPattern = Pattern.compile("^(M)[0-9]{3}$");
    public static final Pattern nicPattern = Pattern.compile("^[A-z0-9/ ]{3,13}$");
    public static final Pattern userIdPattern = Pattern.compile("^(U)[0-9]{3}$");

    public static void setFocus(JFXTextField textField, Pattern pattern) {
        textField.setOnKeyReleased(keyEvent -> {
            Matcher matcher = pattern.matcher(textField.getText());

            if (textField.getText().isEmpty() || textField.getText().isBlank() || !((Matcher) matcher).matches() ){
                textField.setFocusColor(Paint.valueOf("red"));
                textField.setUnFocusColor(Paint.valueOf("red"));
            }else {
                textField.setFocusColor(Paint.valueOf("green"));
                textField.setUnFocusColor(Paint.valueOf("green"));
            }

        });
    }


}
