package ist.school.sevice;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Praveen Gupta on 13/10/16.
 */
public class AdminServiceTest {
    private AdminService adminService;
    private StudentService studentService;

    @Before
    public void setUp(){
        adminService = new AdminService(studentService);
    }

    @Test
    public void shouldReturnTrueIfUnplacedStudentsAreNotifiedToHeadmaster(){
        assertThat(adminService.notifyUnplacedStudentsToHeadmaster(), is(true));
    }
}