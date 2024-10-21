package in.raj.service;

import in.raj.binding.*;

import java.util.Map;

public interface DcService {

    public Map<Integer,String> getPlans();

    public boolean savePlanSelection(PlanSelection planSel);

    public boolean saveIncome(Income income);

    public boolean saveEducation(Education education);

    public boolean saveKids(Kids kids);

    public DcSummary fetchSummaryInfo(Long caseNum);
}
