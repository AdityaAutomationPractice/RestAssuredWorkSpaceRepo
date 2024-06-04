package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.WorkspaceBody;
import pojo.Workspacepj;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;


import java.io.IOException;
import java.util.Random;

import org.testng.Assert;


public class Workspaceneg {
	Response response;

	Random rand = new Random();
	String wrong_URL = "https://api.getpostman.com/workspac";
	
	@When("post worng workspace id along with the url")
	public void post_worng_workspace_id_along_with_the_url() throws IOException {
	    response = given().baseUri(Workspace.enter_the_base_url()).header("X-API-Key", "PMAK-665abcd50d870800017d91a4-949307e6e31f3ca188e6467a0b4437ffdf")
	    		.when().get("/"+ rand.nextInt(1000)).then().extract().response();
	    }

	@Then("check the status code {int}")
	public void check_the_status_code(Integer int1) {
		int actual_code = response.statusCode();
		int expected_code = 404;
		Assert.assertEquals(actual_code, expected_code );
	}

	@Then("check the error message and key are displayed")
	public void check_the_error_message_and_key_are_displayed() {
	    String name = response.jsonPath().get("error.name");
	    String message = response.jsonPath().get("error.message");
	    Assert.assertTrue(name.contains("workspaceNotFoundError"));
	    Assert.assertTrue(message.contains("Workspace not found"));
	}
	
	@When("Send the body with wrong type {string} {string} {string}")
	public void send_the_body_with_wrong_type(String string, String string2, String string3) throws IOException {
		Workspacepj wspj = new Workspacepj();
		wspj.setName(string);
		wspj.setType(string2);
		wspj.setDescription(string3);
		
		WorkspaceBody ws = new WorkspaceBody();
		ws.setWorkspace(wspj);
		
		response = given().baseUri(Workspace.enter_the_base_url())
				.header("X-API-Key", "PMAK-665abcd50d870800017d91a4-949307e6e31f3ca188e6467a0b4437ffdf").header("'Content-Type", "application/json").body(ws)
				.when().post().then().extract().response();
	}
	
	@Then("check the status code {int} or not")
	public void check_the_status_code_check(Integer int1) {
		int actual_code = response.statusCode();
		int expected_code = 400;
		Assert.assertEquals(actual_code, expected_code );
	}

	@Then("validate the body of the data")
	public void validate_the_body_of_the_data() {
	    String error = response.jsonPath().getString("error.name");
	    Assert.assertTrue(error.contains("invalidParamError"));
	}
	
	@When("Send the body with invalid id")
	public void send_the_body_with_invalid_id() throws IOException {
	    response = given().baseUri(Workspace.enter_the_base_url()).header("X-API-Key", "PMAK-665abcd50d870800017d91a4-949307e6e31f3ca188e6467a0b4437ffdf")
	    		.when().delete("/" + rand.nextInt(1000)).then().extract().response();
	}
	
	@Then("check the response code {int}")
	public void check_the_status_code_(Integer int1) {
		int actual_code = response.statusCode();
		int expected_code = 404;
		Assert.assertEquals(actual_code, expected_code );
	}

	@Then("validate the error message")
	public void validate_the_error_message(){
	    String message = response.jsonPath().get("error.message");
	    Assert.assertTrue(message.contains("The specified workspace does not exist."));
	   
	}
	
	@When("Send the wrong URL in path")
	public void send_the_wrong_url_in_path() throws IOException {
	    response = given().baseUri(wrong_URL).when().get().then().extract().response();
	    
	}

	@Then("Check the status code along with error message")
	public void check_the_status_code_along_with_error_message() {
		int actual_code = response.statusCode();
		int expected_code = 404;
		Assert.assertEquals(actual_code, expected_code );
		
		String error_message = response.jsonPath().getString("error.message");
		Assert.assertTrue(error_message.contains("Requested resource not found"));
	}





}
