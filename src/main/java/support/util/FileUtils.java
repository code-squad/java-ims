package support.util;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

public class FileUtils {
    public static String uploadFile(MultipartFile file, String uploadPath) throws IOException {
        String savedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String dirName = getCurrentUploadPath(uploadPath);
        File target = new File(dirName, savedFileName);
        FileCopyUtils.copy(file.getBytes(), target); // [내용 , 파일] | file.getBytes():byte의 배열(= String)(스트링보다 가벼움)
        return dirName;
    }

    // "uploadPath/2019/01/23/aaa.jpg" 형식으로 만들기
    public static String getCurrentUploadPath(String uploadRootPath) {
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);

//        uploadRootPath + File.separator + y + File.separator + formatDate(m) + File.separator + formatDate(d);
        return makeDir(uploadRootPath, String.valueOf(y), formatDate(m), formatDate(d));
    }

    private static String makeDir(String uploadRootPath, String... paths) { // string 배열 형식으로 들어옴
        for (String path : paths) {
            uploadRootPath += File.separator + path; // File.separator : 운영체제에 맞게 "/" 또는 "\"로 만들어 줌
        }
        return uploadRootPath;
    }

    private static String formatDate(int date) {
        return new DecimalFormat("00").format(date);
    }
}
