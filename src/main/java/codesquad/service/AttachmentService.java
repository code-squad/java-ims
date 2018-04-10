package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.web.AttachmentController;
import jdk.nashorn.internal.objects.NativeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    private static final Logger log = LoggerFactory.getLogger(AttachmentService.class);

    @Autowired
    AttachmentRepository attachmentRepository;

    public Iterable<Attachment> findAll(){
        return attachmentRepository.findAll();
    }

    public Attachment findById(long id){
        return attachmentRepository.findOne(id);
    }

    public  List<Attachment> findByFileName(String fileName){
        return attachmentRepository.findByFileName(fileName);
    }

    public Attachment saveFile(MultipartFile file) throws Exception{

        String oldName = file.getOriginalFilename();
        String dbName = oldName;

        List<Attachment> attachments = findByFileName(oldName);
        if(attachments.size() > 0){
            dbName = renameFile(attachments, oldName);
        }
        String fileName = new String(dbName.getBytes("UTF-8"));
        String savePath = "C:\\attachments/";
        File myFile = new File(savePath + dbName);
        file.transferTo(myFile);


        Attachment attachment = new Attachment(oldName, myFile.getPath());
        return attachmentRepository.save(attachment);
    }

    private String renameFile(List<Attachment> attachments, String oldName) {
        String[] exts = oldName.split("\\.");
        long index = 0;
        for (int i =  attachments.size()-1; i > 0 ;i--) {
            if(attachments.get(i).isSameName(oldName)){
                index = attachments.size();
                break;
            }
        }
        return oldName.substring(0, oldName.length()-4) + "(" + Long.toString(index) + ")."+ exts[exts.length-1];
    }
}
