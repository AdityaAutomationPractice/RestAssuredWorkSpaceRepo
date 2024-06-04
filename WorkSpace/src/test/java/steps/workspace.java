package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.WorkspaceBody;
import pojo.Workspacepj;
import utils.ConfigReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Workspace {
	
	static Response response;
	static String id;

	static Workspacepj wsp = new Workspacepj();

	@Given("Enter the baseURL")
	public static String enter_the_base_url() throws IOException {
		return ConfigReader.getpropObject();
	}	

	@When("pass the endpoint")
	public void pass_the_endpoint() throws IOException {
		response = given().baseUri(enter_the_base_url())
				.header("X-API-Key", "PMAK-665abcd50d870800017d91a4-949307e6e31f3ca188e6467a0b4437ffdf").when().get()
				.then().extract().response();
	}

	@Then("validate the status code")
	public void validate_the_status_code() {
		int actual_code = response.statusCode();
		int expected_code = 200;
		Assert.assertEquals(expected_code, actual_code);
	}

	@Then("Check the body")
	public void check_the_body() {
		List<Map<Object, Object>> id = response.jsonPath().getList("workspaces");
		for (Map<Object, Object> workspace : id) {
			String id_data = (String) workspace.get(id);
			System.out.println(id_data);
		}

	}

	@When("Send the body with {string} {string} {string}")
	public static Response send_the_body_with(String string, String string2, String string3) throws IOException {

		wsp.setName(string);
		wsp.setType(string2);
		wsp.setDescription(string3);

		WorkspaceBody wpspBody = new WorkspaceBody();

		wpspBody.setWorkspace(wsp);

		response = given().baseUri(enter_the_base_url()).header("Content-Type", "application/json")
				.header("X-API-Key", "PMAK-665abcd50d870800017d91a4-949307e6e31f3ca188e6467a0b4437ffdf" ).body(wpspBody)
				.when().post().then().extract().response();
		return response;

	}

	@Then("validate the status code {int}")
	public void validate_the_status_code(Integer int1) {
		int actual_code = response.statusCode();
		int expected_code = 200;
		Assert.assertEquals(expected_code, actual_code);
	}

	@Then("validate the body data")
	public void validate_the_body_data() {
		id = response.jsonPath().get("workspace.id");
		String name = response.jsonPath().get("workspace.name");
		Assert.assertNotNull(id);
		Assert.assertEquals(name, wsp.getName());
		
	}

	@When("Pass the body along with the newly create id")
	public void pass_the_body_along_with_the_newly_create_id() throws IOException {
		
		given().baseUri(enter_the_base_url()).header("Content-Type", "application/json")
				.header("X-API-Key", "PMAK-665abcd50d870800017d91a4-949307e6e31f3ca188e6467a0b4437ffdf")
				.when().get("/"+ id).then().extract().response();
	}
	
	@Then("validate the status code {int} or not")
	public void validate_the_status(Integer int1) {
		Assert.assertEquals(200, response.statusCode());
	}

	@Then("validate the body")
	public void validate_the_body() {
		String name = response.jsonPath().getString("workspace.name");
		String user_id = response.jsonPath().getString("workspace.id");
		Assert.assertEquals(name, wsp.getName());
		Assert.assertEquals(user_id, id);
	}
	@When("Pass the body along with the new data")
	public void pass_the_body_along_with_the_new_data(io.cucumber.datatable.DataTable dataTable) throws IOException {
	    List<Map<String, String>> obj =   dataTable.asMaps(String.class , String.class);
	    String json = null;
	    for(Map<String, String> obj1 : obj) {
	    	json =String.format("{\n" +
	                "    \"workspace\": {\n" +
	                "        \"name\": \"%s\",\n" +
	                "        \"type\": \"%s\",\n" +
	                "        \"description\": \"%s\"\n" +
	                "    }\n" +
	                "}",
	                obj1.get("name"),
	                obj1.get("type"),
	                obj1.get("description"));  
	    }
	    
	    response = given().baseUri(enter_the_base_url()).header("Content-Type", "application/json")
				.header("X-API-Key", "PMAK-665abcd50d870800017d91a4-949307e6e31f3ca188e6467a0b4437ffdf").body(json).when().put("/"+ id).then().extract().response();
	}

	@Then("validate the status code and body")
	public void validate_the_status_code_and_body() {
	    Assert.assertEquals(200, response.statusCode());
	}

	
	@When("Send the body along with id to delete the data")
	public void delete_code() throws IOException {
		response = given().baseUri(enter_the_base_url()).header("X-API-Key", "PMAK-665abcd50d870800017d91a4-949307e6e31f3ca188e6467a0b4437ffdf").when().delete("/"+ id).then().extract().response();
	}
	
	@Then("check the status code {int} or nott")
	public void validate_the_status_code_demo(Integer int1) {
		Assert.assertEquals(200, response.statusCode());
	}
	
	@Then("get the body again and validate the code")
	public void get_the_body_again_and_validate_the_code() {
	    String deleted_id = response.jsonPath().get("workspace.id");
	    Assert.assertEquals(deleted_id, id);
	}

}
