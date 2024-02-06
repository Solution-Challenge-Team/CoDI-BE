package koders.codi.domain.post.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Category {
    FREE("free"),
    RECOMMEND("recommend");

    @Getter
    private final String value;

    Category(String value){
        this.value = value;
    }

    @JsonCreator    //역직렬화(json -> 객체)
    public static Category from(String value){
        for(Category c : Category.values()){
            if(c.getValue().equals(value)){
                return c;
            }
        }
        return null;
    }

    @JsonValue      //직렬화(객체 -> json)
    public String getValue(){
        return value;
    }
}
