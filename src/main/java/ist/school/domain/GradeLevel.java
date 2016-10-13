package ist.school.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class GradeLevel extends PersistentEntity {
    private String grade;

    private List<StudyClass> studyClassList = new ArrayList<>();
    private List<SubjectGroup> groupList = new ArrayList<>();

    public List<SubjectGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<SubjectGroup> groupList) {
        this.groupList = groupList;
    }

    public List<StudyClass> getStudyClassList() {
        return studyClassList;
    }

    public void setStudyClassList(List<StudyClass> studyClassList) {
        this.studyClassList = studyClassList;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
