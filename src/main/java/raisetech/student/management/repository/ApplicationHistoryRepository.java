package raisetech.student.management.repository;

import org.apache.ibatis.annotations.*;
import raisetech.student.management.date.ApplicationHistory;

import java.util.List;

@Mapper
public interface ApplicationHistoryRepository {
    /**
     * 履歴を追加します。
     * @param history
     */
    @Insert("INSERT INTO application_histories " +
            "(application_id, status, changed_at, changed_by, remark)" +
            "VALUES (#{applicationId}, #{status}, NOW(), #{changedBy}, #{remark})")
    void insert(ApplicationHistory history);
    List<ApplicationHistory> findByApplicationIdOrderByChangedAtDesc(Long applicationId);

    /**
     * 最新の履歴を１件取得します。
     * @param applicationId
     * @return 申込状況履歴
     */
    @Select("SELECT * FROM application_histories " +
            "WHERE application_id = #{applicationId}\n" +
            "    ORDER BY changed_at DESC LIMIT 1")

    @Results(id = "ApplicationHistoryMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "applicationId", column = "application_id"),
            @Result(property = "status", column = "status"),
            @Result(property = "changedAt", column = "changed_at"),
            @Result(property = "changedBy", column = "changed_by"),
            @Result(property = "remark", column = "remark")
    })

    ApplicationHistory findLatestByApplicationId(Long applicationId);

    /**
     * 履歴の一覧を取得します。(降順)
     * @param applicationId
     * @return 申込状況履歴
     */
    @Select("SELECT *\n FROM application_histories\n" +
            "WHERE application_id = #{applicationId}\n" +
            "ORDER BY changed_at DESC")
    @ResultMap("ApplicationHistoryMap")
    List<ApplicationHistory> findAllByApplicationId(Long applicationId);

}
