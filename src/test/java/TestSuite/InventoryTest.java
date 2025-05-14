package TestSuite;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Listeners.inventoryListener;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.support.FileReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;





@Listeners(inventoryListener.class)
public class InventoryTest {
	@Test(priority = 1)
    void getAllMenuItems() {
        RestAssured.baseURI = "http://localhost:3100";

        Response response = given()
                                .contentType(ContentType.JSON)
                            .when()
                                .get("api/inventory");

        response.then()
                .statusCode(200)
                .body("data.size()", greaterThanOrEqualTo(9))
                .body("data.id", everyItem(notNullValue()))
                .body("data.name", everyItem(notNullValue()))
                .body("data.price", everyItem(notNullValue()))
                .body("data.image", everyItem(notNullValue()));

        System.out.println("Status Code is " + response.getStatusCode());
        System.out.println("response validated and it contains 9 items");
        System.out.println("response validated and all the items in the response contains id, name, price, image");
    }

    @Test(priority = 2)
    void getid3() {
        RestAssured.baseURI = "http://localhost:3100";

        Response response = given()
                                .contentType(ContentType.JSON)
                            .when()
                                .get("/api/inventory/filter?id=3");

        response.then()
                .statusCode(200)
                .body("id", equalTo("3"))
                .body("name", equalTo("Baked Rolls x 8"))
                .body("price", equalTo("$10"))
                .body("image", equalTo("roll.png"));

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("response validated and it contains the correct id,name,price and image for Baked Rolls x 8");
    }

    @Test(enabled = true, priority = 3)
    void addNewItem_HawaiianPizza() {
        RestAssured.baseURI = "http://localhost:3100";
        Response response = given()
                                .contentType(ContentType.JSON)
                                .body("{\"id\": \"10\",\"name\": \"Hawaiian\",\"image\": \"hawaiian.png\",\"price\": \"$14\"}")
                            .when()
                                .post("api/inventory/add");

        response.then()
                .statusCode(200);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("New item added successfully");
    }

    @Test(enabled = true, priority = 4)
    void addExistsItem_HawaiianPizza() {
        RestAssured.baseURI = "http://localhost:3100";

        Response response = given()
                                .contentType(ContentType.JSON)
                                .body("{\"id\": \"10\",\"name\": \"Hawaiian\",\"image\": \"hawaiian.png\",\"price\": \"$14\"}")
                            .when()
                                .post("api/inventory/add");

        response.then()
                .statusCode(400)
                .body(equalTo("Bad Request"));

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Aleady exists item unable to added inside the inventory");
    }

    @Test(enabled = true, priority = 5)
    void addItem_missingInfo() {
        RestAssured.baseURI = "http://localhost:3100";

        Response response = given()
                                .contentType(ContentType.JSON)
                                .body("{\"name\": \"Hawaiian\",\"image\": \"hawaiian.png\",\"price\": \"$14\"}")
                            .when()
                                .post("api/inventory/add");

        response.then()
                .statusCode(400)
                .body(equalTo("Not all requirements are met"));

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Not all requirements are met error message validated");
    }

    @Test(enabled = true, priority = 6)
    void getLastAddedItem() {
        RestAssured.baseURI = "http://localhost:3100";

        Response response = given()
                                .when()
                                .get("/api/inventory");

        response.then()
                .statusCode(200)
                .body("data.id", hasItem("10"))
                .body("data.name", hasItem("Hawaiian"))
                .body("data.image", hasItem("hawaiian.png"))
                .body("data.price", hasItem("$14"));

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("response validated and the last added item is present in the inventory");
    }
}
