package ist.school.sevice;

import ist.school.domain.Student;
import ist.school.domain.Subject;
import ist.school.domain.SubjectGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class SubjectGroupService {

    /**
     *
     * @param studentToBeAdded
     */
    public void addStudentToSubjectGroup(Student studentToBeAdded){
        if(!studentToBeAdded.isPlaced()) {
           // Assuming that student has already been assigned to a Class
            validateAndPlaceStudent(studentToBeAdded);
        }
    }

    private void validateAndPlaceStudent(Student studentToBeAdded) {
        final List<SubjectGroup> subjectGroupList = studentToBeAdded.getStudyClass().getSubjectGroupList();
        List<SubjectGroup> subjectGroupsBelongToStudent = new ArrayList<>();
        List<Subject> subjectsToBeMatched = new ArrayList<>(studentToBeAdded.getSubjectList());

        for (SubjectGroup subjectGroup : subjectGroupList) {
            if (!subjectGroup.isMaxLimitReached()) {
                List<Subject> unMatchedSubjects = subjectGroup.getUnMatchedSubjects(subjectsToBeMatched);
                if(subjectsToBeMatched.size() > unMatchedSubjects.size()) {
                    subjectGroupsBelongToStudent.add(subjectGroup);
                    subjectsToBeMatched = unMatchedSubjects;
                }
                if (unMatchedSubjects.size() == 0){
                    studentToBeAdded.setPlaced(true);
                    break;
                }
            }
        }

        assignStudentToSubjectGroups(studentToBeAdded, subjectGroupList, subjectGroupsBelongToStudent);
    }

    private void assignStudentToSubjectGroups(Student studentToBeAdded, List<SubjectGroup> subjectGroupList, List<SubjectGroup> subjectGroupsBelongToStudent) {
        if(studentToBeAdded.isPlaced()){
            subjectGroupList.forEach(subjectGroup -> {
                subjectGroupsBelongToStudent.forEach(subjectGroupBelongToStudent -> {
                    if (subjectGroup.equals(subjectGroupBelongToStudent)){
                        subjectGroup.addStudent(studentToBeAdded);
                    }
                });
            });
        }
    }

    //min
}
