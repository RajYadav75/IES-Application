package in.raj.entities;

import javax.persistence.Entity;

@Entity

public class CoNoticeEntity {

    private String noticeStatus;

    private Long caseNum;

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Long getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(Long caseNum) {
        this.caseNum = caseNum;
    }
}
