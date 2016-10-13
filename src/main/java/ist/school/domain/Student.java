package ist.school.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class Student extends PersistentEntity {
    private String studentName;

    private boolean isPlaced;

    private StudyClass studyClass;
    private List<Subject> subjectList;
    private List<GradeLevel> gradeList;

    public Student(Long id, String studentName) {
        this.setId(id);
        this.studentName = studentName;
        this.subjectList = new ArrayList<>();
        this.gradeList  = new ArrayList<>();
        this.isPlaced = false;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }
    public StudyClass getStudyClass() {
        return studyClass;
    }

    public void setStudyClass(StudyClass studyClass) {
        this.studyClass = studyClass;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public List<GradeLevel> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<GradeLevel> gradeList) {
        this.gradeList = gradeList;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}
