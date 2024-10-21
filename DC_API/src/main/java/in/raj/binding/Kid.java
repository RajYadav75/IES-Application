package in.raj.binding;

import java.time.LocalDate;

public class Kid {


    private String kidName;

    private LocalDate kidDOB;

    private Long kidSsn;

    public Long getKidSsn() {
        return kidSsn;
    }

    public void setKidSsn(Long kidSsn) {
        this.kidSsn = kidSsn;
    }

    public LocalDate getKidDOB() {
        return kidDOB;
    }

    public void setKidDOB(LocalDate kidDOB) {
        this.kidDOB = kidDOB;
    }

    public String getKidName() {
        return kidName;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }

}
