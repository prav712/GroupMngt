package ist.school.sevice;


import ist.school.domain.Student;
import ist.school.domain.StudyClass;
import ist.school.domain.Subject;
import ist.school.domain.SubjectGroup;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class SubjectGroupServiceTest {
    private SubjectGroupService groupService;

    @Before
    public void setUp() {
        groupService = new SubjectGroupService();
    }

    @Test
    public void shouldNotDoAnyThingIfStudentIsAlreadyPlaced() {
        Student student = new Student(100L, "Praveen");
        student.setPlaced(true);

        StudyClass studyClass = new StudyClass(200L, "OneA");

        SubjectGroup commonGroupMock = mock(SubjectGroup.class);

        when(commonGroupMock.isMaxLimitReached()).thenReturn(true).thenReturn(true);

        final Subject maths = new Subject(400L, "Maths");
        final Subject geography = new Subject(401L, "Geography");
        final Subject french = new Subject(404L, "French");

        studyClass.setSubjectGroupList(asList(commonGroupMock));

        student.setStudyClass(studyClass);

        student.setSubjectList(asList(maths, geography, french));

        groupService.addStudentToSubjectGroup(student);

        verify(commonGroupMock, never()).isMaxLimitReached();
    }

    @Test
    public void shouldNotPlaceStudentIfMaxCapacityOfGroupsAreReached() {
        Student student = new Student(100L, "Praveen");

        StudyClass studyClass = new StudyClass(200L, "OneA");

        SubjectGroup commonGroup = mock(SubjectGroup.class);
        SubjectGroup scienceGroup = mock(SubjectGroup.class);

        when(commonGroup.isMaxLimitReached()).thenReturn(true).thenReturn(true);
        when(scienceGroup.isMaxLimitReached()).thenReturn(true).thenReturn(true);

        final Subject maths = new Subject(400L, "Maths");
        final Subject geography = new Subject(401L, "Geography");
        final Subject french = new Subject(404L, "French");

        studyClass.setSubjectGroupList(asList(commonGroup, scienceGroup));

        student.setStudyClass(studyClass);

        student.setSubjectList(asList(maths, geography, french));

        groupService.addStudentToSubjectGroup(student);

        assertThat(student.isPlaced(), is(false));
    }


    @Test
    public void shouldNotPlaceStudentIfSomeSubjectsMatchToAGroupButMaxCapacityOfOtherGroupsAreReached() {
        Student student = new Student(100L, "Praveen");

        StudyClass studyClass = new StudyClass(200L, "OneA");

        SubjectGroup commonGroup = new SubjectGroup(300L, "Common");
        SubjectGroup scienceGroup = Mockito.mock(SubjectGroup.class);

        when(scienceGroup.isMaxLimitReached()).thenReturn(true).thenReturn(true);

        final Subject maths = new Subject(400L, "Maths");
        final Subject geography = new Subject(401L, "Geography");
        final Subject french = new Subject(404L, "French");


        studyClass.setSubjectGroupList(asList(commonGroup, scienceGroup));

        student.setStudyClass(studyClass);

        student.setSubjectList(asList(maths, geography, french));

        groupService.addStudentToSubjectGroup(student);

        assertThat(student.isPlaced(), is(false));
    }

    @Test
    public void shouldPlaceStudentAndAssignedToTheGroupsIfMaxCapacityOfGroupsAreNotReached() {
        Student student1 = new Student(100L, "Praveen");

        StudyClass studyClass = new StudyClass(200L, "OneA");

        SubjectGroup commonGroup = new SubjectGroup(300L, "Common");
        SubjectGroup scienceGroup = new SubjectGroup(301L, "Science");
        SubjectGroup foreignLanguage1 = new SubjectGroup(302L, "ForeignLanguage1");
        SubjectGroup foreignLanguage2 = new SubjectGroup(303L, "ForeignLanguage2");

        final Subject maths = new Subject(400L, "Maths");
        final Subject geography = new Subject(401L, "Geography");
        final Subject physics = new Subject(402L, "Physics");
        final Subject chemistry = new Subject(403L, "Chemistry");
        final Subject french = new Subject(404L, "French");
        final Subject hindi = new Subject(405L, "Hindi");


        commonGroup.setSubjectList(asList(maths, geography));
        scienceGroup.setSubjectList(asList(physics, chemistry));
        foreignLanguage1.setSubjectList(asList(french));
        foreignLanguage2.setSubjectList(asList(hindi));

        studyClass.setSubjectGroupList(asList(commonGroup, scienceGroup, foreignLanguage1, foreignLanguage2));

        student1.setStudyClass(studyClass);
        student1.setSubjectList(asList(maths, geography, french));

        groupService.addStudentToSubjectGroup(student1);

        assertThat(student1.isPlaced(), is(true));
        assertThat(student1.getStudyClass().getSubjectGroupList().get(0).getStudentList().get(0), is(student1));
        assertThat(student1.getStudyClass().getSubjectGroupList().get(1).getStudentList(), hasSize(0));
        assertThat(student1.getStudyClass().getSubjectGroupList().get(2).getStudentList().get(0), is(student1));
        assertThat(student1.getStudyClass().getSubjectGroupList().get(3).getStudentList(), hasSize(0));
    }
}