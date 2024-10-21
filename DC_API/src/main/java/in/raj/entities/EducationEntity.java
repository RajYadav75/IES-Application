package in.raj.entities;

import javax.persistence.*;

@Entity
@Table(name = "DC_EDUCATION")
public class EducationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer educationId;
    private String highestDegree;
    private Integer graduationYear;
    private String uniName;
    private Integer caseNum;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
