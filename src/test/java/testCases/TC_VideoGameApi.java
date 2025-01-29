package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;



public class TC_VideoGameApi {

	@Test(priority = 1)
	public void Test_getAllVideoGames() {

		given()

				.when().get("http://localhost:8080/app/videogames")

				.then().statusCode(200);

	}

	@Test(priority = 2)
	public void Test_addNewVideoGame() {

		HashMap<String, String> data = new HashMap<String, String>();
		data.put("id", "100");
		data.put("name", "spider-Man");
		data.put("releaseDate", "2025-01-28T14:21:21.706Z");
		data.put("reviewScore", "12");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

		Response res = given().contentType("application/json").body(data).when()
				.post("http://localhost:8080/app/videogames")

				.then().statusCode(200).log().body().extract().response();

		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);

	}

	@Test(priority = 3)
	public void Test_getVideoGame() {

		given()

				.when()
				.get("http://localhost:8080/app/videogames/100")

				.then()
				.statusCode(200)
				.log().body()
				.body("videoGame.id",equalTo("100"))
				.body("videoGame.name",equalTo("spider-Man"));

	}
	
	@Test(priority=4)
	public void Test_UpdateVideoGame() {
		
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("id", "100");
		data.put("name", "Batman");
		data.put("releaseDate", "2025-01-28T14:21:21.706Z");
		data.put("reviewScore", "20");
		data.put("category", "fantasy");
		data.put("rating", "Global");
		
		given()
		     .contentType("application/json")
		     .body(data)
		     
		.when()
		     .put("http://localhost:8080/app/videogames/100")
		.then()
		    .statusCode(200)
		    . log().body()
		 	.body("videoGame.id",equalTo("100"))
			.body("videoGame.name",equalTo("Batman"));
		     		
		
	}
	
	@Test(priority=5)
	public void Test_DeleteVideoGame() throws Throwable {
		
		Response res=
		given()
		.when()
		.delete("http://localhost:8080/app/videogames/100")
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();
		
		Thread.sleep(3000);
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
		
	}
	
}
