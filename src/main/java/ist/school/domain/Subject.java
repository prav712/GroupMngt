package ist.school.domain;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public class Subject extends PersistentEntity {
    private String subjectName;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Subject(Long id, String subjectName) {
        this.setId(id);
        this.subjectName = subjectName;
    }

    @Override
    public boolean equals(Object object){
        return (object instanceof Subject) &&
                this.getId().equals(((Subject) object).getId());
    }
}
