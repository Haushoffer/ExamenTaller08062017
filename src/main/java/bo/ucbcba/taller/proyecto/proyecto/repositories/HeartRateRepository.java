package bo.ucbcba.taller.proyecto.proyecto.repositories;

import bo.ucbcba.taller.proyecto.proyecto.entities.HeartRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CORE i7 on 31/05/2017.
 */
@Transactional
public interface HeartRateRepository extends JpaRepository<HeartRate, Integer> {

    @Query(value = "select * from heart_rate t where t.register_date >= :dateStart and t.register_date <= :dateFin",nativeQuery = true)
    List<HeartRate> findByDates(@Param("dateStart") String dateStart, @Param("dateFin") String dateFin);

    @Query(value = "select * from heart_rate t where t.register_date >= :dateStart and t.register_date <= :dateFin",nativeQuery = true)
    HeartRate firstDate(@Param("dateStart") String dateStart, @Param("dateFin") String dateFin);

    @Query(value = "select * from heart_rate t where t.register_date >= :dateStart and t.register_date <= :dateFin and t.band_id = :idBand",nativeQuery = true)
    List<HeartRate> findByDatesAndBand(@Param("dateStart") String dateStart, @Param("dateFin") String dateFin, @Param("idBand") String idBand);

    @Query(value = "select * from heart_rate t where t.band_id = :bandId",nativeQuery = true)
    List<HeartRate> findByBandId(@Param("bandId") String bandId);

}
