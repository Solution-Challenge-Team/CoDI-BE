package koders.codi.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Disorder {
    A("지체장애"),
    B("시각장애"),
    C("청각장애"),
    D("언어장애"),
    E("안면장애"),
    F("신장장애"),
    G("심장장애"),
    H("간장애"),
    I("호흡기장애"),
    J("장루요루장애"),
    K("뇌전증장애"),
    L("지적장애"),
    M("자폐성장애"),
    N("정신장애");

    @Getter
    private final String value;

    Disorder(String value){
        this.value = value;
    }

    @JsonCreator
    public static Disorder from(String value){
        for(Disorder d : Disorder.values()){
            if(d.getValue().equals(value)){
                return d;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

}
