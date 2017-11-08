package cn.wu.bos.domain.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 档案的实体类
 *
 * @author 武灵森
 * @version 1.0，2017-10-24 12:27:05
 */
@Entity
@Table(name="t_archive")
public class Archive {

	@Id
	@GeneratedValue
	@Column(name="c_id")
	/** 主键 */
	private Integer id;

	@Column(name="c_archive_num",unique=true)
	/** 档案编号 */
	private String archiveNum;

	@Column(name="c_archive_name")
	/** 档案名称 */
	private String archiveName;

	@Column(name="c_remark")
	/** 备注 */
	private String remark;

	@Column(name="c_hashChild")
	/** 是否分级 */
	private Integer hashChild;

	@Column(name="c_operating_time")
	@Temporal(TemporalType.TIMESTAMP)
	/** 操作时间 */
	private Date operatingTime;

	@Column(name="c_operator")
	/** 操作人 */
	private String operator;
	
	@Column(name="c_operating_company")
	/** 操作单位 */
	private String operatingCompany;
	
	@OneToMany(mappedBy="archive")
	/** 子档案 */
	private Set<SubArchive> subarchives = new HashSet<SubArchive>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArchiveNum() {
		return archiveNum;
	}

	public void setArchiveNum(String archiveNum) {
		this.archiveNum = archiveNum;
	}

	public String getArchiveName() {
		return archiveName;
	}

	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getHashChild() {
		return hashChild;
	}

	public void setHashChild(Integer hashChild) {
		this.hashChild = hashChild;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatingCompany() {
		return operatingCompany;
	}

	public void setOperatingCompany(String operatingCompany) {
		this.operatingCompany = operatingCompany;
	}

	public Set<SubArchive> getSubarchives() {
		return subarchives;
	}

	public void setSubarchives(Set<SubArchive> subarchives) {
		this.subarchives = subarchives;
	}
	
}
