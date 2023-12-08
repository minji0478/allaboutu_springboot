package org.ict.allaboutu.face.repository;

import org.ict.allaboutu.face.domain.Face;
import org.ict.allaboutu.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceRepository extends JpaRepository<Face, Long> {

}