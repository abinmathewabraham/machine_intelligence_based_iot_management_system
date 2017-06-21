package org.eclipse.leshan.server.demo.openhab;

import java.util.ArrayList;

import org.eclipse.leshan.Link;

public class LeshanClientVO {

	String name;
	String regId;
	ArrayList<LeshanObjectVO> leshanObjList;
	
	public LeshanClientVO() {
		super();
	}

	public LeshanClientVO(String name, String regid, Link[] linkObjects) {
		super();
		this.name = name;
		this.regId = regid;
		
		leshanObjList = new ArrayList<>();
		LeshanObjectVO temp;
		String[] tempInfo = null;
		for(Link temp2 : linkObjects){
			tempInfo = temp2.getUrl().split("/");
			if(tempInfo.length >= 3){
				temp = new LeshanObjectVO(Integer.parseInt(tempInfo[1]), Integer.parseInt(tempInfo[2]));
				leshanObjList.add(temp);
			}
		}
	}
}