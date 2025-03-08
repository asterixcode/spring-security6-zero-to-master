package com.asterixcode.bankapi.domain.repository;

import com.asterixcode.bankapi.domain.model.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends ListCrudRepository<Notice, Long> {
  @Query(
      value = "from Notice n where CURRENT_TIMESTAMP BETWEEN n.noticeBegDate AND n.noticeEndDate")
  List<Notice> findAllActiveNotices();
}
