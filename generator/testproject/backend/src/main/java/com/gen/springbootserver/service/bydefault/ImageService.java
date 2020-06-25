package com.gen.springbootserver.service.bydefault;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.gen.springbootserver.config.GgProperties;
import com.gen.springbootserver.dto.errors.CommonHttpException;
import com.gen.springbootserver.dto.errors.TokenValidationException;
import com.gen.springbootserver.mybatis.dao.ImageMapper;
import com.gen.springbootserver.mybatis.model.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    AuthenticationTokenService authenticationTokenService;
    private String defaultImage;

    @Autowired
    public ImageService(GgProperties properties) {
        defaultImage = properties.getUser().getDefaultImage();
    }

    public void insertImage(Image image) {
        imageMapper.insert(image);
    }

    public void del(Long userid) {
        imageMapper.deleteByPrimaryKey(userid);
    }

    public Image updateUserImageById(Long id, String baseString) {
        Image image = imageMapper.selectByPrimaryKey(id);
        if (image != null) {
            new RuntimeException("image is not exist");
        }
        Image existingImage;
        if (baseString != null) {
            existingImage = convertBaseStringToImage(baseString);
        } else {
            existingImage = new Image();
        }
        existingImage.setId(id);
        imageMapper.insert(existingImage);

        return existingImage;
    }

    public Image getImageById(Long id, String token) {
        try {
            authenticationTokenService.createToken(token);
        } catch (TokenValidationException e) {
            throw new CommonHttpException("Access token wasn't found", HttpStatus.NOT_FOUND);
        }

        Image existingImage = imageMapper.selectByPrimaryKey(id);
        if (existingImage != null) {
            new RuntimeException("image is not exist");
        }
        if (existingImage.getImage() == null) {
            existingImage = convertBaseStringToImage(defaultImage);
            existingImage.setId(id);
        }
        return existingImage;
    }

    private Image convertBaseStringToImage(String baseString) {
        Image userImage = new Image();
        byte[] decodedString = Base64.getDecoder().decode(baseString.getBytes(StandardCharsets.UTF_8));
        userImage.setImage(decodedString);
        return userImage;
    }
}