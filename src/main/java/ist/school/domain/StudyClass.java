package ist.school.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class StudyClass extends PersistentEntity{
    private String className;

    private List<GradeLevel> gradeList;
    private List<Student> studentList;
    private List<SubjectGroup> subjectGroupList;

    public StudyClass(Long id, String className) {
        this.setId(id);
        this.className = className;
        this.gradeList = new ArrayList<>();
        this.studentList = new ArrayList<>();
        this.subjectGroupList = new ArrayList<>();
    }

    public List<GradeLevel> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<GradeLevel> gradeList) {
        this.gradeList = gradeList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<SubjectGroup> getSubjectGroupList() {
        return subjectGroupList;
    }

    public void setSubjectGroupList(List<SubjectGroup> subjectGroupList) {
        this.subjectGroupList = subjectGroupList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
