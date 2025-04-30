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
	// Retrieves all weather records ordered by ID in ascending order
	@Query("SELECT w FROM Weather w ORDER BY w.id ASC")
	List<Weather> findAllOrderById();
	
	// Retrieves all weather records matching the given date by ID in ascending order 
	@Query("SELECT w FROM Weather w WHERE w.date = :date ORDER BY w.id ASC")
	List<Weather> findByDate(@Param("date") Date date);

	// Retrieves all weather records matching the given city by ID in ascending order
	@Query("SELECT w FROM Weather w WHERE LOWER(w.city) IN :cities ORDER BY w.id ASC")
	List<Weather> findByCityIgnoreCaseIn(@Param("cities") List<String> cities);

	// Retrieves all weather records matching cities for  by ID in ascending order
	@Query("SELECT w FROM Weather w WHERE w.date = :date AND LOWER(w.city) IN :cities ORDER BY w.id ASC")
	List<Weather> findByDateAndCities(@Param("date") Date date, @Param("cities") List<String> cities);

	// Retrieves all weather records order by ascending date and if same date then order by ascending id
	@Query("SELECT w FROM Weather w ORDER BY w.date ASC, w.id ASC")
	List<Weather> findAllOrderByDateAsc();

	// Retrieves all weather records order by descending date and if same date then order by ascending id
	@Query("SELECT w FROM Weather w ORDER BY w.date DESC, w.id ASC")
	List<Weather> findAllOrderByDateDesc();

	// Retrieves all weather records for matching date with order by ascending date and if same date then order by ascending id
	@Query("SELECT w FROM Weather w WHERE w.date = :date ORDER BY w.date ASC, w.id ASC")
	List<Weather> findByDateOrderByDateAsc(@Param("date") Date date);
	
	// Retrieves all weather records for matching date with order by descending date and if same date then order by ascending id
	@Query("SELECT w FROM Weather w WHERE w.date = :date ORDER BY w.date DESC, w.id ASC")
	List<Weather> findByDateOrderByDateDesc(@Param("date") Date date);
}
