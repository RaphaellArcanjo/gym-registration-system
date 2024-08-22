package academia.domain.student;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table(name="student")
@Entity(name="student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String phone_number;

    private String sex;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date_of_birth;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate payment_date;

    public Student(StudentRequest studentRequest){
        this.name = studentRequest.name();
        this.phone_number = studentRequest.phone_number();
        this.sex = studentRequest.sex();
        this.date_of_birth = studentRequest.date_of_birth();
        this.payment_date = studentRequest.payment_date();
    }
}
