package com.haze.springboot.service;

import com.haze.springboot.dao.QuestionInfoRepository;
import com.haze.springboot.entity.QuestionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionInfoService.class);

    private StringRedisTemplate stringRedisTemplate;

    private QuestionInfoRepository repository;

    @Autowired
    public void setRepository(QuestionInfoRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

   public void read(String id) {
       QuestionInfo info = this.repository.findOne(id);
       String value = stringRedisTemplate.opsForValue().get(info.getTitle());
       if (value == null) {
           value = "0";
       }
       stringRedisTemplate.opsForValue().set(info.getTitle(), String.valueOf(Integer.parseInt(value) + 1));

   }

    public void save(QuestionInfo questionInfo) {
        LOGGER.debug("保存对象至redis{}", questionInfo);
        stringRedisTemplate.opsForValue().set(questionInfo.getTitle(), "0");
        LOGGER.debug("保存对象{}", questionInfo);
        repository.save(questionInfo);
        throw new RuntimeException("测试异常");
       // LOGGER.debug("保存对象{}", questionInfo);
       // repository.save(questionInfo);

    }

    public List<QuestionInfo> findAll() {
        List<QuestionInfo> list =  repository.findAll();
        list.forEach(questionInfo -> {
            String title = questionInfo.getTitle();
            if (title != null) {
                String value = stringRedisTemplate.opsForValue().get(questionInfo.getTitle());
                questionInfo.setReadCount(Long.parseLong(value));
            } else {
                questionInfo.setReadCount(0L);
            }
        });
        return list;
    }
}
