package leshanclient;

public class LeshanClientObjectInfoVO {
    public String name;
    public String regId;
    public int typeId;
    public int instanceId;

    public LeshanClientObjectInfoVO(String name, String regId, int typeId, int instanceId) {
        this.name = name;
        this.regId = regId;
        this.typeId = typeId;
        this.instanceId = instanceId;
    }
}
