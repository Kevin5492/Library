package com.kevin.library.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="inventory")
public class Inventory {
	@Id
	@Column(name = "inventory_id")
	private Integer inventoryId;
	
	@Column(name = "isbn")
	private String isbn;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "store_time")
	private Date storeTime;
	
	@ManyToOne
	@JoinColumn(name = "status_id", referencedColumnName = "state_id")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "isbn", referencedColumnName = "isbn")
	private Book book;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<BorrowingRecord> borrowingRecord = new HashSet<>();

}
