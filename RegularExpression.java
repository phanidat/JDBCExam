import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
    static void main(){
        String text = "http://jenkov.com";

        Pattern pattern = Pattern.compile("^http://");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()){
            System.out.println("Found match at: "  + matcher.start() + " to " + matcher.end());
        }

        String text1= "Mary had a little lamb";

        Pattern pattern1 = Pattern.compile("\\bl");
        Matcher matcher1 = pattern1.matcher(text1);

        while(matcher1.find()){
            System.out.println("Found match at: "  + matcher1.start() + " to " + matcher1.end());
        }
//        String text2 = "";
//        String text3 = "c*";
//        boolean matches = text2.matches(text3);
//        System.out.println("Matches: " + matches);

//        String text2 = "one two three two one";
//
//        boolean matches = text2.matches(".*two.*");
//        System.out.println("Matches: " + matches);

        String text3 = "one two three one one";

        String[] twos = text3.split("two");
        System.out.println("Array: " + twos.length);


    }

}
