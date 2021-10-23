package Dev_will_work.hse.person;

import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class Main works with person's data
 * @version 1.0 22 Mar 2021
 * @author Pasha Emshanov
 */
public class Main {

    /**
     * Creates new word from the input and validates it with the
     * given regex pattern
     * @param description a name for the name word, affects only
     *                    text of the invite for the input
     * @param pattern regex expression, that validates the input
     * @return created word
     */
    static String createWord(String description, String pattern) {
        final Scanner read = new Scanner(System.in);
        String word;
        System.out.println("Enter your " + description + ":");
        word = read.next();
        while (!word.matches(pattern)) {
            System.out.println("Enter your " + description + ":");
            word = read.next();
        }
        return word;
    }

    public static void main(String[] args) {
        final String surname, name, patronymic;
        final Scanner read = new Scanner(System.in);
        LocalDate lDate;
        final DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("dd.MM.uuuu");
        final String wordRegex = "[А-Я][а-я]+|[A-Z][a-z]+";
        surname = createWord("surname", wordRegex);
        name = createWord("name", wordRegex);
        patronymic = createWord("patronymic", wordRegex);

        while (true) {
            System.out.println("Enter your date by " +
                    "following format:(dd.mm.yyyy)");
            try {
                lDate = LocalDate.parse(read.next(), dateFormat);
                if (lDate.getYear() > LocalDate.now().getYear()) {
                    throw new DateTimeParseException(
                      "data is in the future",
                      lDate.toString(), 2 + 1 + 2 + 1 + 1);
                }
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.println("Wrong date format. Try again");
                continue;
            }
            break;
        }

        final PersonData pd = new PersonData(surname, name, patronymic, lDate);
        pd.printFIO();
        System.out.print(pd.determineGender() + " ");
        System.out.print(pd.calculateAge() + " лет");
        System.out.println();
    }
}
