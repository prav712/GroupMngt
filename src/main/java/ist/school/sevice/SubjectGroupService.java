package ist.school.sevice;

import ist.school.domain.Student;
import ist.school.domain.Subject;
import ist.school.domain.SubjectGroup;
import ist.school.repository.SubjectGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class SubjectGroupService {
    private SubjectGroupRepository subjectGroupRepository;
    private StudentService studentService;

    public SubjectGroupService(StudentService studentService, SubjectGroupRepository subjectGroupRepository) {
        this.studentService = studentService;
        this.subjectGroupRepository = subjectGroupRepository;
    }

    /**
     * @return list of subject groups which has min capacity of students are not reached
     */
    public List<SubjectGroup> fetchAllSubjectGroupsWhichHasMinCapacityNotReached(){
        return subjectGroupRepository.getAllSubjectGroups()
                .stream()
                .filter(SubjectGroup::isMinLimitReached)
                .collect(Collectors.toList());
    }

    /**
     * Adds students to the corresponding subject groups if max capacity of subjects groups are not reached.
     * If a student with maths and french subjects has been assigned to a maths subject group but max capacity of
     * french group are already reached then student will not be assigned to any subject groups and remained unplaced.
     * @param studentId id of student to be added
     */
    public void addStudentToSubjectGroup(Long studentId){
        Student studentToBeAdded = studentService.findStudentByStudentId(studentId);

        if(!studentToBeAdded.isPlaced()) {
            validateAndPlaceStudent(studentToBeAdded);
        }
    }

    private void validateAndPlaceStudent(Student studentToBeAdded) {
        // Assuming that student has already been assigned to a Class and Class has list of
        // corresponding subject groups.
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
                    //a student will only be placed if he has been assigned to all corresponding
                    //subject groups
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
}
