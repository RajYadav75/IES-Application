package in.raj.bindings;

import lombok.*;

import java.time.LocalDate;

@Data
public class UserAccountForm {
    private String fullName;
    private String email;
    private String pwd;
    private Long mobileNo;
    private LocalDate dob;
    private Long ssn;
    private String activeSw;
    private String accStatus;
    private Integer roleId;
}
