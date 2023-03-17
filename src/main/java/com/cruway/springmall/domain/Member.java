package com.cruway.springmall.domain;

import com.cruway.springmall.domain.embeded.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id", nullable = false)
    private Long id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",
                    column = @Column(name = "work_city")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "work_zipcodes"))
    })
    private Address homeAddress;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    @Builder
    public Member(Long id, String name, Address homeAddress) {
        this.id = id;
        this.name = name;
        this.homeAddress = homeAddress;
        this.orders = new ArrayList<>();
    }
}
