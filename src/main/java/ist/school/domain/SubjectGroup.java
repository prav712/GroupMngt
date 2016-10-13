package ist.school.domain;

import ist.school.Common.SchoolConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class SubjectGroup extends PersistentEntity {
    private String groupName;

    private List<Subject> subjectList;
    private List<StudyClass> classList;
    private List<Student> studentList;

    public SubjectGroup(Long id, String groupName) {
        this.setId(id);
        this.groupName = groupName;
        this.studentList = new ArrayList<>();
        this.classList = new ArrayList<>();
        this.subjectList = new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    /**
     * @return <tt>true</tt> if subjectGroup has reached maximum number of students
     */
    public boolean isMaxLimitReached() {
        return this.getStudentList().size() >= SchoolConstants.MAX_LIMIT;
    }

    /**
     * @return <tt>true</tt> if subjectGroup has reached minimum number of students
     */
    public boolean isMinLimitReached() {
        return this.getStudentList().size() >= SchoolConstants.MIN_LIMIT;
    }


    /**
     * Removes from given list all of its elements that are contained in the
     * this list.
     *
     * @param subjectList given list
     * @return unMatchedSubjects list with unmatched elements
     **/
    public List<Subject> getUnMatchedSubjects(List<Subject> subjectList) {
        List<Subject> unMatchedSubjects = new ArrayList<>(subjectList);
        unMatchedSubjects.removeAll(this.subjectList);
        return unMatchedSubjects;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof SubjectGroup) &&
                this.getId().equals(((SubjectGroup) object).getId());
    }

    public List<StudyClass> getClassList() {
        return classList;
    }

    public void setClassList(List<StudyClass> classList) {
        this.classList = classList;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
