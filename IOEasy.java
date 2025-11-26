import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IOEasy {
    static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your Name: ");
        String name = sc.nextLine();
        try (FileWriter writer = new FileWriter("/Users/phanidatana/ShakespeareSonnet 2/name.txt",true))
        {
            writer.write(name +"\n");
            writer.flush();
            writer.close();
            System.out.println("Completed");
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

    }
}
