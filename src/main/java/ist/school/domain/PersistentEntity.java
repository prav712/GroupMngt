package ist.school.domain;

/**
 * Created by Praveen Gupta on 12/10/16.
 */
public abstract class PersistentEntity {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        if(this.id == null){
            this.id = id;
        } else {
            throw new IllegalStateException("Id is already set for " + id);
        }
    }
}

