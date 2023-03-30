package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.GalleryImageKind;
import pl.com.mike.developer.domain.ImageData;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
class GalleryJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(GalleryJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    GalleryJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Map<String, Object>> findImages(Long[] dietId, String status, Long offset, Long limit) {

        String query = "" +
                "select * ";
        query += "from images as i ";
        query += "where 1=1 ";
        if (status != null) {
            query += "and status = '" + status + "' ";
        }
        if (dietId != null && dietId.length > 0) {
            query += "and exists(" +
                    "select 1 from images_diets as id where i.id=id.image_id and " +
                    "id.diet_id IN (" +  getIds( dietId) + ")) ";
        }
        query +=  "LIMIT " + offset + ", " + limit;
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    void deleteImage(Long id) {
        String query = "" +
                "UPDATE images SET status = ? WHERE id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                "I",
                id
        );
    }

    void modifyImage(ImageData image) {
        String query = "" +
                "UPDATE images SET image_file_name = ?, kind = ? WHERE id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                image.getFileName(),
                image.getKind().code(),
                image.getId()

        );
    }

    void addImage(ImageData image) {
        String query = "" +
                "INSERT INTO images (image_file_name, status, kind) " +
                "VALUES( ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                image.getFileName(),
                "A",
                image.getKind().code()
        );
    }

    void deleteAssignedDiets(Long imageId) {
        String query = "" +
                "DELETE FROM images_diets WHERE image_id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                imageId
        );
    }

    void createAssignedDiet(Long imageId, Long dietId) {
        String query = "" +
                "INSERT INTO images_diets (image_id, diet_id) " +
                "VALUES( ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                imageId,
                dietId
        );
    }

    ImageData getImage(Long id) {
        String query = "SELECT * FROM images WHERE id = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[] {id},
                    (rs, rowNum) -> {
                        ImageData data = new ImageData(
                                rs.getString("image_file_name"),
                                rs.getLong("id"),
                                GalleryImageKind.toGalleryImageKind(rs.getString("kind")),
                                rs.getString("status")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Long[] getAssignedDiets(Long id) {
        String query = "" +
                "select * from images_diets where image_id = ?";
        log.trace(query);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, id);
        Set<Long> ids = new HashSet<>();
        for (Map row : rows) {
            ids.add(Long.valueOf(String.valueOf(row.get("diet_id"))));
        }

        Long[] result = new Long[ids.size()];
        ids.toArray(result);
        return result;
    }

    private String getIds( Long[] ids){
        String result = "";
        for (int i = 0; i < ids.length; i++) {
            result += ids[i];
            if (i <ids.length-1) {
                result += ", ";
            }
        }
        return result;
    }





}
