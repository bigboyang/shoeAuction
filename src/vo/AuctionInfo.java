package vo;

public class AuctionInfo {
	private int pa_idx, pa_price;
	private String pi_id, mi_id, pa_date, pa_win;
	
	public int getPa_idx() {
		return pa_idx;
	}
	public void setPa_idx(int pa_idx) {
		this.pa_idx = pa_idx;
	}
	public int getPa_price() {
		return pa_price;
	}
	public void setPa_price(int pa_price) {
		this.pa_price = pa_price;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getPa_date() {
		return pa_date;
	}
	public void setPa_date(String pa_date) {
		this.pa_date = pa_date;
	}
	public String getPa_win() {
		return pa_win;
	}
	public void setPa_win(String pa_win) {
		this.pa_win = pa_win;
	}
}
