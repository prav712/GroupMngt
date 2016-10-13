package ist.school.sevice;

import ist.school.domain.Student;
import ist.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Praveen Gupta on 13/10/16.
 */
public class StudentService {
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * @return students who are not placed in any groups
     */
    public List<Student> getAllUnplacedStudents(){
        return studentRepository.getAllStudents().stream()
                .filter(Student::isPlaced)
                .collect(Collectors.toList());
    }
}
