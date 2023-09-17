package com.bibum_server.domain.dto.response;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantRes {

    Long id;

    String title;

    String category;

    Long count;

    String link;

    Long distance;

    Long roomId;  // Room ID
    String rank;

    @Builder
    public RestaurantRes(Long id, String title, String category, Long count,
                         String link, Long distance, Long roomId, String rank) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.count = count;
        this.link = link;
        this.distance = distance;
        this.roomId = roomId;
        this.rank = rank;
    }

    public static RestaurantRes fromEntity(Restaurant restaurant) {
        return RestaurantRes.builder()
                .id(restaurant.getId())
                .category(restaurant.getCategory())
                .distance(restaurant.getDistance())
                .count(restaurant.getCount())
                .title(restaurant.getTitle())
                .link(restaurant.getLink())
                .roomId(restaurant.getRoom().getId())
                .build();

    }
}
