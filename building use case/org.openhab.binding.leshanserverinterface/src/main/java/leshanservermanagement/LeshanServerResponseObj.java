package leshanservermanagement;

public class LeshanServerResponseObj {
    public int resourceId;
    public String status;
    Content content;

    public int getContentId() {
        return content.id;
    }

    public String getContentValue() {
        return content.value;
    }
}

class Content {
    public int id;
    public String value;
    public String type;
}
