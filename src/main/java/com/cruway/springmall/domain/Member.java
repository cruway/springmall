package com.cruway.springmall.domain;

import com.cruway.springmall.domain.embeded.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty
    @Setter
    private String userName;

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

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    @Builder
    public Member(Long id, String userName, Address homeAddress) {
        this.id = id;
        this.userName = userName;
        this.homeAddress = homeAddress;
        this.orders = new ArrayList<>();
    }
}
