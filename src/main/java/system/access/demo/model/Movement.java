package system.access.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
public class Movement {

    @Id
    private Integer keyId;
    private Integer roomId;
    private Boolean entrance;

}