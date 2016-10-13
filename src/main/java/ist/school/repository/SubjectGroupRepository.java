package ist.school.repository;

import ist.school.domain.Student;
import ist.school.domain.SubjectGroup;

import java.util.List;

/**
 * Created by Praveen Gupta on 13/10/16.
 */
public interface SubjectGroupRepository {
    List<SubjectGroup> getAllSubjectGroups();
}
