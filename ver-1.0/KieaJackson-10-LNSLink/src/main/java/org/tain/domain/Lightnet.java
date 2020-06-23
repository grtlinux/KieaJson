package org.tain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_lightnet"
	, indexes = {
			@Index(name = "lightnet_idx_1", unique = false, columnList = "title"),
	}
)
@SequenceGenerator(name = "lightnet_seq"
	, sequenceName = "lightnet_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
@JsonIgnoreProperties({""})
public class Lightnet {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lightnet_seq")
	@Column(name = "id")
	@JsonIgnore
	private Long id;
	
	@Column(name = "title", length = 256)
	private String title;
	
	@Column(name = "content", length = 1024)
	private String content;
	
	@Column(name = "user_id", length = 16)
	private String userId;
	
	@Column(name = "req_url", length = 128)
	private String reqUrl;
	
	@Column(name = "req_method", length = 16)
	private String reqMethod;
	
	@Column(name = "req_json_data", length = 10240)
	private String reqJsonData;
	
	@Column(name = "res_status", length = 512)
	private String resStatus;
	
	@Column(name = "res_json_data", length = 10240)
	private String resJsonData;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate = LocalDateTime.now();
	
	@Builder
	public Lightnet(
			String title,
			String content,
			String userId,
			String reqUrl,
			String reqMethod,
			String reqJsonData,
			String resStatus,
			String resJsonData
			){
		
	}
}
