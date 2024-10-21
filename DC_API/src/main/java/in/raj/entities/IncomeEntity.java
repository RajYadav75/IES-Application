package in.raj.entities;

import javax.persistence.*;

@Entity
@Table(name = "DC_INCOME")
public class IncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incomeId;
    private Double salaryIncome;
    private Double rentIncome;
    private Double propertyIncome;

}
