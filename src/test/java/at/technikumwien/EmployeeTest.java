package at.technikumwien;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmployeeTest {
    private Employee emp;
    private Employee empFemale;
    private Employee empMale;

    @BeforeEach
    public void setUp() throws Exception {
        emp = new Employee();
        empFemale = new Employee("Martina", "Musterfrau", Sex.FEMALE, LocalDate.of(1991, 1, 1), true);
        empFemale.setClock(getFixedClockWithDate(2009, 12, 31));

        empMale = new Employee("Markus", "Mustermann", Sex.MALE, LocalDate.of(1991, 1, 1), false);
        empMale.setClock(getFixedClockWithDate(2010, 1, 1));
    }

    @Test
    public void testGetFullNameWithFemale() {
        assertEquals("Frau Martina Musterfrau", empFemale.getFullName());
    }

    @Test
    public void testGetFullNameWithMale() {
        assertEquals("Herr Markus Mustermann", empMale.getFullName());
    }

    @Test
    public void testGetFullNameWithSexNull() {
        assertThrows(NullPointerException.class, () -> emp.getFullName());
    }

    @Test
    public void testGetAgeInYearsWithYesterday() {
        assertEquals(18, empFemale.getAgeInYears());
    }

    @Test
    public void testGetAgeInYearsWithClockToday() {
        assertEquals(19, empMale.getAgeInYears());
    }

    private Clock getFixedClockWithDate(int year, int month, int dayOfMonth) {
        return Clock.fixed(
                LocalDateTime.of(year, month, dayOfMonth, 0, 0).toInstant(ZoneOffset.ofHours(0)),
                ZoneId.systemDefault()
        );
    }
}
