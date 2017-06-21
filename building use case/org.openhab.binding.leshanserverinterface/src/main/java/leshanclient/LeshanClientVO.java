package leshanclient;

import java.util.ArrayList;

public class LeshanClientVO {
    public String name;
    public String regId;
    public ArrayList<LeshanObjectVO> leshanObjList;

    public LeshanClientVO() {
        super();
    }

    public LeshanClientVO(String name, String regid, ArrayList<LeshanObjectVO> leshanObjList) {
        super();
        this.name = name;
        this.regId = regid;
        this.leshanObjList = leshanObjList;
    }
}
