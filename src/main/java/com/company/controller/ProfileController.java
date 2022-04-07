package com.company.controller;

import com.company.dto.ProfileDto;
import com.company.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto dto) {
        log.info("Profile creating {}",dto);
        ProfileDto response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(ProfileDto dto) {
        log.info("Profile updating: {}",dto);
        if (dto==null){
            log.warn("Request can not be null!!! ");
        }
        profileService.update(dto);
        return ResponseEntity.ok("Succesfull");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProfileDto> getById(@PathVariable("id") Integer id) {
        log.info("get profile by Id: {}",id);
        ProfileDto dto = profileService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProfileDto>> getAll(){
        log.info("Get All Profile :");
        List<ProfileDto> dtoList=profileService.getall();
        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<?> deleteById(@RequestParam("id")Integer id){
        log.info("Profile is deleting by Id:{}",id);
        profileService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

}
