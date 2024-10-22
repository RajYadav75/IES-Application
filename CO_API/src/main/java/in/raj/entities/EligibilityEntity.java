package in.raj.entities;



import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "elig_dtls")
public class EligibilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer edgTraceId;

    private String  planId;

    private Long caseNum;

    private String planStatus;

    private Double benefitAmt;

    private LocalDate eligStartDate;

    private LocalDate eligEndDate;

    private String denialRsn;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Long getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(Long caseNum) {
        this.caseNum = caseNum;
    }

    public LocalDate getEligStartDate() {
        return eligStartDate;
    }

    public void setEligStartDate(LocalDate eligStartDate) {
        this.eligStartDate = eligStartDate;
    }

    public LocalDate getEligEndDate() {
        return eligEndDate;
    }

    public void setEligEndDate(LocalDate eligEndDate) {
        this.eligEndDate = eligEndDate;
    }

    public String getDenialRsn() {
        return denialRsn;
    }

    public void setDenialRsn(String denialRsn) {
        this.denialRsn = denialRsn;
    }



    public Double getBenefitAmt() {
        return benefitAmt;
    }

    public void setBenefitAmt(Double benefitAmt) {
        this.benefitAmt = benefitAmt;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public Integer getEdgTraceId() {
        return edgTraceId;
    }

    public void setEdgTraceId(Integer edgTraceId) {
        this.edgTraceId = edgTraceId;
    }
}
