package koders.codi.domain.jobInfo;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobInfoController {

    private final JobInfoService jobInfoService;

    @GetMapping("/api/jobs")
    public List<JobInfo> getJobInfos() throws Exception{
//        return jobInfoService.getJobInfos();
        return jobInfoService.getJobs();
    }

//    public String JobInfo(Model model) throws Exception{
//        List<JobInfo> jobInfoList = jobInfoService.getJobInfos();
//        model.addAttribute("jobInfo", jobInfoList);
//
//        return "jobInfo";
//    }
}
