package ist.school.sevice;

/**
 * Created by Praveen Gupta on 13/10/16.
 */
public class AdminService {
    private final StudentService studentService;

    public AdminService(StudentService studentService) {
        this.studentService = studentService;
    }


    public boolean notifyUnplacedStudentsToHeadmaster(){
        //Need to have some service which sends all unplaced students. Skkiping it as its not part of assignment
        // this.studentService.getAllUnplacedStudents();
        return true;
    }
}
