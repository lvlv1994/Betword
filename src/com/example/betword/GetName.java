package com.example.betword;

public class GetName {
	String name;
      public GetName(String a) {
		// TODO Auto-generated constructor stub
    	  name=a;
	}
      
      void a(UserName un){
    	  un.getName(name);
    	  
      }
      
    public static interface UserName{
        void getName(String result);  	
    		}

}
