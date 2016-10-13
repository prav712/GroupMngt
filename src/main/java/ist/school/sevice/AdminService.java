package ist.school.sevice;

import ist.school.domain.Student;
import ist.school.domain.Subject;

import java.util.List;

/**
 * Created by Praveen Gupta on 13/10/16.
 */
public class AdminService {
    private StudentService studentService;
    private SubjectGroupService subjectGroupService;

    public AdminService(StudentService studentService, SubjectGroupService subjectGroupService) {
        this.studentService = studentService;
        this.subjectGroupService = subjectGroupService;
    }

    /**
     * unplace students who belong to a group which has min student capacity is not reached
     */
    public void unplaceStudentsOfGroupsWithHasMinLimitIsNotReached(){

        // If a student is unplaced from a group because of min limit rule
        // then that student should also be removed from corresponding other groups and again need to check
        // if min limit of another groups are reached.
        // Not implementing above logic

        subjectGroupService.fetchAllSubjectGroupsWhichHasMinCapacityNotReached()
                .forEach(subjectGroup -> {
                        subjectGroup.getStudentList().forEach(student -> {
                            student.setPlaced(false);
                        });
                });
    }

    /**
     * Notify students who are not placed in any groups
     * @return unplaced students
     */
    public List<Student> notifyUnplacedStudentsToHeadmaster(){
       return this.studentService.getAllUnplacedStudents();
    }


    /**
     *  Assign subjects to the student
     * @param studentId id of student
     * @param subjectList subjects to be added
     */
    public void addSubjectsToTheStudent(long studentId, List<Subject> subjectList){
        studentService.findStudentByStudentId(studentId).setSubjectList(subjectList);
    }

    //need to have following more methods
    //assign class to student
    //assign grade to student

    //assign grades/grade to class and vice versa
    //assign subjects to group
    //etc
}
