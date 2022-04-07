package com.company.service;

import com.company.dto.ProfileDto;
import com.company.entity.ProfileEntity;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.utils.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDto create(ProfileDto profile) {
        if (profileRepository.existsByPassportNumber(profile.getPassportNumber())) {
            log.warn("This Password number allready exists:{} ",profile.getPassportNumber());
            throw new BadRequestException("Password number allready exists");
        }
        if (!CheckUtil.checkPhone(profile.getPhone())) {
            log.warn("Phone is invalid : {}",profile.getPhone());
            throw new BadRequestException("Phone is invalid");
        }
        if (!CheckUtil.checkPassportNumber(profile.getPassportNumber())) {
            log.warn("Passport number is invalid : {}",profile.getPassportNumber());
            throw new BadRequestException("Passport number is invalid");
        }
        ProfileEntity prof = new ProfileEntity();
        prof.setFirstName(profile.getFirstName());
        prof.setLastName(profile.getLastName());
        prof.setMiddleName(profile.getMiddleName());
        prof.setBirthDate(profile.getBirthDate());
        prof.setPhone(profile.getPhone());
        prof.setPassportNumber(profile.getPassportNumber());
        profileRepository.save(prof);
        profile.setCreatedDate(prof.getCreatedDate());
        profile.setId(prof.getId());
        return profile;
    }

    public ProfileEntity get(Integer profileId) {
        return this.profileRepository.findById(profileId).orElseThrow(() ->
            new ItemNotFoundException("Profile Not Found"));
    }

    public ProfileDto getById(Integer id) {
        ProfileEntity entity = get(id);
        return toDto(entity);
    }

    private ProfileDto toDto(ProfileEntity entity) {
        ProfileDto dto = new ProfileDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setLastName(entity.getLastName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setPhone(entity.getPhone());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPassportNumber(entity.getPassportNumber());
        return dto;
    }

    public void update(ProfileDto dto) {
        if (dto.getId() == null) {
            log.warn("Id can not be null");
            throw new BadRequestException("Id can not be null");
        }
        ProfileEntity entity = get(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPhone(dto.getPhone());
        profileRepository.save(entity);
    }

    public void deleteById(Integer id){
        if (profileRepository.existsById(id)){
            profileRepository.deleteById(id);
        }else {
            log.warn("Profile not found");
            throw new ItemNotFoundException("Profile not found");
        }
    }

    public List<ProfileDto> getall(){
        return profileRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
