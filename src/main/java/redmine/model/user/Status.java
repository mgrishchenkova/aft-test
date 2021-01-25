package redmine.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE(1),
    WAS(2),
    BLOCKED(3);

    public final Integer status;

    public Integer getStatus(){
        return status;
    }

    public static Status of(Integer status){
        return Stream.of(values())
                .filter(it->it.status.equals(status))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Не найден status"+status));

    }


}
