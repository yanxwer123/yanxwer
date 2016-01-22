package com.kld.gsm.ATG.domain;

public class DailyDailyBaseEquipment extends DailyDailyBaseEquipmentKey {

	//油罐编号
	private Integer oilcanno;
	
	//产品型号
	private String devicemodel;
	
	//设备代码
	private String equipcode;
	
	//探棒序列号
	private String  Probeno;
	
	//系统版本号
	private String version;
	
	//制造日期
	private String makedate;

	public Integer getOilcanno() {
		return oilcanno;
	}

	public void setOilcanno(Integer oilcanno) {
		this.oilcanno = oilcanno;
	}

	public String getDevicemodel() {
		return devicemodel;
	}

	public void setDevicemodel(String devicemodel) {
		this.devicemodel = devicemodel;
	}

	public String getEquipcode() {
		return equipcode;
	}

	public void setEquipcode(String equipcode) {
		this.equipcode = equipcode;
	}

	public String getProbeno() {
		return Probeno;
	}

	public void setProbeno(String probeno) {
		Probeno = probeno;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	@Override
	public String toString() {
		return "DailyDailyBaseEquipment{" +
				"oilcanno=" + oilcanno +
				", devicemodel='" + devicemodel + '\'' +
				", equipcode='" + equipcode + '\'' +
				", Probeno='" + Probeno + '\'' +
				", version='" + version + '\'' +
				", makedate='" + makedate + '\'' +
				'}';
	}
}
