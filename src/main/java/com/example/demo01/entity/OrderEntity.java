package com.example.demo01.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@FieldNameConstants
@Table(name = "order_info")
public class OrderEntity implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "shop_id")
	private Long shopId;

	@Column(name = "shop_name")
	private String shopName;

	@Column(name = "merch_id")
	private Long merchId;

	@Column(name = "merch_name")
	private String merchName;

	@Column(name = "trans_time")
	private java.util.Date transTime;

	@Column(name = "finished_time")
	private java.util.Date finishedTime;

	@Column(name = "created_time")
	private java.util.Date createdTime;

}
