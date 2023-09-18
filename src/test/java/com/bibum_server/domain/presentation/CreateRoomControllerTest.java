package com.bibum_server.domain.presentation;

import com.bibum_server.domain.AbstractRestDocsTests;
import com.bibum_server.domain.TestUtil;
import com.bibum_server.domain.application.RoomService;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.RestaurantRes;
import com.bibum_server.domain.dto.response.RoomRes;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.room.entity.Room;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(TodayMenuController.class)
class CreateRoomControllerTest extends AbstractRestDocsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("Create Room.")
    @Test
    void createRoom() throws Exception {
        LocationReq location =  LocationReq.builder()
                .latitude("37.230840")
                .longitude("127.190607")
                .build();

        String locationRequest = mapper.writeValueAsString(location);

        Room room = TestUtil.CreateTestRoom();

        List<Restaurant> restaurantList = TestUtil.CreateTestRestaurantList(room);
        room.addRestaurant(restaurantList);

        List<RestaurantRes> restaurantResList = restaurantList.stream().map(RestaurantRes::fromEntity).toList();
        RoomRes mockResponse = RoomRes.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .total(room.getTotal())
                .restaurantResList(restaurantResList)
                .build();
        given(roomService.createRoom(any(LocationReq.class))).willReturn(mockResponse);


        this.mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationRequest))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
    @Test
    void retry() throws Exception {
        long roomId = 1L;
        Room room = TestUtil.CreateTestRoom();

        List<Restaurant> restaurantList = TestUtil.CreateTestRestaurantList(room);
        room.addRestaurant(restaurantList);

        List<RestaurantRes> restaurantResList = restaurantList.stream().map(RestaurantRes::fromEntity).toList();
        RoomRes mockResponse = RoomRes.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .total(room.getTotal())
                .restaurantResList(restaurantResList)
                .build();
        given(roomService.createRoom(any(LocationReq.class))).willReturn(mockResponse);


        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/retry/{roomId}",roomId))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }


    }