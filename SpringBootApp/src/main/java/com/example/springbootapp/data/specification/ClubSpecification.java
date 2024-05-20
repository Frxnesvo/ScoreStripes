package com.example.springbootapp.data.specification;

import com.example.springbootapp.data.entities.Club;
import com.example.springbootapp.data.entities.League;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ClubSpecification {
    public static Specification<Club> leagueNameEquals(String leagueName) {
        return (root, query, builder) -> {
            Join<Club, League> leagueJoin = root.join("league", JoinType.INNER);
            return builder.equal(builder.lower(leagueJoin.get("name")), leagueName.toLowerCase());
        };
    }
}
