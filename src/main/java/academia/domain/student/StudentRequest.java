package academia.domain.student;

import java.time.LocalDate;

public record StudentRequest(String id, String name, String phone_number, String sex, LocalDate date_of_birth, LocalDate payment_date) {
}
