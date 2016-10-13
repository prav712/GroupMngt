package ist.school.sevice;


import ist.school.domain.Student;
import ist.school.domain.StudyClass;
import ist.school.domain.Subject;
import ist.school.domain.SubjectGroup;
import ist.school.repository.SubjectGroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SubjectGroupServiceTest {
    private static final long STUDENT_ID_100 = 100L;

    private static final long CLASS_ID_200 = 200L;

    private static final long SUBJECT_ID_400 = 400L;
    private static final long SUBJECT_ID_401 = 401L;
    private static final long SUBJECT_ID_402 = 404L;

    private static final long GROUP_ID_300 = 300L;

    private static final String MATHS = "Maths";
    private static final String GEOGRAPHY = "Geography";
    private static final String FRENCH = "French";

    private SubjectGroupService groupService;

    @Mock
    private StudentService studentService;

    @Mock
    SubjectGroupRepository subjectGroupRepository;

    @Before
    public void setUp() {
        groupService = new SubjectGroupService(studentService, subjectGroupRepository);
    }

    @Test
    public void shouldReturnSubjectGroupsWhichHasMinCapacityNotReached() {
        SubjectGroup commonGroupMock = mock(SubjectGroup.class);
        SubjectGroup scienceGroupMock = mock(SubjectGroup.class);

        when(subjectGroupRepository.getAllSubjectGroups()).thenReturn(asList(commonGroupMock, scienceGroupMock));

        when(commonGroupMock.isMinLimitReached()).thenReturn(true);
        when(scienceGroupMock.isMinLimitReached()).thenReturn(false);

        assertThat(groupService.fetchAllSubjectGroupsWhichHasMinCapacityNotReached(), hasSize(1));
    }


    @Test
    public void shouldNotDoAnyThingIfStudentIsAlreadyPlaced() {
        Student student = new Student(STUDENT_ID_100, "Praveen");
        student.setPlaced(true);

        StudyClass studyClass = new StudyClass(CLASS_ID_200, "OneA");

        SubjectGroup commonGroupMock = mock(SubjectGroup.class);

        final Subject maths = new Subject(SUBJECT_ID_400, MATHS);
        final Subject geography = new Subject(SUBJECT_ID_401, GEOGRAPHY);
        final Subject french = new Subject(SUBJECT_ID_402, FRENCH);

        studyClass.setSubjectGroupList(asList(commonGroupMock));

        student.setStudyClass(studyClass);

        student.setSubjectList(asList(maths, geography, french));

        when(commonGroupMock.isMaxLimitReached()).thenReturn(true).thenReturn(true);
        when((studentService.findStudentByStudentId(STUDENT_ID_100))).thenReturn(student);

        groupService.addStudentToSubjectGroup(STUDENT_ID_100);

        verify(commonGroupMock, never()).isMaxLimitReached();
    }

    @Test
    public void shouldNotPlaceStudentIfMaxCapacityOfGroupsAreReached() {
        Student student = new Student(STUDENT_ID_100, "Praveen");

        StudyClass studyClass = new StudyClass(CLASS_ID_200, "OneA");

        SubjectGroup commonGroup = mock(SubjectGroup.class);
        SubjectGroup scienceGroup = mock(SubjectGroup.class);

        final Subject maths = new Subject(SUBJECT_ID_400, MATHS);
        final Subject geography = new Subject(SUBJECT_ID_401, GEOGRAPHY);
        final Subject french = new Subject(SUBJECT_ID_402, FRENCH);

        studyClass.setSubjectGroupList(asList(commonGroup, scienceGroup));

        student.setStudyClass(studyClass);

        student.setSubjectList(asList(maths, geography, french));


        when(commonGroup.isMaxLimitReached()).thenReturn(true).thenReturn(true);
        when(scienceGroup.isMaxLimitReached()).thenReturn(true).thenReturn(true);
        when((studentService.findStudentByStudentId(STUDENT_ID_100))).thenReturn(student);

        groupService.addStudentToSubjectGroup(STUDENT_ID_100);

        assertThat(student.isPlaced(), is(false));
    }

    @Test
    public void shouldNotPlaceStudentIfStudentHasAlreadyBeenAssignedToGroup() {
        Student student = new Student(STUDENT_ID_100, "Praveen");

        StudyClass studyClass = new StudyClass(CLASS_ID_200, "OneA");

        SubjectGroup commonGroup = new SubjectGroup(GROUP_ID_300, "Common");
        commonGroup.setStudentList(asList(student));

        final Subject maths = new Subject(SUBJECT_ID_400, MATHS);
        final Subject geography = new Subject(SUBJECT_ID_401, GEOGRAPHY);
        final Subject french = new Subject(SUBJECT_ID_402, FRENCH);

        studyClass.setSubjectGroupList(asList(commonGroup));

        student.setStudyClass(studyClass);

        student.setSubjectList(asList(maths, geography, french));

        when((studentService.findStudentByStudentId(STUDENT_ID_100))).thenReturn(student);

        groupService.addStudentToSubjectGroup(STUDENT_ID_100);

        assertThat(student.isPlaced(), is(false));
    }

    @Test
    public void shouldNotPlaceStudentIfSomeSubjectsMatchToAGroupButMaxCapacityOfOtherGroupsAreReached() {
        Student student = new Student(STUDENT_ID_100, "Praveen");

        StudyClass studyClass = new StudyClass(CLASS_ID_200, "OneA");

        SubjectGroup commonGroup = new SubjectGroup(GROUP_ID_300, "Common");
        SubjectGroup scienceGroup = Mockito.mock(SubjectGroup.class);

        final Subject maths = new Subject(SUBJECT_ID_400, MATHS);
        final Subject geography = new Subject(SUBJECT_ID_401, GEOGRAPHY);
        final Subject french = new Subject(SUBJECT_ID_402, FRENCH);

        studyClass.setSubjectGroupList(asList(commonGroup, scienceGroup));

        student.setStudyClass(studyClass);

        student.setSubjectList(asList(maths, geography, french));

        when(scienceGroup.isMaxLimitReached()).thenReturn(true).thenReturn(true);
        when((studentService.findStudentByStudentId(STUDENT_ID_100))).thenReturn(student);

        groupService.addStudentToSubjectGroup(STUDENT_ID_100);

        assertThat(student.isPlaced(), is(false));
    }

    @Test
    public void shouldPlaceStudentAndAssignedToTheGroupsIfMaxCapacityOfGroupsAreNotReached() {
        Student student = new Student(STUDENT_ID_100, "Praveen");

        StudyClass studyClass = new StudyClass(CLASS_ID_200, "OneA");

        SubjectGroup commonGroup = new SubjectGroup(GROUP_ID_300, "Common");
        SubjectGroup scienceGroup = new SubjectGroup(301L, "Science");
        SubjectGroup foreignLanguage1 = new SubjectGroup(302L, "ForeignLanguage1");
        SubjectGroup foreignLanguage2 = new SubjectGroup(303L, "ForeignLanguage2");

        final Subject maths = new Subject(SUBJECT_ID_400, MATHS);
        final Subject geography = new Subject(SUBJECT_ID_401, GEOGRAPHY);
        final Subject physics = new Subject(402L, "Physics");
        final Subject chemistry = new Subject(403L, "Chemistry");
        final Subject french = new Subject(SUBJECT_ID_402, FRENCH);
        final Subject hindi = new Subject(405L, "Hindi");


        commonGroup.setSubjectList(asList(maths, geography));
        scienceGroup.setSubjectList(asList(physics, chemistry));
        foreignLanguage1.setSubjectList(asList(french));
        foreignLanguage2.setSubjectList(asList(hindi));

        studyClass.setSubjectGroupList(asList(commonGroup, scienceGroup, foreignLanguage1, foreignLanguage2));

        student.setStudyClass(studyClass);
        student.setSubjectList(asList(maths, geography, french));

        when((studentService.findStudentByStudentId(STUDENT_ID_100))).thenReturn(student);

        groupService.addStudentToSubjectGroup(STUDENT_ID_100);

        assertThat(student.isPlaced(), is(true));
        assertThat(student.getStudyClass().getSubjectGroupList().get(0).getStudentList().get(0), is(student));
        assertThat(student.getStudyClass().getSubjectGroupList().get(1).getStudentList(), hasSize(0));
        assertThat(student.getStudyClass().getSubjectGroupList().get(2).getStudentList().get(0), is(student));
        assertThat(student.getStudyClass().getSubjectGroupList().get(3).getStudentList(), hasSize(0));
    }
}