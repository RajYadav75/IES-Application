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

    public Integer getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Integer incomeId) {
        this.incomeId = incomeId;
    }

    public Double getSalaryIncome() {
        return salaryIncome;
    }

    public void setSalaryIncome(Double salaryIncome) {
        this.salaryIncome = salaryIncome;
    }

    public Double getRentIncome() {
        return rentIncome;
    }

    public void setRentIncome(Double rentIncome) {
        this.rentIncome = rentIncome;
    }

    public Double getPropertyIncome() {
        return propertyIncome;
    }

    public void setPropertyIncome(Double propertyIncome) {
        this.propertyIncome = propertyIncome;
    }
}
