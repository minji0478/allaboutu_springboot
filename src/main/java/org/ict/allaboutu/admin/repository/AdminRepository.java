package org.ict.allaboutu.admin.repository;

import org.ict.allaboutu.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository  extends JpaRepository<Admin, Long> {

    //    public List<Admin> findAdminByUserNum(Long userNum);
//     Page<Admin> findAllByIdDesc();

//    @Query("SELECT u FROM Admin u JOIN Report r ON u.userNum = r.userNum")
//    List<Report> findAllByUserNum(@Param("userNum") Long userNum);
}
