package com.kld.gsm.center.domain;

/**
 * Created by xhz on 2015/12/4.  新构造
 */
public class FMResult {
    /*	现金 00
		邮票 01
		记账 02
		银行卡 03
		其他1 04
		其他2 05
		IC卡 06
		IC卡积分 16
		已售未提 22
		已提未售 32
		代存代付 42
		邮票代管 52
		自用油 62
		非机走 07*/
    private String oilno;//油品
    private String oilname;//油品名称
    private Double total;//合计
    private Double money;//现金
    private Double yp;//邮票
    private Double jz;//记账
    private Double yhk;//银行卡
    private Double other1;//其他1
    private Double other2;//其他2
    private Double ic;//ic卡
    private Double icjf;//ic卡积分
    private Double yswt;//已售未提
    private Double ytws;//已提未售
    private Double dcdf;//代存代付
    private Double ypdg;//邮票代管
    private Double zyy;//自用油
    private Double fjz;//飞机走
    public String getOilno(){return oilno;}
    public void setOilno(String oilno){this.oilno=oilno;}
    public String getOilname(){return oilname;}
    public void setOilname(String oilname){this.oilname=oilname;}
    public  Double getTotal(){return total;}
    public void setTotal(Double total){this.total=total;}
    public Double getMoney(){return money;}
    public void setMoney(Double money){this.money=money;}
    public Double getYp(){return yp;}
    public void setYp(Double yp){this.yp=yp;}
    public Double getJz(){return jz;}
    public void setJz(Double jz){this.jz=jz;}
    public Double getYhk(){return yhk;}
    public void setYhk(Double yhk){this.yhk=yhk;}
    public Double getOther1(){return other1;}
    public void setOther1(Double other1){this.other1=other1;}
    public Double getOther2(){return other2;}
    public void setOther2(Double other2){this.other2=other2;}
    public Double getIc(){return ic;}
    public void setIc(Double ic){this.ic=ic;}

    public Double getIcjf(){return icjf;}
    public void setIcjf(Double icjf){this.icjf=icjf;}

    public Double getYswt(){return yswt;}
    public void setYswt(Double yswt){this.yswt=yswt;}

    public Double getYtws(){return ytws;}
    public void setYtws(Double ytws){this.ytws=ytws;}

    public Double getDcdf(){return dcdf;}
    public void setDcdf(Double dcdf){this.dcdf=dcdf;}

    public Double getYpdg(){return ypdg;}
    public void setYpdg(Double ypdg){this.ypdg=ypdg;}

    public Double getZyy(){return zyy;}
    public void setZyy(Double zyy){this.zyy=zyy;}

    public Double getFjz(){return fjz;}
    public void setFjz(Double fjz){this.fjz=fjz;}
}
