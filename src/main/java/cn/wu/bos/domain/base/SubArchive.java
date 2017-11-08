package cn.wu.bos.domain.base;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 子档案的实体类
 *
 * @author 武灵森
 * @version 1.0，2017-10-24 12:34:56
 */
@Entity
@Table(name="t_subarchive")
public class SubArchive {

	@Id
	@GeneratedValue
	@Column(name="c_id")
	/** 主键 */
	private Integer id;

	@Column(name="c_sub_archive_name")
	/** 子档案名称 */
	private String subArchiveName;
	
	@Column(name="c_memonic_code")
	/** 助记码 */
	private String memonicCode;

	@Column(name="c_remark")
	/** 备注 */
	private String remark;
	
	@Column(name="c_mothballed")
	/** 封存标记 */
	private Character mothballed;
	
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
	
	@ManyToOne
	@JoinColumn(name="c_archive_id")
	/** 档案 */
	private Archive archive;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubArchiveName() {
		return subArchiveName;
	}

	public void setSubArchiveName(String subArchiveName) {
		this.subArchiveName = subArchiveName;
	}

	public String getMemonicCode() {
		return memonicCode;
	}

	public void setMemonicCode(String memonicCode) {
		this.memonicCode = memonicCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Character getMothballed() {
		return mothballed;
	}

	public void setMothballed(Character mothballed) {
		this.mothballed = mothballed;
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

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}
	
}
