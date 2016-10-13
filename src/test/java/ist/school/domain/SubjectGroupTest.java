package ist.school.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Praveen Gupta on 13/10/16.
 */
public class SubjectGroupTest {

    private SubjectGroup subjectGroup;

    @Before
    public void setUp() {
        subjectGroup = new SubjectGroup(1L, "Common");
    }

    @Test
    public void shouldReturnTrueIfMaxLimitOfGroupIsReached() {
        subjectGroup.setStudentList(asList(mock(Student.class), mock(Student.class),
                mock(Student.class), mock(Student.class),
                mock(Student.class), mock(Student.class),
                mock(Student.class), mock(Student.class),
                mock(Student.class), mock(Student.class)));

        assertThat(subjectGroup.isMaxLimitReached(), is(true));
    }

    @Test
    public void shouldRemoveCommonSubjectsFromSubjectListWhichMatchWithSujectsOfGroup() {
        SubjectGroup commonGroup = new SubjectGroup(300L, "Common");

        final Subject maths = new Subject(400L, "Maths");
        final Subject geography = new Subject(401L, "Geography");
        final Subject french = new Subject(404L, "French");

        commonGroup.setSubjectList(asList(maths, geography));

        final List<Subject> unMatchedSubjects = commonGroup.getUnMatchedSubjects(asList(maths, geography, french));

        assertThat(unMatchedSubjects, hasSize(1));
        assertThat(unMatchedSubjects.get(0), is(french));
    }

}