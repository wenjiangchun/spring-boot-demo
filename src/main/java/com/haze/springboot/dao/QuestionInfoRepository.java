package com.haze.springboot.dao;

import com.haze.springboot.entity.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionInfoRepository extends JpaRepository<QuestionInfo, String> {
}
