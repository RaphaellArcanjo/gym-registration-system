package academia.domain.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findByNameContaining(String name);
}
