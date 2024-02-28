package com.be.monolithic.model_old;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date createDate;
    private Date updateDate;
}
