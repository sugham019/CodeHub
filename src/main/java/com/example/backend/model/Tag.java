package com.example.backend.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TagType tagType;

    @Column(name = "name", nullable = false)
    private String name;

    public Tag() {}

    public Tag(TagType tagType, String name) {
        this.tagType = tagType;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag tag)) return false;
        return tagType == tag.tagType && name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagType, name);
    }

    public Long getId() {
        return id;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}