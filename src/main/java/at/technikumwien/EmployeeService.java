package at.technikumwien;

import lombok.Getter;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Stateless
public class EmployeeService {
    @Getter
    @Setter
    @PersistenceContext
    private EntityManager em;

    public long Count() {
        return findAll().size();
    }

    public void deleteById(long id) {
        var employee = findById(id).orElseThrow();
        em.remove(employee);
    }

    public List<Employee> findAll() {
        return em.createNamedQuery("Employee.selectAll", Employee.class).getResultList();
    }

    public List<Employee> findAll(Predicate<Employee> predicate) {
        Objects.requireNonNull(predicate, "predicate must not be null");

        return findAll().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Optional<Employee> findById(long id) {
        return Optional.ofNullable(
                em.find(Employee.class, id)
        );
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            em.persist(employee);
            return employee;
        } else {
            return em.merge(employee);
        }
    }

    public List<Employee> saveAll(List<Employee> employees) {
        return employees.stream()
                .map(employee -> save(employee))
                .collect(Collectors.toList());
    }
}
