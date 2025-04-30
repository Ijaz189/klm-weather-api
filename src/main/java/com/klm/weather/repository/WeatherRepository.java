package com.klm.weather.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.klm.weather.model.Weather;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

	@Query("SELECT w FROM Weather w ORDER BY w.id ASC")
	List<Weather> findAllOrderById();

	@Query("SELECT w FROM Weather w WHERE w.date = :date ORDER BY w.id ASC")
	List<Weather> findByDate(@Param("date") Date date);

	@Query("SELECT w FROM Weather w WHERE LOWER(w.city) IN :cities ORDER BY w.id ASC")
	List<Weather> findByCityIgnoreCaseIn(@Param("cities") List<String> cities);

	@Query("SELECT w FROM Weather w WHERE w.date = :date AND LOWER(w.city) IN :cities ORDER BY w.id ASC")
	List<Weather> findByDateAndCities(@Param("date") Date date, @Param("cities") List<String> cities);

	@Query("SELECT w FROM Weather w ORDER BY w.date ASC, w.id ASC")
	List<Weather> findAllOrderByDateAsc();

	@Query("SELECT w FROM Weather w ORDER BY w.date DESC, w.id ASC")
	List<Weather> findAllOrderByDateDesc();

	@Query("SELECT w FROM Weather w WHERE w.date = :date ORDER BY w.date ASC, w.id ASC")
	List<Weather> findByDateOrderByDateAsc(@Param("date") Date date);

	@Query("SELECT w FROM Weather w WHERE w.date = :date ORDER BY w.date DESC, w.id ASC")
	List<Weather> findByDateOrderByDateDesc(@Param("date") Date date);
}
