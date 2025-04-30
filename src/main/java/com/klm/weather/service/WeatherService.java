package com.klm.weather.service;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;

@Service
public class WeatherService {
	@Autowired
	private WeatherRepository repo;

	public Weather create(Weather weather) {
		return repo.save(weather); // Saves to H2 database
	}

	public Optional<Weather> getById(Integer id) {
		return repo.findById(id);

	}

	public List<Weather> getFilteredWeather(Optional<String> dateStr, Optional<String> citiesStr,
			Optional<String> sort) {
		boolean hasDate = dateStr.isPresent() && !dateStr.get().isBlank();
		boolean hasCities = citiesStr.isPresent() && !citiesStr.get().isBlank();
		boolean hasSort = sort.isPresent() && !sort.get().isBlank();

		Date parsedDate = null;
		if (hasDate) {
			try {
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				parsedDate = java.sql.Date.valueOf(dateStr.get());
			} catch (Exception e) {
				return Collections.emptyList(); // invalid date format
			}
		}

		List<String> cityList = null;
		if (hasCities) {
			cityList = Arrays.stream(citiesStr.get().split(",")).map(String::toLowerCase).collect(Collectors.toList());
		}

		if (hasDate && hasCities) {
			return repo.findByDateAndCities(parsedDate, cityList);
		} else if (hasDate) {
			if (hasSort && sort.get().equals("-date")) {
				return repo.findByDateOrderByDateDesc(parsedDate);
			} else if (hasSort && sort.get().equals("date")) {
				return repo.findByDateOrderByDateAsc(parsedDate);
			} else {
				return repo.findByDate(parsedDate);
			}
		} else if (hasCities) {
			return repo.findByCityIgnoreCaseIn(cityList);
		} else {
			if (hasSort && sort.get().equals("-date")) {
				return repo.findAllOrderByDateDesc();
			} else if (hasSort && sort.get().equals("date")) {
				return repo.findAllOrderByDateAsc();
			} else {
				return repo.findAllOrderById();
			}
		}
	}
}
