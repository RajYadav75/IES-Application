package in.raj.service;

import in.raj.entities.CoNoticeEntity;
import in.raj.repo.CoNoticeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoServiceImpl implements CoService{
    @Autowired
    private CoNoticeRepo noticeRepo;
    @Override
    public void processPendingTriggers() {
        // get All Pending Records from co_notices tables
        List<CoNoticeEntity> records = noticeRepo.findByNoticeStatus("P");



    }

    private void processEachRecord(CoNoticeEntity entity){
        Long caseNum = entity.getCaseNum();
    }
}
