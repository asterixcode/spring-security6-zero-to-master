package com.asterixcode.bankapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "notice_details")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_details_id_seq")
  @SequenceGenerator(
      name = "notice_details_id_seq",
      sequenceName = "notice_details_id_seq",
      allocationSize = 1)
  @Column(name = "notice_id")
  private Long noticeId;

  @Column(name = "notice_summary")
  private String noticeSummary;

  @Column(name = "notice_details")
  private String noticeDetails;

  @Column(name = "notice_beg_date")
  private LocalDate noticeBegDate;

  @Column(name = "notice_end_date")
  private LocalDate noticeEndDate;

  @JsonIgnore
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @JsonIgnore
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
