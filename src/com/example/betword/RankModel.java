package com.example.betword;
public class RankModel {
	
	private String name;
	private String rank;	
	private String pic_url;
	
	public RankModel(String name, String rank){
		setName(name);
		
		setRank(rank);		
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRank() {
		return this.rank;
	}
	public void setRank(String rank2) {
		this.rank = rank2;
	}
//	public String getPic() {
//		return pic_url;
//	}
//	public void setPic(String pic_url) {
//		this.pic_url = pic_url;
//	}
//	
	
}
