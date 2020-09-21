package io.surisoft.demo.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "Unprotected", tags = {"Unprotected Service"})
@Slf4j
public class LicenseController {
	
	@ApiOperation(value = "Get all OpenSource Licenses")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got your list"),
        @ApiResponse(code = 500, message = "No Licenses")
    })
	@GetMapping(path="/licenses")
	public ResponseEntity<String> licenses(HttpServletRequest request) {
		JSONObject licenses = new JSONObject();
		try {
			JSONLoader loader = new JSONLoader();
			licenses = loader.getLicenses();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(licenses.toString(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get licenses for given name")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Got your License for a given name"),
			@ApiResponse(code = 500, message = "No Licenses")
	})
	@GetMapping(path = "/licenses/{name}")
	public ResponseEntity<String> licensesByName(@PathVariable String name, HttpServletRequest request) {

		JSONObject search = new JSONObject();
		JSONArray searchArray = new JSONArray();
		try {
			JSONLoader loader = new JSONLoader();
			JSONObject licenses = loader.getLicenses();
			JSONArray arr = (JSONArray) licenses.get("licenses");
			for(int i=0; i<arr.size(); i++) {
				JSONObject l = (JSONObject) arr.get(i);
				if(l.getAsString("id").toLowerCase().contains(name.toLowerCase())) {
					searchArray.appendElement(l);
				}
			}
			search.put("licenses", searchArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(search.toString(), HttpStatus.OK);
	}
}