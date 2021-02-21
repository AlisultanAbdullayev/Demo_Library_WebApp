package com.alijaver.libraryapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "fullName", includeFieldNames = false)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fullName", unique = true)
    private String fullName;
    @Lob
    private String about;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

}
