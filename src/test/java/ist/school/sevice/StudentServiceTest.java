package ist.school.sevice;

import ist.school.domain.Student;
import ist.school.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Praveen Gupta on 13/10/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    private static final long STUDENT_ID_100 = 100L;

    private StudentService studentService;

    @Mock
    StudentRepository studentRepository;


    @Before
    public void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void shouldReturnTwoAsNumberOfUnplacedStudens() {
        Student student1 = new Student(STUDENT_ID_100, "Praveen");
        Student student2 = new Student(101L, "Harald");

        student1.setPlaced(true);
        student2.setPlaced(true);

        when(studentRepository.getAllStudents()).thenReturn(asList(student1,
                student2,
                new Student(102L, "Levi"),
                new Student(103L, "Simen")));

        final List<Student> allUnplacedStudents = studentService.getAllUnplacedStudents();

        assertThat(allUnplacedStudents, hasSize(2));
    }

    @Test
    public void shouldReturnStudentOfGivenId() {
        Student student1 = new Student(STUDENT_ID_100, "Praveen");

        student1.setPlaced(true);

        when(studentRepository.getStudent(STUDENT_ID_100)).thenReturn(student1);

        assertThat(studentService.findStudentByStudentId(STUDENT_ID_100), is(student1));
    }
}