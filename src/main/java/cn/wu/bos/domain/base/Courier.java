package cn.wu.bos.domain.base;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 快递员的实体类
 *
 * @author 武灵森
 * @version 1.0，2017-10-25 13:19:36
 */
@SuppressWarnings("all")
@Entity
@Table(name="t_courier")
public class Courier {

	@Id
	@GeneratedValue
	@Column(name="c_id")
	/** 主键 */
	private Integer id; 
	
	@Column(name="c_courier_num", unique=true)
	/** 快递员编号 */
	private String courierNum; 
	
	@Column(name="c_name")
	/** 快递员姓名 */
	private String name; 
	
	@Column(name="c_telephone")
	/** 联系方式 */
	private String telephone; 
	
	@Column(name="c_pda")
	/** PDA号 */
	private String pda; 
	
	@Column(name="c_deltag")
	/** 作废标志 */
	private Character deltag; 
	
	@Column(name="c_check_pwd")
	/** 查台密码 */
	private String checkPwd; 
	
	@Column(name="c_type")
	/** 取件员类型 */
	private String type; 
	
	@Column(name="c_company")
	/** 单位 */
	private String company; 
	
	@Column(name="c_vehicle_type")
	/** 车辆类型 */
	private String vehicleType; 
	
	@Column(name="c_vehicle_num")
	/** 车牌号 */
	private String vehicleNum; 
	
	@ManyToOne
	@JoinColumn(name="c_standard_id")
	/** 收派标准 */
	private Standard standard; 
	
	@ManyToOne
	@JoinColumn(name="c_taketime_id")
	/** 收派时间 */
	private TakeTime takeTime; 
	
	@ManyToMany(mappedBy="couriers")
	/** 定区 */
	private Set<FixedArea> fixedAreas = new HashSet<FixedArea>();

	@Transient
	public String getInfo(){
		return this.name+"("+company+")";
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourierNum() {
		return courierNum;
	}

	public void setCourierNum(String courierNum) {
		this.courierNum = courierNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPda() {
		return pda;
	}

	public void setPda(String pda) {
		this.pda = pda;
	}

	public Character getDeltag() {
		return deltag;
	}

	public void setDeltag(Character deltag) {
		this.deltag = deltag;
	}

	public String getCheckPwd() {
		return checkPwd;
	}

	public void setCheckPwd(String checkPwd) {
		this.checkPwd = checkPwd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleNum() {
		return vehicleNum;
	}

	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	public Standard getStandard() {
		return standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}

	public TakeTime getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(TakeTime takeTime) {
		this.takeTime = takeTime;
	}

	public Set<FixedArea> getFixedAreas() {
		return fixedAreas;
	}

	public void setFixedAreas(Set<FixedArea> fixedAreas) {
		this.fixedAreas = fixedAreas;
	}
	
}
