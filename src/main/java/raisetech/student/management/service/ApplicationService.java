package raisetech.student.management.service;

import org.springframework.stereotype.Service;
import raisetech.student.management.date.Application;
import raisetech.student.management.date.ApplicationHistory;
import raisetech.student.management.repository.ApplicationHistoryRepository;
import raisetech.student.management.repository.ApplicationRepository;

import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationHistoryRepository applicationHistoryRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              ApplicationHistoryRepository applicationHistoryRepository) {
        this.applicationRepository = applicationRepository;
        this.applicationHistoryRepository = applicationHistoryRepository;
    }

    // 申請作成
    public void createApplication(Application application) {
        applicationRepository.insert(application);
        addHistory(application, "created");
    }

    // 申請更新
    public void updateApplication(Application application) {
        applicationRepository.updateStatus(application);
        addHistory(application, "updated");
    }

    // ステータス変更
    public void changeStatus(Long id, String newStatus) {
        Application application = applicationRepository.findById(id);
        application.setStatus(newStatus);
        applicationRepository.updateStatus(application);
        addHistory(application, "status changed");
    }

    // 履歴一覧取得（新しい順）
    public List<ApplicationHistory> getHistory(Long applicationId) {
        return applicationHistoryRepository
                .findByApplicationIdOrderByChangedAtDesc(applicationId);
    }

    // 履歴追加（サービス内部専用）
    private void addHistory(Application application, String remark) {
        ApplicationHistory history = new ApplicationHistory();
        history.setApplicationId(application.getId());
        history.setStatus(application.getStatus());
        history.setRemark(remark);
        history.setChangedBy("admin");
        applicationHistoryRepository.insert(history);
    }
}
