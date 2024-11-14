package com.glb.practice.my_practice.models;
import java.util.Date;

import jakarta.persistence.*;

import lombok.*;
@Data
@Builder
@Entity
@Table(name = "access")
@AllArgsConstructor
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accessid;
    @ManyToOne
    @JoinColumn(name = "reader_Id")
    private Reader reader;
    @Temporal(TemporalType.DATE)
    private Date accessDate;
    Access(){

    }
}
