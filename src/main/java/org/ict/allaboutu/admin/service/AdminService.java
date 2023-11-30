package org.ict.allaboutu.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.domain.Admin;
import org.ict.allaboutu.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;

//    public Header<List<AdminDto>> getMemberList(Pageable pageable) {
////        List<Admin> memberEntities = adminRepository.findAll();
////        List<AdminDto> memberDtoList = new ArrayList<>();
//
//        List<AdminDto> adminDtos = new ArrayList<>();
//        Page<Admin> adminEntites = AdminRepository.findAllByOrderByIdxDesc(pageable);
//
//        for (Admin entity : adminEntites) {
//            AdminDto adminDto = AdminDto.builder()
//                    .userId(entity.getUserId())
//                    .userName(entity.getUserName())
//                    .userEmail(entity.getUserEmail())
//                    .userPhone(entity.getUserPhone())
//                    .enrolleDate(entity.getEnrollDate())
//                    .build();
//            adminDtos.add(adminDto);
//
//        }
//        Pagination pagination = new Pagination(
//                (int) adminEntites.getTotalElements()
//                , pageable.getPageNumber() + 1
//                , pageable
//                , 10
//        );
//
//        return Header.OK(adminDtos, pagination);
//    }
//    @Transactional
//    public List<Admin> getMemberList (){
////        List<Admin> list = adminRepository.findAll();
////        return list; return adminRepository.findAll(Sort.by(Sort.Direction.DESC, "userNum"));
//    }
    public List<AdminDto> getMemberList() {
        List<Admin> adminEntites = adminRepository.findAll();
        List<AdminDto> dtos = new ArrayList<>();

        for (Admin entity : adminEntites){
            AdminDto dto = AdminDto.builder()
                    .userId(entity.getUserId())
                    .userEmail(entity.getUserEmail())
                    .userPhone(entity.getUserPhone())
                    .enrolleDate(entity.getEnrollDate())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }
}