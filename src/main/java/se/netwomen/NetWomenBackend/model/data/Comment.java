package se.netwomen.NetWomenBackend.model.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.awt.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    protected Comment(){}
}
