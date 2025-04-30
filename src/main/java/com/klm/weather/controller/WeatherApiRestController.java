package com.klm.weather.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klm.weather.model.Weather;
import com.klm.weather.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {
	@Autowired
	private WeatherService service;
	//To create and store weather info in db
	@PostMapping
	public ResponseEntity<Weather> create(@RequestBody Weather weather) {
		Weather created = service.create(weather);
		return ResponseEntity.status(201).body(created);
	}
	//To get Weather object based on multiple critiria
	@GetMapping
	public ResponseEntity<List<Weather>> getAllWeather(@RequestParam Optional<String> date,
			@RequestParam Optional<String> city, @RequestParam Optional<String> sort) {
		List<Weather> result = service.getFilteredWeather(date.filter(s -> !s.isBlank()),
				city.filter(s -> !s.isBlank()), sort.filter(s -> !s.isBlank()));
		return ResponseEntity.ok(result);
	}
	//To get Weather object based on id
	@GetMapping("/{id}")
	public ResponseEntity<Weather> getById(@PathVariable Integer id) {
		return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}
