package in.raj.bindings;

import lombok.Data;

@Data
public class DashboardCard {
    private Long plansCnt;
    private Long approvedCnt;
    private Long deniedCnt;
    private Double benifitAmtGiven;

    private UserAccountForm user;
}
