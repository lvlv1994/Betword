package Betword.model;

public class wordModel {
	private String ID;
	private String meanning;
	private String spelling;
	private String phonetic_alphabet;

	public wordModel(){
		
	}
	public wordModel(String ID,String meanning,String spelling,String phonetic_alphabet){
		this.ID=ID;
		this.meanning=meanning;
		this.spelling=spelling;
		this.phonetic_alphabet=phonetic_alphabet;
		
	}
	public void setID(String ID){
		this.ID=ID;
	}
	public String getID(){
		return ID;
	}

	public void setMeanning(String meanning){
		this.meanning=meanning;
	}
	public String getMeanning(){
		return meanning;
	}

	public void setSpelling(String spelling){
		this.spelling=spelling;
	}
	public String getSpelling(){
		return spelling;
	}

	public void setPhonetic_alphabet(String phonetic_alphabet){
		this.phonetic_alphabet=phonetic_alphabet;
	}
	public String getPhonetic_alphabet(){
		return phonetic_alphabet;
	}

	

}
