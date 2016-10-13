package ist.school.sevice;

import ist.school.domain.Student;
import ist.school.domain.Subject;
import ist.school.domain.SubjectGroup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Praveen Gupta on 13/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {
    private AdminService adminService;

    @Mock
    private StudentService studentService;

    @Mock
    private SubjectGroupService subjectGroupService;

    @Before
    public void setUp() {
        adminService = new AdminService(studentService, subjectGroupService);
    }

    @Test
    public void shouldReturnTrueIfUnplacedStudentsAreNotifiedToHeadmaster() {
        Student student1 = new Student(102L, "Levi");
        Student student2 = new Student(103L, "Simen");

        when(studentService.getAllUnplacedStudents()).thenReturn(asList(student1, student2));

        assertThat(adminService.notifyUnplacedStudentsToHeadmaster(), hasSize(2));
    }

    @Test
    public void shouldAddSubjectToTheStudent() {
        Student student1 = new Student(102L, "Levi");

        when(studentService.findStudentByStudentId(102L)).thenReturn(student1);

        Subject maths = new Subject(200l, "maths");

        adminService.addSubjectsToTheStudent(102l, Arrays.asList(maths));

        assertThat(student1.getSubjectList().get(0), is(maths));
    }

    @Test
    public void shouldUnplaceStudentsWhoesGroupHasNotReachedMinCapacity() {
        Student student1 = new Student(102L, "Levi");
        Student student2 = new Student(103L, "Simen");

        student1.setPlaced(true);
        student2.setPlaced(true);

        SubjectGroup scienceGroup = new SubjectGroup(200L, "Science");
        scienceGroup.setStudentList(asList(student1, student2));

        when(subjectGroupService.fetchAllSubjectGroupsWhichHasMinCapacityNotReached()).thenReturn(asList(scienceGroup));

        adminService.unplaceStudentsOfGroupsWithHasMinLimitIsNotReached();

        scienceGroup.getStudentList().forEach(student -> {
            assertThat(student.isPlaced(), is(false));
        });
    }
}