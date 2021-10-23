package Dev_will_work.hse.person;

import java.time.LocalDate;
import java.time.Period;

/**
 *The PersonData class provides tools for work with
 * FIO and data of birth of a person
 *
 * @version 1.0 22 Mar 2021
 * @author Pasha Emshanov
 */
public class PersonData {
    final String name, surname, patronymic;
    final LocalDate date;

    /**
     *
     * @param surname surname of a person
     * @param name name of a person
     * @param patronymic patronymic of a person
     * @param date date of birth of a person
     */
    PersonData(String surname, String name, String patronymic,
                                                LocalDate date) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.date = date;
    }

    /**
     * Determines the gender of a person by his patronymic.
     * Gender can be male, female or unknown because of
     * the lack of information(?)
     * @return character, representing a person's gender
     */
    char determineGender() {
        if (this.patronymic.endsWith("ч") ||
            this.patronymic.endsWith("в") ||
            this.patronymic.endsWith("ch") ||
            this.patronymic.endsWith("v")) return 'М';
        else if (this.patronymic.endsWith("на") ||
                this.patronymic.endsWith("na")) return 'Ж';
        else return '?';
    }

    /**
     * Calculates the age of a person by date of his birth
     * @return age
     */
    int calculateAge() {
        final LocalDate today = LocalDate.now();
        return Period.between(this.date, today.minusDays(1)).getYears();
    }

    /**
     * Prints surname and initials of a person in the chosen format:
     * Surname N. P.
     */
    void printFIO() {
        System.out.print(this.surname + ' ' + this.name.charAt(0) +
                            ". " + this.patronymic.charAt(0) + ". ");
    }
}
