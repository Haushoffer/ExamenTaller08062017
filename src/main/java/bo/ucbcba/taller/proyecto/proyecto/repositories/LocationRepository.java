package bo.ucbcba.taller.proyecto.proyecto.repositories;

import bo.ucbcba.taller.proyecto.proyecto.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CORE i7 on 31/05/2017.
 */
@Transactional
public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = "select * from Location t where t.register_date >= :dateStart and t.register_date <= :dateFin", nativeQuery = true)
    List<Location> findByDates(@Param("dateStart") String dateStart, @Param("dateFin") String dateFin);

    @Query(value = "select * from Location t where t.register_date >= :dateStart and t.register_date <= :dateFin and t.band_id = :idBand", nativeQuery = true)
    List<Location> findByDatesAndBand(@Param("dateStart") String dateStart, @Param("dateFin") String dateFin, @Param("idBand") String idBand);

    @Query(value = "select * from Location t where t.band_id = :bandId", nativeQuery = true)
    List<Location> findByBandId(@Param("bandId") String bandId);

    @Query(value = "select * from Location t where t.band_id = :bandId order by t.register_date", nativeQuery = true)
    List<Location> findByBandIdInOrder(@Param("bandId") String bandId);
}
