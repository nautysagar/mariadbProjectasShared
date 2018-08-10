package com.nashtech.dataCollector.pojo;


public class WaferDataMO extends AbstractMO {

	private String ocr;

	private String diffLot;
	private String sfcLot;
	private int waferNumber;
	
	private String testStage;
	
	private String nc12;
	private String productName;

	private String tpName;

	private String tpVersion;
	
	private String tpiVersion;
	
	public WaferDataMO() {
		super();
		// TODO Auto-generated constructor stub
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
