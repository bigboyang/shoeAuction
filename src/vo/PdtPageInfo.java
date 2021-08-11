package vo;

//상품 관련 목록에서 페이징을 위해 필요한 데이터들을 저장할 클래스(어드민 & 프로튼 공용).
public class PdtPageInfo {
	private int cpage, pcnt, spage, epage, rcnt, psize, bsize, year;
	
	// 프론트와 어드민에서 공통적으로 사용할 검색 관련 정보를 저장한 변수.
	private String keyword, bcata, scata, brand, sprice, eprice, pkeyword;
	
	// 어드민에서만 사용할 검색 관련 정보를 저장할 변수.
	private String isview, sdate, edate, stock, isactive, schtype;
	
	private String ord, status, ismail, size, quaility, pfstatus, miid;
	private String pms, isrun, bqcata, xline, yline;

	// setter, getter
	public String getXline() {
		return xline;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setXline(String xline) {
		this.xline = xline;
	}
	public String getYline() {
		return yline;
	}
	public void setYline(String yline) {
		this.yline = yline;
	}
	public String getIsmail() {
		return ismail;
	}
	public void setIsmail(String ismail) {
		this.ismail = ismail;
	}
	public String getPkeyword() {
		return pkeyword;
	}
	public void setPkeyword(String pkeyword) {
		this.pkeyword = pkeyword;
	}
	public int getCpage() {
		return cpage;
	}
	public void setCpage(int cpage) {
		this.cpage = cpage;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public String getSchtype() {
		return schtype;
	}
	public void setSchtype(String schtype) {
		this.schtype = schtype;
	}
	public int getPcnt() {
		return pcnt;
	}
	public void setPcnt(int pcnt) {
		this.pcnt = pcnt;
	}
	public int getSpage() {
		return spage;
	}
	public void setSpage(int spage) {
		this.spage = spage;
	}
	public int getEpage() {
		return epage;
	}
	public void setEpage(int epage) {
		this.epage = epage;
	}
	public int getRcnt() {
		return rcnt;
	}
	public void setRcnt(int rcnt) {
		this.rcnt = rcnt;
	}
	public int getPsize() {
		return psize;
	}
	public void setPsize(int psize) {
		this.psize = psize;
	}
	public int getBsize() {
		return bsize;
	}
	public void setBsize(int bsize) {
		this.bsize = bsize;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getBcata() {
		return bcata;
	}
	public void setBcata(String bcata) {
		this.bcata = bcata;
	}
	public String getScata() {
		return scata;
	}
	public void setScata(String scata) {
		this.scata = scata;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSprice() {
		return sprice;
	}
	public void setSprice(String sprice) {
		this.sprice = sprice;
	}
	public String getEprice() {
		return eprice;
	}
	public void setEprice(String eprice) {
		this.eprice = eprice;
	}
	public String getIsview() {
		return isview;
	}
	public void setIsview(String isview) {
		this.isview = isview;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getQuaility() {
		return quaility;
	}
	public void setQuaility(String quaility) {
		this.quaility = quaility;
	}
	public String getPfstatus() {
		return pfstatus;
	}
	public void setPfstatus(String pfstatus) {
		this.pfstatus = pfstatus;
	}
	public String getMiid() {
		return miid;
	}
	public void setMiid(String miid) {
		this.miid = miid;
	}
	public String getPms() {
		return pms;
	}
	public void setPms(String pms) {
		this.pms = pms;
	}
	public String getIsrun() {
		return isrun;
	}
	public void setIsrun(String isrun) {
		this.isrun = isrun;
	}
	public String getBqcata() {
		return bqcata;
	}
	public void setBqcata(String bqcata) {
		this.bqcata = bqcata;
	}
	
}
