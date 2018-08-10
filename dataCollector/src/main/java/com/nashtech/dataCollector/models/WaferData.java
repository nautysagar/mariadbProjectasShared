package com.nashtech.dataCollector.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;




@Entity
@Table(name="waferdata")
public class WaferData extends BaseEntity {

	@Size(min=4, max=20)
	private String ocr;

	@Size(min=4, max=20)
	@Column(name="diff_lot")
	private String diffLot;

	@Size(min=4, max=20)
	@Column(name="sfc_lot")
	private String sfcLot;
	
	@Column(name="wafer_number")
	private int waferNumber;
	
	@Size(min=2, max=20)
	@Column(name="test_stage")
	private String testStage;
	
	@Size(min=12, max=12)
	private String nc12;
		
	@Size(min=2, max=32)
	@Column(name="product_name")
	private String productName;

	@Size(min=2, max=40)
	@Column(name="tp_name")
	private String tpName;

	@Size(min=2, max=20)
	@Column(name="tp_version")
	private String tpVersion;
	
	@Size(min=2, max=80)
	@Column(name="tpi_version")
	private String tpiVersion;
	
	public WaferData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WaferData(String ocr, String diffLot, String sfcLot, int waferNumber, String testStage, String nc12,
			String productName, String tpName, String tpVersion, String tpiVersion) {
	//	super(id);
		this.ocr = ocr;
		this.diffLot = diffLot;
		this.sfcLot = sfcLot;
		this.waferNumber = waferNumber;
		this.testStage = testStage;
		this.nc12 = nc12;
		this.productName = productName;
		this.tpName = tpName;
		this.tpVersion = tpVersion;
		this.tpiVersion = tpiVersion;
	}


	
	public String getOcr() {
		return ocr;
	}
	public void setOcr(String ocr) {
		this.ocr = ocr;
	}

	public String getDiffLot() {
		return diffLot;
	}
	public void setDiffLot(String diffLot) {
		this.diffLot = diffLot;
	}

	public String getSfcLot() {
		return sfcLot;
	}
	public void setSfcLot(String sfcLot) {
		this.sfcLot = sfcLot;
	}

	public int getWaferNumber() {
		return waferNumber;
	}
	public void setWaferNumber(int waferNumber) {
		this.waferNumber = waferNumber;
	}

	public String getTestStage() {
		return testStage;
	}
	public void setTestStage(String testStage) {
		this.testStage = testStage;
	}

	
	public String getNc12() {
		return nc12;
	}
	public void setNc12(String nc12) {
		this.nc12 = nc12;
	}

	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getTpName() {
		return tpName;
	}
	public void setTpName(String tpName) {
		this.tpName = tpName;
	}


	public String getTpVersion() {
		return tpVersion;
	}
	public void setTpVersion(String tpVersion) {
		this.tpVersion = tpVersion;
	}
	
	public String getTpiVersion() {
		return tpiVersion;
	}
	public void setTpiVersion(String tpiVersion) {
		this.tpiVersion = tpiVersion;
	}


	@Override
	public String toString() {
		return "WaferData [id=" + getId() + ", ocr=" + ocr + ", diffLot=" + diffLot + ", sfcLot=" + sfcLot + ", waferNumber="
				+ waferNumber + ", testStage=" + testStage + ", nc12=" + nc12 + ", productName=" + productName
				+ ", tpName=" + tpName + ", tpVersion=" + tpVersion + ", tpiVersion=" + tpiVersion + "]";
	}
	


}
