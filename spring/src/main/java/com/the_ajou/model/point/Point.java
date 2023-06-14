package com.the_ajou.model.point;

import com.the_ajou.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "point")
    private int point;

    @Builder
    Point(User user, String createdAt, int point){
        this.user = user;
        this.createdAt = createdAt;
        this.point = point;
    }
}
