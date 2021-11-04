package com.revature.models;

import lombok.*;

import javax.persistence.*;
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Like {
    @Id
    //determine if a sequence is needed for each like
    @SequenceGenerator(
            name = "like_sequence",
            sequenceName = "like_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "like_sequence"
    )
    private Long id;

    //type of data has to be determined, (what are likes sending?  (String, int, etc)
    private String like;

    //mappings have to be completed
}
