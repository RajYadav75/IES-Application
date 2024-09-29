package in.raj.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "elig_dtls")
public class EligibilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer edgTraceId;

    private String planStatus;

    private Double benefitAmt;
}
