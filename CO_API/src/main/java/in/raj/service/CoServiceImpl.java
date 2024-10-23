package in.raj.service;

import in.raj.entities.CoNoticeEntity;
import in.raj.entities.EligibilityEntity;
import in.raj.repo.CoNoticeRepo;
import in.raj.repo.EligRepo;
import in.raj.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class CoServiceImpl implements CoService{
    @Autowired
    private CoNoticeRepo noticeRepo;
    @Autowired
    private EligRepo eligRepo;

    @Autowired
    private EmailUtils emailUtils;
    @Override
    public void processPendingTriggers() {
        // get All Pending Records from co_notices tables
        List<CoNoticeEntity> records = noticeRepo.findByNoticeStatus("P");

        for (CoNoticeEntity entity : records){
            processEachRecord(entity);
        }



    }

    private void processEachRecord(CoNoticeEntity entity){
        Long caseNum = entity.getCaseNum();
        // Get eligibility data

        EligibilityEntity elig = eligRepo.findByCaseNum(caseNum);
        String planStatus = elig.getPlanStatus();

        File pdfFile = null;

        if ("AP".equals(planStatus)){

            pdfFile = generatedApprovedNotice(elig);
        } else if ("DN".equals(planStatus)) {
            pdfFile =generateDeniedNotice(elig);
        }

        String fileUrl = storePdfInS3(pdfFile);

        boolean isUpdate = updateProcessedRecord(entity,fileUrl);

        if (isUpdate){
            emailUtils.sendEmail("","","",pdfFile);
        }

    }

    private boolean updateProcessedRecord(CoNoticeEntity entity, String fileUrl) {
        // Set Status as Completed

        entity.setNoticeStatus("H");
        entity.setNoticeUrl(fileUrl);
        noticeRepo.save(entity);

        // set Notice S3 Object URL

        //Update Record in DB
        return true;
    }

    private String storePdfInS3(File pdfFile) {
        // Logic to store in S3
        return null;
    }

    private File generatedApprovedNotice(EligibilityEntity elig) {
        // generate pdf file
        return null;
    }
    private File generateDeniedNotice(EligibilityEntity elig) {
        // generate pdf file
        return null;
    }


}
