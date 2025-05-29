package com.rodolfo.rediscachepostgresql.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Indexed
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Serial
    private Long id;

    private String name;

    private String code;

    private Integer quantity;

    private Double price;
}