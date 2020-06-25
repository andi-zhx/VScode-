package com.gen.springbootserver.config;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;
import com.gen.springbootserver.dto.user.UserDTO;
import com.gen.springbootserver.mybatis.model.Role;
import com.gen.springbootserver.mybatis.model.User;

@Configuration
public class ConfigDTO {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        configModelMapper(modelMapper);
        return modelMapper;
    }

    private void configModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        configUserToUserDTOMapper(modelMapper);
        configUserDTOToUserMapper(modelMapper);

        // configTestgenDTOToTestgenMapper(modelMapper);
        // configTestgenToTestgenDTOMapper(modelMapper);
    }

    private void configUserToUserDTOMapper(ModelMapper modelMapper) {
        Converter<Set<Role>, Set<String>> converter = ctx -> ctx.getSource() == null ? null
                : ctx.getSource().stream().map(Role::getName).collect(Collectors.toSet());
        modelMapper.typeMap(User.class, UserDTO.class)
                .addMappings(mapper -> mapper.using(converter).map(User::getRoles, UserDTO::setRoles));
    }

    private void configUserDTOToUserMapper(ModelMapper modelMapper) {
        Converter<Set<String>, Set<Role>> converter = ctx -> ctx.getSource() == null ? null
                : ctx.getSource().stream().map(roleName -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return role;
                }).collect(Collectors.toSet());
        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper -> mapper.using(converter).map(UserDTO::getRoles, User::setRoles));
    }

    
    // private void configTestgenToTestgenDTOMapper(ModelMapper modelMapper) {
    //     Converter<byte[], String> toUppercase = new AbstractConverter<byte[], String>() {
    //         protected String convert(byte[] source) {
    //             String a = null;
    //             if (source.length > 0) {
    //                 a = new String(source);
    //             }
    //             return source == null ? null : a;
    //         }
    //     };
    //     modelMapper.typeMap(Testgen.class, TestgenDTO.class).addMappings(mapper -> 
    //         mapper.using(toUppercase).map(Testgen::getBlobCol, TestgenDTO::setBlobColBase64));
    // }

    // private void configTestgenDTOToTestgenMapper(ModelMapper modelMapper) {
    //     Converter<String, byte[]> toUppercase = new AbstractConverter<String, byte[]>() {
    //         protected byte[] convert(String source) {
    //             byte[] decodedString = Base64.getDecoder().decode(source.getBytes(StandardCharsets.UTF_8));
    //             return source == null ? null : decodedString;
    //         }
    //     };
    //     modelMapper.typeMap(TestgenDTO.class, Testgen.class).addMappings(
    //             mapper -> mapper.using(toUppercase).map(TestgenDTO::getBlobColBase64, Testgen::setBlobCol));
    // }

}
