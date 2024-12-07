package com.wekids.backend.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.wekids.backend.config.S3Config;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AmazonS3Manager {
    private final AmazonS3 amazonS3;
    private final S3Config s3Config;

    public String uploadFile(String keyName, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType("image/jpeg");
        try {
            amazonS3.putObject(
                    new PutObjectRequest(
                            s3Config.getBucket(), keyName, file.getInputStream(), metadata));
        } catch (IOException e) {
            throw new WekidsException(ErrorCode.FAILED_SAVE_IMAGE, "");
        }

        return amazonS3.getUrl(s3Config.getBucket(), keyName).toString();
    }

    public String uploadLogFile(String keyName, File logFile) {
        try (FileInputStream fileInputStream = new FileInputStream(logFile)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(logFile.length());
            metadata.setContentType("text/plain");

            amazonS3.putObject(new PutObjectRequest(
                    s3Config.getBucket(),
                    keyName,
                    fileInputStream,
                    metadata
            ));

            return amazonS3.getUrl(s3Config.getBucket(), keyName).toString();
        } catch (IOException e) {
            throw new WekidsException(ErrorCode.FAILED_SAVE_LOG, "");
        }
    }

    public String makeKeyName(Filepath... filepaths) {
        String uuid = UUID.randomUUID().toString();

        StringBuilder pathBuilder = new StringBuilder();
        for (Filepath filepath : filepaths) {
            pathBuilder.append(filepath.toString()).append('/');
        }

        return pathBuilder.append(uuid).toString();
    }


    public void deleteFile(String keyName) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(s3Config.getBucket(), keyName));
        } catch (Exception e) {
            throw new WekidsException(ErrorCode.FAILED_DELETE_IMAGE, "");
        }
    }
}
