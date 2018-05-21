package se.netwomen.NetWomenBackend.model.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LikeTable {
    @Id
    @GeneratedValue
    private Long id;

    protected LikeTable(){}
}
