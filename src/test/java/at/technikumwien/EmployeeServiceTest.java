package at.technikumwien;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EntityManager em;
    @InjectMocks
    private EmployeeService employeeService;

    private Employee emp;

    @BeforeEach
    public void setUp() {
        emp = new Employee();
    }

    @Test
    public void testMocks() {
        assertNotNull(em);
        assertNotNull(employeeService);
        assertSame(em, employeeService.getEm());
        assertNull(em.merge(null));
    }

    @Test
    public void testSaveWithCreate() {
        employeeService.save(emp);

        verify(em, times(1)).persist(emp);
        verify(em, never()).merge(emp); // redundant
        verifyNoMoreInteractions(em);
    }

    @Test
    public void testSaveWithUpdate() {
        emp.setId(1L);

        when(em.merge(emp)).thenReturn(emp);
        // doReturn(emp).when(em).merge(emp);

        var empSaved = employeeService.save(emp);

        assertSame(emp, empSaved);
        verify(em).merge(emp);
        verifyNoMoreInteractions(em);
    }
}
