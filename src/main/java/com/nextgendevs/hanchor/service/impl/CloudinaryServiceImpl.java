package com.nextgendevs.hanchor.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import com.nextgendevs.hanchor.io.repository.UserRepository;
import com.nextgendevs.hanchor.service.CloudinaryService;
import com.nextgendevs.hanchor.ui.model.response.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinaryConfig;

    @Autowired
    UserRepository userRepository;

    public CloudinaryServiceImpl(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Override
    public String uploadFile(MultipartFile file, String userId, String imageId) {

        checkUserEntity(userId);

        try {
            File uploadedFile = convertMultiPartToFile(file);
//            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            Map uploadResult = cloudinaryConfig.uploader()
                    .upload(
                            uploadedFile,
                            ObjectUtils.asMap(
                                    "folder", "hanchor/production/"+userId,
                                    "public_id", imageId,
                                    "use_filename", true,
                                    "unique_filename", true
                            )
                    );

            boolean isDeleted = uploadedFile.delete();

            if (isDeleted) {
                System.out.println("File successfully deleted");
            } else
                System.out.println("File doesn't exist");

            return uploadResult.get("url").toString();

        } catch (Exception e) {
            throw new HanchorServiceException(ErrorMessages.ERROR_CONVERTING_IMAGE.getErrorMessage());
        }
    }

    @Override
    public String destroyFile(String userId, String public_id)  {
        checkUserEntity(userId);

        try {
            String folder = "hanchor/production/" + userId + "/";
            Map destroyResult = cloudinaryConfig.uploader().destroy(folder + public_id, ObjectUtils.emptyMap());
            return destroyResult.get("result").toString();
        } catch (IOException e) {
            throw new HanchorServiceException(ErrorMessages.ERROR_DELETING_IMAGE.getErrorMessage());
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void checkUserEntity(String query) {
        UserEntity userEntity = userRepository.findUserEntitiesByUserId(query);

        if (userEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
    }
}
