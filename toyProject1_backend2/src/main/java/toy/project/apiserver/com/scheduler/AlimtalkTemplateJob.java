package toy.project.apiserver.com.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlimtalkTemplateJob {

    // 매일 02:30에 실행 (Quartz cron: "0 30 2 * * ?")
    @Scheduled(cron = "0 30 2 * * *")  // 초, 분, 시, 일, 월, 요일 (스프링은 초부터 시작)
    public void execute() {
        // 여기에 기존 Job에서 하던 로직 작성
        System.out.println("AlimtalkTemplateJob 실행 중...");
    }
}