package in.raj.service;

import in.raj.binding.*;

import java.util.Collections;
import java.util.Map;

public class DcImpl implements DcService{
    @Override
    public Map<Integer, String> getPlans() {
        return Collections.emptyMap();
    }

    @Override
    public boolean savePlanSelection(PlanSelection planSel) {
        return false;
    }

    @Override
    public boolean saveIncome(Income income) {
        return false;
    }

    @Override
    public boolean saveEducation(Education education) {
        return false;
    }

    @Override
    public boolean saveKids(Kids kids) {
        return false;
    }

    @Override
    public DcSummary fetchSummaryInfo(Long caseNum) {
        return null;
    }
}
