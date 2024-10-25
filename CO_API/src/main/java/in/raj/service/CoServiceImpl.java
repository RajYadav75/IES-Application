package in.raj.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import in.raj.entities.CoNoticeEntity;
import in.raj.entities.EligibilityEntity;
import in.raj.repo.CoNoticeRepo;
import in.raj.repo.CoTriggerRepo;
import in.raj.repo.EligRepo;
import in.raj.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;


@Service
public class CoServiceImpl implements CoService{
    @Autowired
    private CoNoticeRepo noticeRepo;
    @Autowired
    private EligRepo eligRepo;
    @Autowired
    private DcCaseRepo dcCaseRepo;
    @Autowired
    private CitizenAppRepo appRepo;

    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private CoTriggerRepo coTriggerRepo;
    @Autowired
    private AmazonS3 s3;
    @Value("${bucketName}")
    private String bucketName;

    @Override
    public CoResponse processPendingTriggers()throws Exception {
        // Fetch All Pending triggers from co_notices tables
        List<CoTriggerEntity> pendingTrgs = coTriggerRepo.findByTrgStatus("Pending");

        for (CoTriggerEntity trigger : pendingTrgs){
            processEachRecord(trigger);
        }
        return null;
    }

    private void processEachRecord(CoNoticeEntity entity){

        Long caseNum = entity.getCaseNum();
        // Get eligibility data

        EligibilityEntity elig = eligRepo.findByCaseNum(caseNum);
        String planStatus = elig.getPlanStatus();

        File pdfFile = null;

        if ("AP".equals(planStatus)){

            pdfFile = generateAndSendApPdf(elig,appEntity);
        } else if ("DN".equals(planStatus)) {
            pdfFile =generateAndSendDnPdf(elig,appEntity);
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

    private String storePdfInS3(File file) {
        // Logic to store in S3

        PutObjectResult putObjectResult = s3.putObject(bucketName,file.getName(),file);
        URL url = s3.getUrl(bucketName,file.getName());
        return url.toExternalForm();
    }

    private CitizenAppEntity processTrigger(CoTriggerEntity entity) throws Exception{
        CitizenAppEntity appEntity = null;
        //get Eligiblity data based on casenum

        EligDtlsEntity elig = eligRepo.findByCaseNum(entity.getCaseNum());
        //Get citizen data based on case num
        Optional<DcCaseEntity> findById = dcCaseRepo.findById(entity.getCaseNum());
        if (findById.isPresent()){
            DcCaseEntity dcCaseEntity = findById.get();
            Integer appId = dcCaseEntity.getAppId();
            Optional<CitizenAppEntity> appEntityOptional = appRepo.findById(appId);
            if (appEntityOptional.isPresent()){
                appEntity = appEntityOptional.get();
            }
        }
        String planStatus = elig.getPlanStatus();
        File file = null;
        if ("AP".equals(planStatus)){
            file =generateAndSendApPdf(elig,appEntity);
        }else if ("DN".equals(planStatus)){
            file = generateAndSendDnPdf(elig,appEntity);
        }
        String objUrl = uploadToS3(file);
        updateTrigger(elig.getCaseNum(),objUrl);

        return appEntity;
    }

    private File generateAndSendDnPdf(EligDtlsEntity elig, CitizenAppEntity appEntity)throws Exception {
        Document document = new Document(PageSize.A4);
         File file = new File(eligData.getCaseNum()+".pdf");
         FileOutputStream fos = null;
         try {
             fos = new FileOutputStream(file);
         }catch (FileNotFoundException e){
             e.printStackTrace();
         }
         PdfWriter.getInstance(document,fos);
         document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);


        Paragraph p = new Paragraph("Eligibility Report ",font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f,3.5f,3.0f,1.5f)};
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Citizen Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Status",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Denial Reason",font));
        table.addCell(cell);

        table.addCell(appEntity.getFullName());
        table.addCell(eligData.getPlanName());
        table.addCell(eligData.getPlanStatus());
        table.addCell(eligData.getPlanStartDate()+" ");
        table.addCell(eligData.getPlanEndDate()+" ");
        table.addCell(eligData.getBenefitAmt()+" ");
        table.addCell(eligData.getDenialReason()+" ");


        document.add(table);

        document.close();

        String subject = "HIS Eligiblity Info";
        String body = "HIS Eligiblity Info";

        emailUtils.sendEmail(appEntity.getEmail(),subject,body,file);
        return file;

    }

    private String uploadToS3(File file){
        PutObjectResult putObjectResult = s3.putObject(bucketName,file.getName(),file);
        URL url = s3.getUrl(bucketName,file.getName());
        return url.toExternalForm();
    }


    private void updateTrigger(Long caseNum, String objUrl)throws Exception{
        CoTriggerEntity coEntity = coTriggerRepo.findByCaseNum(caseNum);
        coEntity.setNoticeUrl(objUrl);
        coEntity.setTrgStatus("C");
        coTriggerRepo.save(coEntity);
    }

    private File generateAndSendApPdf(ELigDtlsEntity eligData,CitizenAppEntity appEntity) throws Exception{
        Document document = new Document(PageSize.A4);
        File file = new File(eligData.getCaseNum()+".pdf");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        PdfWriter.getInstance(document,fos);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);


        Paragraph p = new Paragraph("Eligibility Report ",font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f,3.5f,3.0f,1.5f,3.0f,1.5f});
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Citizen Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Status",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Start Date",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Benefit Amount",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Denial Reason",font));
        table.addCell(cell);

        table.addCell(appEntity.getFullName());
        table.addCell(eligData.getPlanName());
        table.addCell(eligData.getPlanStatus());
        table.addCell(eligData.getPlanStartDate()+" ");
        table.addCell(eligData.getPlanEndDate()+" ");
        table.addCell(eligData.getBenefitAmt()+" ");


        document.add(table);

        document.close();

        String subject = "HIS Eligiblity Info";
        String body = "HIS Eligiblity Info";

        emailUtils.sendEmail(appEntity.getEmail(),subject,body,file);
        return file;
    }
}
