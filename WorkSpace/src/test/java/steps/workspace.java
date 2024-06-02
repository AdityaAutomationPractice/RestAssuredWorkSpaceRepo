package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.WorkspaceBody;
import pojo.workspacepj;
import utils.ConfigReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class workspace {
	
	static Response response;

	static workspacepj wsp = new workspacepj();

	@Given("Enter the baseURL")
	public static String enter_the_base_url() throws IOException {
		return ConfigReader.getCorrectURL();
	}

	@When("pass the endpoint")
	public void pass_the_endpoint() throws IOException {
		response = given().baseUri(enter_the_base_url())
				.header("X-API-Key", ConfigReader.getAPIKey()).when().get()
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
				.header("X-API-Key", ConfigReader.getAPIKey() ).body(wpspBody)
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
	public String validate_the_body_data() {
		String id = response.jsonPath().get("workspace.id");
		String name = response.jsonPath().get("workspace.name");
		Assert.assertNotNull(id);
		Assert.assertEquals(name, wsp.getName());
		return id;
	}

	@When("Pass the body along with the newly create id")
	public void pass_the_body_along_with_the_newly_create_id() throws IOException {
		
		given().log().all().baseUri(enter_the_base_url()).header("Content-Type", "application/json")
				.header("X-API-Key", ConfigReader.getAPIKey())
				.when().log().all().get("/"+ validate_the_body_data()).then().extract().response();
	}
	
	@Then("validate the status code {int} or not")
	public void validate_the_status(Integer int1) {
		int actual_code = response.statusCode();
		int expected_code = 200;
		Assert.assertEquals(expected_code, actual_code);
	}

	@Then("validate the body")
	public void validate_the_body() {
		String name = response.jsonPath().getString("workspace.name");
		String id = response.jsonPath().getString("workspace.id");
		Assert.assertEquals(name, wsp.getName());
//		Assert.assertEquals(id, validate_the_body_data());
	}

}
