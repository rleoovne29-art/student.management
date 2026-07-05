package raisetech.student.management.repository;

import org.apache.ibatis.annotations.*;
import raisetech.student.management.date.Application;

@Mapper
public interface ApplicationRepository {
    /**
     * 申込状況の取得をします。
     * @param id
     * @return　申込者情報
     */
    @Select("SELECT * FROM applications WHERE id = #{id} AND deleted_at IS NULL")
    @Results(id = "ApplicationMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "studentsCoursesId", column = "students_courses_id"),
            @Result(property = "status", column = "status"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "deletedAt", column = "deleted_at")
    })


    Application findById(Long id);

    /**
     * 申込の新規登録をします。
     * @param application
     */
    @Insert("INSERT INTO applications (students_courses_id, status) " +
            "VALUES (#{studentsCoursesId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Application application);

    /**
     * 申込状況の変更をします。
     * @param application
     */
    @Update("UPDATE applications SET status = #{status}," +
            "updated_at = NOW() WHERE id = #{id}")
    void updateStatus(Application application);

    /**
     * 論理削除
     * @param id
     */
    @Update("UPDATE applications SET deleted_at = NOW() WHERE id = #{id}")
    void softDelete(Long id);

}
