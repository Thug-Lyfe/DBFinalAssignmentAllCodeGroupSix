package cphbusiness.group6.apiTests;


import com.jayway.restassured.response.ValidatableResponse;
import cphbusiness.group6.api.APIMAINTEST;
import cphbusiness.group6.api.TestAPI;
import cphbusiness.group6.entities.Coordinate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.TestPropertySource;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Map;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {APIMAINTEST.class, TestAPI.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = TestAPI.class)
public class ApiTests {
//
//
//    @BeforeClass
//    public static void setup(){
//       // String port = System.getProperties();
//    }
//
//    @Test
//    public void checkTrialStatus(){
//
//       given().when().get("/tester/2").then().statusCode(200);
//    }
//
//    @Test
//    public void checkTrialValues(){
//        ValidatableResponse response = given().when().get("/teste/list").then();
//        assertThat(response.extract().jsonPath().getList("").size(), equalTo(3));
//    }
//
//
//





}
