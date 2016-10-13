package ist.school.sevice;

import ist.school.domain.Student;
import ist.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Praveen Gupta on 13/10/16.
 */
public class StudentService {
    private StudentRepository studentRepository;

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

    /**
     * finds student of given id
     * @param id id of student
     * @return student of given id
     */
    public Student findStudentByStudentId(long id){
        return studentRepository.getStudent(id);
    }
}
