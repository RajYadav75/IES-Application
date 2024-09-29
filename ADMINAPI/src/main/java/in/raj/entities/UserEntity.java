package in.raj.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name ="ies_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String fullName;
    private String email;
    private String pwd;
    private Long mobileNo;
    private String gender;
    private LocalDate dob;
    private Long ssn;
    private String activeSw;
    private String accStatus;
    private Integer roleId;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PlanEntity> plans;

}
