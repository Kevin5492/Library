package com.kevin.library.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="borrowing_record")
public class BorrowingRecord {
	@Id
	@Column(name = "borrowing_record_id")
	private Integer borrowingRecordId;
	
	@ManyToOne
	@JoinColumn(name ="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name ="inventory_id")
	private Inventory inventory;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "borrowing_time")
	private Date borrowingTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "return_time")
	private Date returnTime;

}
