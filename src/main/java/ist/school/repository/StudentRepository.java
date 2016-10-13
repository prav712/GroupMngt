package ist.school.repository;

import ist.school.domain.Student;

import java.util.List;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public interface StudentRepository {
    List<Student> getAllStudents();

    Student getStudent(long id);

    void deleteStudent(long id);

    void createStudent(Student student);

    void updateStudent(Student student);
}
